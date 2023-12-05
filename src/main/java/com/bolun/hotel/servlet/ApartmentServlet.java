package com.bolun.hotel.servlet;

import com.bolun.hotel.mapper.helper.JspHelper;
import com.bolun.hotel.service.ApartmentService;
import com.bolun.hotel.service.impl.ApartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.bolun.hotel.mapper.helper.UrlPath.APARTMENT;

@WebServlet(APARTMENT)
public class ApartmentServlet extends HttpServlet {

    private final ApartmentService apartmentService = ApartmentServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("apartments", apartmentService.findAll());
        req.getRequestDispatcher(JspHelper.getPath(APARTMENT))
                .forward(req, resp);
    }
}
