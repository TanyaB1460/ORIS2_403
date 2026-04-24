package ru.itis.dis403.lab2_2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import ru.itis.dis403.lab2_2.annotation.GetMapping;
import ru.itis.dis403.lab2_2.service.StoreService;

import java.io.IOException;

@Component
public class HomeController {

    private final StoreService storeService;

    public HomeController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/home")
    public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(storeService.homeHtml());
    }

    @GetMapping("/")
    public void root(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(storeService.homeHtml());
    }
}