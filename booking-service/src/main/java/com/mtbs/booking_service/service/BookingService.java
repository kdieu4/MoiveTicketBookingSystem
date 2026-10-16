package com.mtbs.booking_service.service;

import com.mtbs.booking_service.domain.dto.request.CreateBookingRequest;
import com.mtbs.booking_service.domain.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    // Tạo đơn đặt vé
    BookingResponse createBooking(CreateBookingRequest request);

    // Lấy danh sách đơn đặt vé
    List<BookingResponse> getAllBookings(Long userId);

    // Lấy chi tiết đơn đặt vé
    BookingResponse getBookingById(Long bookingId);

    // Cập nhật thông tin đơn đặt vé
    BookingResponse updateBooking(Long bookingId, BookingResponse request);

    // Hủy đơn đặt vé
    void cancelBooking(Long bookingId);
}