package ru.itis.dis403.lab2_6.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.lab2_6.dto.BookingDto;
import ru.itis.dis403.lab2_6.dto.BookingUpdateRequest;
import ru.itis.dis403.lab2_6.model.Booking;
import ru.itis.dis403.lab2_6.model.User;
import ru.itis.dis403.lab2_6.repository.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDto> getAll() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto getById(Long id) {
        Booking b = bookingRepository.findById(id).orElse(null);
        if (b == null) {
            return null;
        }
        return convertToDto(b);
    }

    public BookingDto getBookingById(Long bookingId, User user) {
        Booking b = bookingRepository.findByIdAndHotelId(bookingId, user.getHotel().getId());
        if (b == null) {
            return null;
        }
        return convertToDto(b);
    }

    @Transactional
    public BookingDto update(Long id, BookingUpdateRequest request) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));

        if (request.getArrivalDate() != null) {
            b.setArrivalDate(request.getArrivalDate());
        }
        if (request.getStayingDate() != null) {
            b.setStayingDate(request.getStayingDate());
        }
        if (request.getDepartureDate() != null) {
            b.setDepartureDate(request.getDepartureDate());
        }

        // Обновление имени персоны
        if (request.getPersonName() != null && b.getPerson() != null) {
            b.getPerson().setName(request.getPersonName());
        }

        bookingRepository.save(b);

        return convertToDto(b);
    }

    private BookingDto convertToDto(Booking b) {
        BookingDto dto = new BookingDto();
        dto.setId(b.getId());
        dto.setArrivalDate(b.getArrivalDate());
        dto.setStayingDate(b.getStayingDate());
        dto.setDepartureDate(b.getDepartureDate());
        if (b.getPerson() != null) {
            dto.setPersonId(b.getPerson().getId());
            dto.setName(b.getPerson().getName());
            dto.setGender(b.getPerson().getGender());
            dto.setBirthDate(b.getPerson().getBirthdate());
        }
        return dto;
    }
}