package com.bolun.hotel.servlet;

import com.bolun.hotel.dto.ReadOrderDto;
import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.service.OrderService;
import com.bolun.hotel.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Disposition", "attachment; filename=\"orders.txt\"");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ReadUserDto readUserDto = (ReadUserDto) req.getSession().getAttribute("user");
        List<ReadOrderDto> readOrderDtoList = orderService.findUserOrdersById(readUserDto.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        byte[] data = objectMapper.writeValueAsBytes(readOrderDtoList);

        try (OutputStream outputStream = resp.getOutputStream()) {
            outputStream.write(data);
        }
    }
}
