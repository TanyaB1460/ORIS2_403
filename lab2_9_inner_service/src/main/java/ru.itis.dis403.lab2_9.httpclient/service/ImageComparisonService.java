package ru.itis.dis403.lab2_9.httpclient.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ImageComparisonService {

    private final ImageService imageService;
    private final ImageServiceForNats imageServiceForNats;

    public ImageComparisonService(ImageService imageService,
                                  ImageServiceForNats imageServiceForNats) {
        this.imageService = imageService;
        this.imageServiceForNats = imageServiceForNats;
    }

    public ComparisonResult compare(byte[] imageBytes, int iterations) {
        List<Long> httpTimes = new ArrayList<>();
        List<Long> natsTimes = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            long start = System.currentTimeMillis();
            imageService.processImageSync(imageBytes);
            long end = System.currentTimeMillis();
            httpTimes.add(end - start);
        }

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        for (int i = 0; i < iterations; i++) {
            long start = System.currentTimeMillis();
            imageServiceForNats.processImage(imageBytes);
            long end = System.currentTimeMillis();
            natsTimes.add(end - start);
        }

        return new ComparisonResult(httpTimes, natsTimes);
    }

    public static class ComparisonResult {
        private final List<Long> httpTimes;
        private final List<Long> natsTimes;

        public ComparisonResult(List<Long> httpTimes, List<Long> natsTimes) {
            this.httpTimes = httpTimes;
            this.natsTimes = natsTimes;
        }

        public List<Long> getHttpTimes() {
            return httpTimes;
        }

        public List<Long> getNatsTimes() {
            return natsTimes;
        }

        public double getHttpAvg() {
            return httpTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        }

        public double getNatsAvg() {
            return natsTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        }
    }
}