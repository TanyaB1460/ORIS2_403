package ru.itis.dis403.lab2_2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import ru.itis.dis403.lab2_2.annotation.GetMapping;
import ru.itis.dis403.lab2_2.service.StoreService;

import java.io.IOException;

@Component
public class IndexController {

    private final StoreService storeService;

    public IndexController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(storeService.indexHtml());
    }
}