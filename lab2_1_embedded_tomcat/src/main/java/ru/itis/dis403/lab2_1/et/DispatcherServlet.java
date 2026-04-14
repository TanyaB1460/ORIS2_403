package ru.itis.dis403.lab2_1.et;

import ru.itis.dis403.lab2_1.di.config.Context;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends HttpServlet {
    public final Context context;

    public DispatcherServlet(Context context) {
        this.context = context;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            Method method = context.getHttpMap().get(path);
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