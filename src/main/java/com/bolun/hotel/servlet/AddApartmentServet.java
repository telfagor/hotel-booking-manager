package com.bolun.hotel.servlet;

import com.bolun.hotel.helper.JspHelper;
import com.bolun.hotel.service.ApartmentStatusService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/addApartment")
public class AddApartmentServet extends HttpServlet {

    private final ApartmentStatusService apartmentStatusService = ApartmentStatusService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", apartmentStatusService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("addApartment"))
                .forward(req, resp);
    }
}
