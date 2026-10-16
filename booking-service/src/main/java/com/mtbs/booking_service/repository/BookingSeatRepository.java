package com.mtbs.booking_service.repository;

import com.mtbs.booking_service.domain.entity.BookingSeat;
import com.mtbs.booking_service.domain.enums.BookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    // Kiểm tra ghế đã được đặt trong một suất chiếu hay chưa
    @Query("""
        SELECT COUNT(bs) > 0
        FROM BookingSeat bs
        JOIN Booking b ON b.bookingId = bs.bookingId
        WHERE b.showtimeId = :showtimeId
          AND bs.showtimeSeatId = :showtimeSeatId
          AND b.status IN :statuses
    """)
    boolean existsByShowtimeIdAndShowtimeSeatId(
            @Param("showtimeId") Long showtimeId,
            @Param("showtimeSeatId") Long showtimeSeatId,
            @Param("statuses") List<BookingStatus> statuses
    );

    // Lấy danh sách ghế thuộc một booking
    List<BookingSeat> findAllByBookingId(Long bookingId);
}