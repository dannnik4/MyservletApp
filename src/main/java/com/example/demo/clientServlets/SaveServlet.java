package com.example.demo.clientServlets;

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

@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String producingCountry = request.getParameter("producingCountry");
        String bodyType = request.getParameter("bodyType");
        Boolean isUsed = Boolean.valueOf(request.getParameter("isUsed"));


        Car car = new Car();

        car.setBrand(brand);
        car.setModel(model);
        car.setProducingCountry(producingCountry);
        car.setBodyType(bodyType);
        car.setIsUsed(isUsed);

        //out.println(car.toString());
        //out.println(CarRepository.getConnection());

        int status = 0;
        try {
            status = CarRepository.save(car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //out.println(status);

        if (status > 0) {
            out.print("Record saved successfully!");
        } else {
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}