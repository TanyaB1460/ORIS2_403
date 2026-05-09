package ru.itis.dis403.lab2_9.httpclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dis403.lab2_9.httpclient.service.ImageServiceForNats;

import java.io.IOException;

@Controller
public class UploadImage2Controller {

    private final ImageServiceForNats imageService;

    public UploadImage2Controller(ImageServiceForNats imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/uploading2", consumes="multipart/form-data")
    public String uploadImg(@RequestParam(value = "image", required = false) MultipartFile file) {
        try {
            if (file != null) {
                System.out.println("получили картинку (NATS)");
                imageService.processImage(file.getBytes());
            } else {
                System.out.println("нет изображения");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/compare";
    }
}