package ru.itis.dis403.lab2_2.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.itis.dis403.lab2_2.annotation.GetMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class InitMap {

    private final Map<String, Method> httpMap = new HashMap<>();
    private final ApplicationContext context;

    public InitMap(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        String[] beanNames = context.getBeanNamesForAnnotation(Component.class);

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> clazz = bean.getClass();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    httpMap.put(path, method);
                }
            }
        }

        System.out.println("Мапа проиницилизирована");
    }

    public Map<String, Method> getHttpMap() {
        return httpMap;
    }
}