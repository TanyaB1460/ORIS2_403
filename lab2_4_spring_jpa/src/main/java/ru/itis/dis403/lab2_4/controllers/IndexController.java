package ru.itis.dis403.lab2_4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dis403.lab2_4.model.Phone;
import ru.itis.dis403.lab2_4.service.PhoneService;
import ru.itis.dis403.lab2_4.service.PersonService;

import java.util.List;

@Controller
public class IndexController {

    private final PhoneService phoneService;
    private final PersonService personService;

    public IndexController(PhoneService phoneService, PersonService personService) {
        this.phoneService = phoneService;
        this.personService = personService;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<Phone> phones = phoneService.findAll();

        model.addAttribute("phones", phones);
        model.addAttribute("persons", personService.findAll());

        return "index";
    }
}
