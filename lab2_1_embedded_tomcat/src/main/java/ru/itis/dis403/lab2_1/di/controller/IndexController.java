package ru.itis.dis403.lab2_1.di.controller;

import ru.itis.dis403.lab2_1.di.annotation.Controller;
import ru.itis.dis403.lab2_1.di.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class IndexController {

    @GetMapping(value = "/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>");
        writer.write("<head></head>");
        writer.write("<body> <h1>страничка с ресурсом index</h1></body></html>");
//        response.getWriter().write("эта страница по ресурсу index");
    }
}