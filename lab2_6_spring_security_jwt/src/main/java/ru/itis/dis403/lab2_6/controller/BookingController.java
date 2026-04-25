package ru.itis.dis403.lab2_6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dis403.lab2_6.dto.BookingDto;
import ru.itis.dis403.lab2_6.dto.BookingUpdateRequest;
import ru.itis.dis403.lab2_6.dto.BookingsViewResponse;
import ru.itis.dis403.lab2_6.dto.BookingPersonViewDto;
import ru.itis.dis403.lab2_6.model.Booking;
import ru.itis.dis403.lab2_6.repository.BookingRepository;
import ru.itis.dis403.lab2_6.service.BookingPersonViewService;
import ru.itis.dis403.lab2_6.service.BookingService;
import ru.itis.dis403.lab2_6.service.UserDetailImpl;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final BookingPersonViewService bookingPersonViewService;

    public BookingController(BookingRepository bookingRepository,
                             BookingService bookingService,
                             BookingPersonViewService bookingPersonViewService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.bookingPersonViewService = bookingPersonViewService;
    }

    // Получение одной брони по ID
    @GetMapping("/get/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        System.out.println("User: " + userDetails.getUser());

        BookingDto booking = bookingService.getBookingById(id, userDetails.getUser());

        System.out.println("Booking: " + booking);

        return ResponseEntity.ok(booking);
    }

    // Получение всех броней для отеля пользователя
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getBookings() {
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        System.out.println("User hotel: " + userDetails.getUser().getHotel());

        List<Booking> bookings = bookingRepository.findByHotel(userDetails.getUser().getHotel());

        bookings.forEach(b -> System.out.println(b.getId()));

        return ResponseEntity.ok(bookings);
    }

    // Получение броней из представления (для таблицы)
    @GetMapping("/allview")
    public ResponseEntity<BookingsViewResponse> getBookingsView() {
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<BookingPersonViewDto> bookings = bookingPersonViewService
                .findByHotelId(userDetails.getUser().getHotel().getId());

        bookings.forEach(b -> System.out.println(b.getId()));

        return ResponseEntity.ok(new BookingsViewResponse(bookings));
    }

    // Обновление брони (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable Long id,
                                             @RequestBody BookingUpdateRequest request) {
        System.out.println(">>> PUT update called: id=" + id);
        System.out.println(">>> request: arrivalDate=" + request.getArrivalDate()
                + ", stayingDate=" + request.getStayingDate());

        BookingDto updated = bookingService.update(id, request);

        System.out.println(">>> Updated: " + updated.getId());

        return ResponseEntity.ok(updated);
    }
}