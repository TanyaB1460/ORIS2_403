package ru.itis.dis403.lab2_9.httpclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dis403.lab2_9.httpclient.service.ImageService;
import ru.itis.dis403.lab2_9.httpclient.service.ImageServiceForNats;

import java.io.IOException;

@Controller
public class UploadImageController {

    private final ImageService imageService;

    public UploadImageController(ImageService imageService, ImageServiceForNats imageService2) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/uploading", consumes="multipart/form-data")
    public String uploadImg(@RequestParam(value = "image", required = false) MultipartFile file) {
        try {
            if (file != null) {
                System.out.println("получили картинку (HTTP)");
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