package ru.itis.dis403.lab2_4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.lab2_4.model.Person;
import ru.itis.dis403.lab2_4.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
