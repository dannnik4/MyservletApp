package com.example.demo.adminServlets;

import com.example.demo.Car;
import com.example.demo.CarRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String producingCountry = request.getParameter("producingCountry");
        String bodyType = request.getParameter("bodyType");
        Boolean isUsed = Boolean.valueOf(request.getParameter("isUsed"));

        Car car = new Car();
        car.setId(id);
        car.setBrand(brand);
        car.setModel(model);
        car.setProducingCountry(producingCountry);
        car.setBodyType(bodyType);
        car.setIsUsed(isUsed);

        int status = 0;
        try {
            status = CarRepository.update(car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (status > 0) {
            response.sendRedirect("viewServlet");
        } else {
            out.println("Sorry! unable to update record");
        }
        out.close();
    }
}