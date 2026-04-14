package ru.itis.dis403.lab2_4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.dis403.lab2_4.model.PersonForm;
import ru.itis.dis403.lab2_4.model.Admin;
import ru.itis.dis403.lab2_4.model.Client;
import ru.itis.dis403.lab2_4.model.Person;
import ru.itis.dis403.lab2_4.model.Phone;
import ru.itis.dis403.lab2_4.service.PersonService;
import ru.itis.dis403.lab2_4.service.PhoneService;

@Controller
public class PersonController {

    private final PersonService personService;
    private final PhoneService phoneService;

    public PersonController(PersonService personService, PhoneService phoneService) {
        this.personService = personService;
        this.phoneService = phoneService;
    }

    @GetMapping("/persons/new")
    public String newPersonForm(Model model) {
        model.addAttribute("personForm", new PersonForm());
        return "person_form";
    }

    @PostMapping("/persons")
    public String savePerson(@ModelAttribute("personForm") PersonForm form) {
        // @ModelAttribute связывает данные из формы с объектом PersonForm

        Phone phone = phoneService.getOrCreateByNumber(form.getPhoneNumber());

        Person person;
        if ("ADMIN".equalsIgnoreCase(form.getType())) {
            Admin admin = new Admin();
            admin.setName(form.getName());
            admin.setPhone(phone);
            admin.setEmail(form.getEmail());
            person = admin;
        } else {
            Client client = new Client();
            client.setName(form.getName());
            client.setPhone(phone);
            client.setAddress(form.getAddress());
            person = client;
        }

        personService.save(person);

        return "redirect:/";
    }
}
