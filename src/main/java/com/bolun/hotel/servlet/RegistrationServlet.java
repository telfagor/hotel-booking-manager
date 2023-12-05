package com.bolun.hotel.servlet;

import com.bolun.hotel.dto.CreateUserDto;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.exception.UserNotValidException;
import com.bolun.hotel.helper.JspHelper;
import com.bolun.hotel.service.UserService;
import com.bolun.hotel.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;

import static com.bolun.hotel.helper.UrlPath.LOGIN;
import static com.bolun.hotel.helper.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", List.of(Role.values()));
        req.setAttribute("genders", List.of(Gender.values()));
        req.getRequestDispatcher(JspHelper.getPath(REGISTRATION))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .firstName(req.getParameter("first_name"))
                .lastName(req.getParameter("last_name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.save(createUserDto);
            resp.sendRedirect(LOGIN);
        } catch (UserNotValidException ex) {
            req.setAttribute("errors", ex.getErrors());
            doGet(req, resp);
        }
    }
}
