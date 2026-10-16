package com.mtbs.booking_service.service.impl;

import com.mtbs.booking_service.client.MovieServiceClient;
import com.mtbs.booking_service.client.UserServiceClient;

import com.mtbs.booking_service.domain.dto.request.CreateBookingRequest;
import com.mtbs.booking_service.domain.dto.response.BookingResponse;
import com.mtbs.booking_service.domain.dto.response.SeatResponse;
import com.mtbs.booking_service.domain.dto.response.ShowtimeResponse;

import com.mtbs.booking_service.domain.entity.Booking;
import com.mtbs.booking_service.domain.entity.BookingSeat;

import com.mtbs.booking_service.domain.enums.BookingStatus;

import com.mtbs.booking_service.exception.BookingNotFoundException;
import com.mtbs.booking_service.exception.InvalidSeatException;
import com.mtbs.booking_service.exception.SeatAlreadyBookedException;

import com.mtbs.booking_service.messaging.BookingCreatedEvent;
import com.mtbs.booking_service.messaging.BookingEventProducer;

import com.mtbs.booking_service.repository.BookingRepository;
import com.mtbs.booking_service.repository.BookingSeatRepository;

import com.mtbs.booking_service.service.BookingService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final UserServiceClient userServiceClient;
    private final MovieServiceClient movieServiceClient;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final BookingEventProducer bookingEventProducer;

    public BookingServiceImpl(
            UserServiceClient userServiceClient,
            MovieServiceClient movieServiceClient,
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            BookingEventProducer bookingEventProducer) {

        this.userServiceClient = userServiceClient;
        this.movieServiceClient = movieServiceClient;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.bookingEventProducer = bookingEventProducer;
    }

    // 1. TẠO BOOKING
    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {

        // Kiểm tra dữ liệu đầu vào
        if (request == null || request.getUserId() == null
                || request.getShowtimeId() == null) {
            throw new IllegalArgumentException(
                    "Thông tin người dùng hoặc suất chiếu không hợp lệ"
            );
        }

        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new InvalidSeatException(
                    "Vui lòng chọn ít nhất một ghế"
            );
        }

        if (request.getSeatIds().stream().distinct().count()
                != request.getSeatIds().size()) {
            throw new InvalidSeatException(
                    "Danh sách ghế có ID bị trùng"
            );
        }

        // Kiểm tra User
        userServiceClient.getUserById(request.getUserId());

        // Lấy thông tin Showtime
        ShowtimeResponse showtime =
                movieServiceClient.getShowtimeById(request.getShowtimeId());

        if (showtime == null || showtime.getRoomId() == null) {
            throw new InvalidSeatException(
                    "Không tìm thấy thông tin phòng chiếu"
            );
        }

        // Lấy danh sách ghế theo Showtime
        List<SeatResponse> seats =
                movieServiceClient.getSeatsByShowtimeId(
                        request.getShowtimeId()
                );

        if (seats == null || seats.isEmpty()) {
            throw new InvalidSeatException(
                    "Suất chiếu không có danh sách ghế"
            );
        }

        List<SeatResponse> selectedSeats = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Kiểm tra từng ghế được chọn
        for (Long seatId : request.getSeatIds()) {

            SeatResponse selectedSeat = seats.stream()
                    .filter(seat -> seatId.equals(seat.getSeatId()))
                    .findFirst()
                    .orElseThrow(() -> new InvalidSeatException(
                            "Ghế " + seatId
                                    + " không thuộc suất chiếu này"
                    ));

            if (selectedSeat.getShowtimeSeatId() == null) {
                throw new InvalidSeatException(
                        "Ghế chưa được liên kết với suất chiếu"
                );
            }

            if (selectedSeat.getPrice() == null
                    || selectedSeat.getPrice()
                            .compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidSeatException(
                        "Giá ghế không hợp lệ"
                );
            }

            // Kiểm tra trạng thái ghế từ Movie Service
            if ("BOOKED".equalsIgnoreCase(selectedSeat.getStatus())
                    || "HELD".equalsIgnoreCase(selectedSeat.getStatus())) {

                throw new SeatAlreadyBookedException(
                        "Ghế " + seatId + " đã được đặt hoặc giữ"
                );
            }

            // Kiểm tra ghế đã được đặt trong Booking Service chưa
            boolean alreadyBooked =
                    bookingSeatRepository
                            .existsByShowtimeIdAndShowtimeSeatId(
                                    request.getShowtimeId(),
                                    selectedSeat.getShowtimeSeatId(),
                                    List.of(
                                            BookingStatus.PENDING,
                                            BookingStatus.PAID
                                    )
                            );

            if (alreadyBooked) {
                throw new SeatAlreadyBookedException(
                        "Ghế " + seatId
                                + " đã được đặt cho suất chiếu này"
                );
            }

            selectedSeats.add(selectedSeat);
            totalAmount = totalAmount.add(selectedSeat.getPrice());
        }

        // Tạo Booking
        Booking booking = new Booking();

        booking.setUserId(request.getUserId());
        booking.setShowtimeId(request.getShowtimeId());

        booking.setBookingCode(
                "BK-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingTime(OffsetDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        // Lưu chi tiết ghế
        List<Long> showtimeSeatIds = new ArrayList<>();

        for (SeatResponse seat : selectedSeats) {

            BookingSeat bookingSeat = new BookingSeat();

            bookingSeat.setBookingId(savedBooking.getBookingId());
            bookingSeat.setShowtimeSeatId(seat.getShowtimeSeatId());
            bookingSeat.setPrice(seat.getPrice());

            bookingSeatRepository.save(bookingSeat);

            showtimeSeatIds.add(seat.getShowtimeSeatId());
        }

        // Phát sự kiện BookingCreated
        BookingCreatedEvent event = new BookingCreatedEvent();

        event.setBookingId(savedBooking.getBookingId());
        event.setUserId(savedBooking.getUserId());
        event.setShowtimeId(savedBooking.getShowtimeId());
        event.setShowtimeSeatIds(showtimeSeatIds);
        event.setTotalAmount(savedBooking.getTotalAmount());
        event.setStatus(savedBooking.getStatus());

        bookingEventProducer.publishBookingCreated(event);

        // Trả về kết quả
        return convertToResponse(savedBooking);
    }

    // 2. LẤY DANH SÁCH BOOKING
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings(Long userId) {

        List<Booking> bookings;

        if (userId == null) {
            bookings = bookingRepository.findAll();
        } else {
            bookings = bookingRepository.findAllByUserId(userId);
        }

        return bookings.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // 3. LẤY CHI TIẾT BOOKING
    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Không tìm thấy booking với ID: " + bookingId
                ));

        return convertToResponse(booking);
    }

    // 4. CẬP NHẬT BOOKING
    @Override
    @Transactional
    public BookingResponse updateBooking(
            Long bookingId,
            BookingResponse request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Thông tin cập nhật không hợp lệ"
            );
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Không tìm thấy booking với ID: " + bookingId
                ));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Chỉ có thể cập nhật booking đang ở trạng thái PENDING"
            );
        }

        booking.setPaymentMethod(request.getPaymentMethod());

        Booking savedBooking = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    // 5. HỦY BOOKING
    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Không tìm thấy booking với ID: " + bookingId
                ));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Booking đã được hủy trước đó"
            );
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Chỉ có thể hủy booking đang ở trạng thái PENDING"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }

    // 6. CHUYỂN BOOKING THÀNH BOOKING RESPONSE
    private BookingResponse convertToResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setBookingId(booking.getBookingId());
        response.setUserId(booking.getUserId());
        response.setShowtimeId(booking.getShowtimeId());
        response.setBookingCode(booking.getBookingCode());
        response.setTotalAmount(booking.getTotalAmount());
        response.setStatus(booking.getStatus());
        response.setPaymentMethod(booking.getPaymentMethod());
        response.setBookingTime(booking.getBookingTime());
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());

        List<Long> showtimeSeatIds =
                bookingSeatRepository
                        .findAllByBookingId(booking.getBookingId())
                        .stream()
                        .map(BookingSeat::getShowtimeSeatId)
                        .toList();

        response.setShowtimeSeatIds(showtimeSeatIds);

        return response;
    }
}