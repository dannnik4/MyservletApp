package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            String sid = request.getParameter("id");
            int id = Integer.parseInt(sid);
            List<Employee> listEmployees = new ArrayList<>();

            try {
                Connection connection = EmployeeRepository.getConnection();
                PreparedStatement ps = connection.prepareStatement("select * from users");
                ResultSet rs = ps.executeQuery();

                countEmployees(listEmployees, rs);
                isIDBiggerThenList(out, id, listEmployees);

            } catch (SQLException e) {

                out.println("Something terrible happened");

            } finally {
                Employee employee = EmployeeRepository.getEmployeeById(id);
                out.print(employee);
                out.close();
            }
        } catch (IOException e) {
            System.out.println("ID not found.");
        }
    }

    private void isIDBiggerThenList(PrintWriter out, int id, List<Employee> listEmployees) {
        if (id > listEmployees.size()) {
            out.println("There is no such ID in database");
        }
    }

    private void countEmployees(List<Employee> listEmployees, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Employee employee = new Employee();
            listEmployees.add(employee);
        }
    }
}