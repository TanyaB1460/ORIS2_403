package ru.itis.dis403.lab2_6.service;

import ru.itis.dis403.lab2_6.dto.BookingDto;
import ru.itis.dis403.lab2_6.dto.BookingUpdateRequest;
import ru.itis.dis403.lab2_6.model.Booking;
import ru.itis.dis403.lab2_6.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDto> getAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BookingDto getById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return toDto(booking);
    }



    private BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getHotel() != null ? booking.getHotel().getName() : "",
                booking.getPerson() != null ? String.valueOf(booking.getPerson().getGender()) : "",
                booking.getPerson() != null && booking.getPerson().getBirthdate() != null
                        ? booking.getPerson().getBirthdate().toString() : "",
                booking.getPerson() != null ? booking.getPerson().getFromCity() : "",
                booking.getArrivalDate() != null ? booking.getArrivalDate().toString() : "",
                booking.getStayingDate() != null ? booking.getStayingDate().toString() : "",
                booking.getDepartureDate() != null ? booking.getDepartureDate().toString() : ""
        );
    }

    public BookingDto update(Long id, BookingUpdateRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setArrivalDate(
                request.getArrivalDate() == null || request.getArrivalDate().isBlank()
                        ? null
                        : java.sql.Date.valueOf(request.getArrivalDate())
        );

        booking.setStayingDate(
                request.getStayingDate() == null || request.getStayingDate().isBlank()
                        ? null
                        : java.sql.Date.valueOf(request.getStayingDate())
        );

        booking.setDepartureDate(
                request.getDepartureDate() == null || request.getDepartureDate().isBlank()
                        ? null
                        : java.sql.Date.valueOf(request.getDepartureDate())
        );

        if (booking.getPerson() != null) {
            booking.getPerson().setFromCity(request.getFromCity());
        }

        bookingRepository.save(booking);
        return toDto(booking);
    }
}