package com.bolun.hotel.servlet;

import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.exception.InvalidDateException;
import com.bolun.hotel.mapper.helper.JspHelper;
import com.bolun.hotel.service.ApartmentService;
import com.bolun.hotel.service.OrderService;
import com.bolun.hotel.service.impl.ApartmentServiceImpl;
import com.bolun.hotel.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.bolun.hotel.mapper.helper.UrlPath.LOGIN;
import static com.bolun.hotel.mapper.helper.UrlPath.ORDER;

@WebServlet(ORDER)
public class OrderServlet extends HttpServlet {

    private final ApartmentService apartmentService = ApartmentServiceImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime currentDateTime = LocalDateTime.now().withSecond(0).withNano(0);

        req.setAttribute("apartments", apartmentService.findAll());
        req.setAttribute("current", currentDateTime);
        req.getRequestDispatcher(JspHelper.getPath(ORDER))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReadUserDto readUserDto = (ReadUserDto) req.getSession().getAttribute("user");

        CreateOrderDto createOrderDto = new CreateOrderDto(
                readUserDto.getId(),
                req.getParameter("check-in"),
                req.getParameter("check-out"),
                req.getParameter("apartment")
        );

        try {
            orderService.create(createOrderDto);
            resp.sendRedirect(LOGIN);
        } catch (InvalidDateException ex) {
            req.setAttribute("errors", ex.getErrors());
            doGet(req, resp);
        }
    }
}
