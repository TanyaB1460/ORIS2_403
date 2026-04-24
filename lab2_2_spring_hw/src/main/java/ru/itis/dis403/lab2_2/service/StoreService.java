package ru.itis.dis403.lab2_2.service;

import org.springframework.stereotype.Component;

@Component
public class StoreService {

    public String homeHtml() {
        return "<h1>Главная страница</h1><a href='/home'>/home</a><br><a href='/index'>/index</a>";
    }

    public String indexHtml() {
        return "<h1>Страница index</h1><a href='/home'>На home</a>";
    }
}