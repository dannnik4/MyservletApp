package com.example.demo.clientServlets;

import com.example.demo.CarRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clientDeletedServlet")
public class ClientDeletedServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        try {
            CarRepository.isDeleted(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("availableCarServlet");
    }

}