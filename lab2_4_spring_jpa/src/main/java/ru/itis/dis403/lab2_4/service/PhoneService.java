package ru.itis.dis403.lab2_4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.lab2_4.model.Phone;
import ru.itis.dis403.lab2_4.repository.PhoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Optional<Phone> findByNumber(String number) {
        return phoneRepository.findByNumber(number);
    }

    @Transactional
    public Phone getOrCreateByNumber(String number) {
        return phoneRepository.findByNumber(number)
                .orElseGet(() -> {
                    Phone phone = new Phone();
                    phone.setNumber(number);
                    return phoneRepository.save(phone);
                });
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

}