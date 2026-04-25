package ru.itis.dis403.lab2_6.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.lab2_6.dto.BookingSaveRequest;
import ru.itis.dis403.lab2_6.model.Booking;
import ru.itis.dis403.lab2_6.model.Person;
import ru.itis.dis403.lab2_6.model.User;
import ru.itis.dis403.lab2_6.repository.BookingRepository;
import ru.itis.dis403.lab2_6.repository.PersonRepository;

@Service
public class BookingManageService {

    private final BookingRepository bookingRepository;
    private final PersonRepository personRepository;

    public BookingManageService(BookingRepository bookingRepository,
                                PersonRepository personRepository) {
        this.bookingRepository = bookingRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public Booking saveBooking(BookingSaveRequest request, User user) {
        Person person = new Person();
        person.setName(request.getPersonName());
        person.setGender(request.getPersonGender());
        person.setBirthdate(request.getPersonBirthdate());
        person.setFromCity("Не указан");
        person = personRepository.save(person);

        Booking booking = new Booking();
        booking.setId(request.getId());
        booking.setArrivalDate(request.getArrivalDate());
        booking.setStayingDate(request.getStayingDate());
        booking.setDepartureDate(request.getDepartureDate());
        booking.setHotel(user.getHotel());
        booking.setPerson(person);

        return bookingRepository.save(booking);
    }
}