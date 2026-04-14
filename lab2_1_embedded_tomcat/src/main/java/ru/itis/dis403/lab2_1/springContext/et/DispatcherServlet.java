package ru.itis.dis403.lab2_1.springContext.et;

import ru.itis.dis403.lab2_1.springContext.config.InitMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    public final ApplicationContext context;

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {

            Map<String, Method> httpMap = context.getBean(InitMap.class).getHttpMap();
            Method method = httpMap.get(path);

            if (method == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("404 not found");
                return;
            }

            Object controller = context.getBean(method.getDeclaringClass());
            method.invoke(controller, req, resp);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}