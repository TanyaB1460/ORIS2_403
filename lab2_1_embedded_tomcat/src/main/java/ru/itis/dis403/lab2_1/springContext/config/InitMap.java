package ru.itis.dis403.lab2_1.springContext.config;

import ru.itis.dis403.lab2_1.di.annotation.GetMapping;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitMap {
    private String scanPath = "SpringContext";
    private Map<Class<?>, Object> controllers = new HashMap<>();
    private Map<String, Method> httpMap= new HashMap<>();

    public InitMap() {
        scanComponent();
        initMap();
    }

    private void scanComponent() {
        List<Class<?>> classes = PathScan.find(scanPath);

        // Создание экземпляров компонент
        // перебираем список классов
        // находим конструктор с аргументами, если такого нет - создаем экземпляр
        // размещаем в components, удаляем из списка
        // если есть конструктор с аргументами (только компоненты)
        // пытаемся получить из components объекты - аргументы
        // если полный набор - создаем экземпляр, иначе пропускаем итерацию
        int countClasses = classes.size();
        while (countClasses > 0) {
            // Перебираем классы компоненты
            objectNotFound:
            for (Class c : classes) {
                if (controllers.get(c) != null) continue;
                // берем первый попавшийся контруктор
                Constructor constructor = c.getConstructors()[0];
                // извлекаем типы аргументов конструктора
                Class[] types = constructor.getParameterTypes();
                // Пытаемся найти готовые компоненты по аргументу
                Object[] args = new Object[types.length];
                for (int i = 0; i < types.length; ++i) {
                    args[i] = controllers.get(types[i]);
                    if (args[i] == null) {
                        continue objectNotFound;
                    }
                }

                try {
                    Object o = constructor.newInstance(args);
                    controllers.put(c, o);
                    countClasses--;
                    System.out.println(c + " добавлен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void initMap() {
        for (Class<?> clazz : controllers.keySet()) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    httpMap.put(path, method);

                }
            }
        }
        System.out.println("мапа проинициализирована");
    }

    public Map<String, Method> getHttpMap() {
        return httpMap;
    }
}