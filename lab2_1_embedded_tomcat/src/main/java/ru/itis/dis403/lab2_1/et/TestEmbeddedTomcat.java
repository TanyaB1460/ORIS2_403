package ru.itis.dis403.lab2_1.et;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TestEmbeddedTomcat {
    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");  //временная папка для работы сервера для хранения служебных файлов
        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);

        String contextPath = "";

        String docBase = new File(".").getAbsolutePath(); //физичексий путь
        Context tomcatContext = tomcat.addContext(contextPath, docBase);

        ru.itis.dis403.lab2_1.di.config.Context appContext = new ru.itis.dis403.lab2_1.di.config.Context();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        String servletName = "dispatcher";
        tomcat.addServlet(contextPath, servletName, dispatcherServlet);


        tomcatContext.addServletMappingDecoded("/*", servletName);


        try {
            tomcat.start();
            tomcat.getServer().await(); //await() заставляет основной поток  «заснуть» и ждать сигнала остановки сервера
            /*
                tomcat.stop()
                tomcat.destroy()
             */
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

    }
}