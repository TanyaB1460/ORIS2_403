package ru.itis.dis403.lab2_6.controller;

import ru.itis.dis403.lab2_6.dto.BookingDto;
import ru.itis.dis403.lab2_6.dto.BookingUpdateRequest;
import ru.itis.dis403.lab2_6.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDto> getAll() {
        return bookingService.getAll();
    }

    @GetMapping("/{id}")
    public BookingDto getOne(@PathVariable Long id) {
        return bookingService.getById(id);
    }

    @PutMapping("/{id}")
    public BookingDto update(@PathVariable Long id, @RequestBody BookingUpdateRequest request) {
        return bookingService.update(id, request);
    }
}