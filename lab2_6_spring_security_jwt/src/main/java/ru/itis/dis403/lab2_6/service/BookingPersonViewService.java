package ru.itis.dis403.lab2_6.service;

import org.springframework.stereotype.Service;
import ru.itis.dis403.lab2_6.dto.BookingPersonViewDto;
import ru.itis.dis403.lab2_6.model.BookingPersonView;
import ru.itis.dis403.lab2_6.repository.BookingPersonViewRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingPersonViewService {

    private final BookingPersonViewRepository bookingPersonViewRepository;

    public BookingPersonViewService(BookingPersonViewRepository bookingPersonViewRepository) {
        this.bookingPersonViewRepository = bookingPersonViewRepository;
    }

    public List<BookingPersonViewDto> findByHotelId(Long hotelId) {
        List<BookingPersonView> views = bookingPersonViewRepository.findByHotelId(hotelId);
        List<BookingPersonViewDto> result = new ArrayList<>();

        for (BookingPersonView b : views) {
            BookingPersonViewDto dto = new BookingPersonViewDto();
            dto.setId(b.getId());
            dto.setArrivaldate(b.getArrivaldate());
            dto.setStayingdate(b.getStayingdate());
            dto.setRoom(b.getRoom());
            dto.setName(b.getName());
            dto.setBirthdate(b.getBirthdate());
            dto.setHotelId(b.getHotelId());
            dto.setGender(b.getGender());
            result.add(dto);
        }

        return result;
    }
}