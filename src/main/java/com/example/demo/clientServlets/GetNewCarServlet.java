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
import java.util.List;

@WebServlet("/getNewCarServlet")
public class GetNewCarServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<Car> list = null;
        try {
            list = CarRepository.getNewCar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Car car : list) {
            out.print(car);
        }
        out.close();
    }
}