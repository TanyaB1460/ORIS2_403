package ru.itis.dis403.lab2_6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dis403.lab2_6.dto.BookingSaveRequest;
import ru.itis.dis403.lab2_6.model.Booking;
import ru.itis.dis403.lab2_6.service.BookingManageService;
import ru.itis.dis403.lab2_6.service.UserDetailImpl;

@RestController
@RequestMapping("/api/booking")
public class BookingManageController {

    private final BookingManageService bookingManageService;

    public BookingManageController(BookingManageService bookingManageService) {
        this.bookingManageService = bookingManageService;
    }

    @PostMapping("/save")
    public ResponseEntity<Booking> saveBooking(@RequestBody BookingSaveRequest request) {
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Booking saved = bookingManageService.saveBooking(request, userDetails.getUser());
        return ResponseEntity.ok(saved);
    }
}