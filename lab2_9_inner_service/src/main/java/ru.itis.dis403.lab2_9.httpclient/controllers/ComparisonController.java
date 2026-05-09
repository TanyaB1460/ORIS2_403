package ru.itis.dis403.lab2_9.httpclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dis403.lab2_9.httpclient.service.ImageComparisonService;

import java.io.IOException;
import java.util.*;

@Controller
public class ComparisonController {

    private final ImageComparisonService comparisonService;

    public ComparisonController(ImageComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @GetMapping("/compare")
    public String comparePage() {
        return "compare";
    }

    @PostMapping("/compare")
    public String doCompare(Model model,
                            @RequestParam("image") MultipartFile file,
                            @RequestParam(value = "iterations", defaultValue = "5") int iterations) {

        if (file == null || file.isEmpty()) {
            model.addAttribute("error", "Выберите изображение");
            return "compare";
        }

        try {
            byte[] imageBytes = file.getBytes();
            ImageComparisonService.ComparisonResult result =
                    comparisonService.compare(imageBytes, iterations);

            model.addAttribute("httpTimes", result.getHttpTimes());
            model.addAttribute("natsTimes", result.getNatsTimes());
            model.addAttribute("httpAvg", result.getHttpAvg());
            model.addAttribute("natsAvg", result.getNatsAvg());
            model.addAttribute("iterations", iterations);

            String faster;
            double diff;
            if (result.getHttpAvg() < result.getNatsAvg()) {
                faster = "HTTP (прямой вызов)";
                diff = result.getNatsAvg() - result.getHttpAvg();
            } else {
                faster = "NATS (через брокер)";
                diff = result.getHttpAvg() - result.getNatsAvg();
            }
            model.addAttribute("faster", faster);
            model.addAttribute("diff", diff);

        } catch (IOException e) {
            model.addAttribute("error", "Ошибка: " + e.getMessage());
        }

        return "compare";
    }
}