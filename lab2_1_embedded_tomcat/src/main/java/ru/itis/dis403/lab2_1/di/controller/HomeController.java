package ru.itis.dis403.lab2_1.di.controller;

import ru.itis.dis403.lab2_1.di.annotation.Controller;
import ru.itis.dis403.lab2_1.di.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>");
        writer.write("<head></head><body><h1>страничка с ресурсом home</h1></body</html>");
//        response.getWriter().write("эта страница по ресурсу home");
    }

}