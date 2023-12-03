package com.bolun.hotel.servlet;

import com.bolun.hotel.dto.CreateUserDetailDto;
import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.exception.UserDetailNotValidationException;
import com.bolun.hotel.helper.JspHelper;
import com.bolun.hotel.service.ImageService;
import com.bolun.hotel.service.UserDetailService;
import com.bolun.hotel.service.UserService;
import com.bolun.hotel.service.impl.UserDetailServiceImpl;
import com.bolun.hotel.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.bolun.hotel.helper.UrlPath.USER_DETAIL;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(USER_DETAIL)
public class UserDetailServlet extends HttpServlet {
    private final UserDetailService userDetailService = UserDetailServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(USER_DETAIL))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReadUserDto user = (ReadUserDto) req.getSession().getAttribute("user");

        CreateUserDetailDto createUserDetailDto = new CreateUserDetailDto(
                user.getId(),
                req.getParameter("telephone"),
                req.getPart("photo"),
                req.getParameter("birthdate"),
                req.getParameter("amount")
        );

        try {
            userDetailService.create(createUserDetailDto);
        } catch (UserDetailNotValidationException ex) {
            req.setAttribute("errors", ex.getErrors());
            doGet(req, resp);
        }
    }
}
