package com.bolun.hotel.servlet;

import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.helper.JspHelper;
import com.bolun.hotel.service.UserService;
import com.bolun.hotel.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static com.bolun.hotel.helper.UrlPath.LOGIN;
import static com.bolun.hotel.helper.UrlPath.APARTMENT;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("/login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        userService.findByEmailAndPassword(email, password)
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginSuccess(ReadUserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(APARTMENT);
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
    }
}
