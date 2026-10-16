package com.mtbs.booking_service.messaging;

import com.mtbs.booking_service.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.util.List;

public class BookingCreatedEvent {

    private Long bookingId;
    private Long userId;
    private Long showtimeId;
    private List<Long> showtimeSeatIds;
    private BigDecimal totalAmount;
    private BookingStatus status;

    public BookingCreatedEvent() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<Long> getShowtimeSeatIds() {
        return showtimeSeatIds;
    }

    public void setShowtimeSeatIds(List<Long> showtimeSeatIds) {
        this.showtimeSeatIds = showtimeSeatIds;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}