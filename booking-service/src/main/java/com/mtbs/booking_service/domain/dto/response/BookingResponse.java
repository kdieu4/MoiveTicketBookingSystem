package com.mtbs.booking_service.domain.dto.response;

import com.mtbs.booking_service.domain.enums.BookingStatus;
import com.mtbs.booking_service.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingResponse {

    private Long bookingId;

    private Long userId;

    private Long showtimeId;

    private String bookingCode;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private PaymentMethod paymentMethod;

    private OffsetDateTime bookingTime;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<Long> showtimeSeatIds;

    public BookingResponse() {
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

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OffsetDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(OffsetDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Long> getShowtimeSeatIds() {
        return showtimeSeatIds;
    }

    public void setShowtimeSeatIds(List<Long> showtimeSeatIds) {
        this.showtimeSeatIds = showtimeSeatIds;
    }
}