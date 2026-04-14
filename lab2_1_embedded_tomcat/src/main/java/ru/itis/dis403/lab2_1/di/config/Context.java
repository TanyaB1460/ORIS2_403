package ru.itis.dis403.lab2_1.di.config;

import ru.itis.dis403.lab2_1.di.annotation.Controller;
import ru.itis.dis403.lab2_1.di.annotation.GetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private String scanPath = "ru.itis.dis403.lab2_1.di";
    private Map<Class<?>, Object> beans = new HashMap<>();
    private Map<String, Method> httpMap= new HashMap<>();

    public Context() {
        scanComponent();
        initMap();

    }
    public Object getBean(Class clazz) {
        return beans.get(clazz);
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
                if (beans.get(c) != null) continue;
                // берем первый попавшийся контруктор
                Constructor constructor = c.getConstructors()[0];
                // извлекаем типы аргументов конструктора
                Class[] types = constructor.getParameterTypes();
                // Пытаемся найти готовые компоненты по аргументу
                Object[] args = new Object[types.length];
                for (int i = 0; i < types.length; ++i) {
                    args[i] = beans.get(types[i]);
                    if (args[i] == null) {
                        continue objectNotFound;
                    }
                }

                try {
                    Object o = constructor.newInstance(args); //вызывает конструктор и передает туда зависимости
                    beans.put(c, o);
                    countClasses--;
                    System.out.println(c + " добавлен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void initMap() {
        for (Class<?> clazz : beans.keySet()) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        String path = method.getAnnotation(GetMapping.class).value();
                        httpMap.put(path, method); //url - метод

                    }
                }
            }
        }
        System.out.println("Map проинициализирована");
    }

    public Map<String, Method> getHttpMap() {
        return httpMap;
    }
}