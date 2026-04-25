package ru.itis.dis403.lab2_6.dto;

import java.util.List;

public class BookingsViewResponse {

    private List<BookingPersonViewDto> bookings;

    // Конструктор по умолчанию
    public BookingsViewResponse() {
    }

    // Конструктор с параметром
    public BookingsViewResponse(List<BookingPersonViewDto> bookings) {
        this.bookings = bookings;
    }

    // Геттер
    public List<BookingPersonViewDto> getBookings() {
        return bookings;
    }

    // Сеттер
    public void setBookings(List<BookingPersonViewDto> bookings) {
        this.bookings = bookings;
    }
}