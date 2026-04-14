package ru.itis.dis403.lab2_1.springContext.et;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class TestEmbeddedTomcat {
    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);

        String contextPath = "";

        String docBase = new File(".").getAbsolutePath();
        Context tomcatContext = tomcat.addContext(contextPath, docBase);

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext("" +
                "SpringContext");
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        String servletName = "dispatcher";
        tomcat.addServlet(contextPath, servletName, dispatcherServlet);


        tomcatContext.addServletMappingDecoded("/*", servletName);


        try {
            tomcat.start();
            tomcat.getServer().await();

            /*
                tomcat.stop()
                tomcat.destroy()
             */
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

    }
}