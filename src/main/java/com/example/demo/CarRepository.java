package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/cars";
        String user = "postgres";
        String password = "JavaMax1994";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public static int save(Car car) throws SQLException {
        int status = 0;
        Connection connection = null;
        try {
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into wheels(brand,model,producingCountry,bodyType) values (?,?,?,?)");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getProducingCountry());
            ps.setString(4, car.getBodyType());

            status = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return status;
    }

    public static int update(Car car) throws SQLException {

        int status = 0;

        Connection connection = null;
        try {
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("update wheels set brand=?,model=?,producingCountry=?,bodyType=? where id=?");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getProducingCountry());
            ps.setString(4, car.getBodyType());

            ps.setInt(5, car.getId());

            status = ps.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return status;
    }

    public static int delete(int id) throws SQLException {

        int status = 0;

        Connection connection = null;
        try {
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from wheels where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return status;
    }

    public static Car getCarById(int id) throws SQLException {

        Car car = new Car();

        Connection connection = null;
        try {
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setProducingCountry(rs.getString(4));
                car.setBodyType(rs.getString(5));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return car;
    }

    public static List<Car> getAllCars() throws SQLException {

        List<Car> listCars = new ArrayList<>();

        Connection connection = null;
        try {
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Car car = new Car();

                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setProducingCountry(rs.getString(4));
                car.setBodyType(rs.getString(5));
                listCars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return listCars;
    }
}