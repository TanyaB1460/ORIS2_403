package ru.itis.dis403.lab2_2;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.lab2_2.config.Config;
import ru.itis.dis403.lab2_2.web.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8090);
        tomcat.setBaseDir("temp");

        Context appContext = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(appContext, "dispatcher", new DispatcherServlet(context));
        appContext.addServletMappingDecoded("/*", "dispatcher");

        tomcat.getConnector(); //инициализирует соединение
        tomcat.start(); //запускает сервер

        tomcat.getServer().await();
    }
}