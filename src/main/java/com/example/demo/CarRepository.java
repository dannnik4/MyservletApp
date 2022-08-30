package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Logged
public class CarRepository {

    //    public static void main(String[] args) throws SQLException {
//        getConnection();
//        isDeleted(4);
//    }
    @Logged
    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/cars";
        String user = "postgres";
        String password = "JavaMax1994";

        try {
            log.info("Start connecting to the server");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                log.info("Connected to the PostgreSQL server successfully.");
            } else {
                log.info("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        log.info("Connection - end = {}", connection);
        return connection;
    }
    @Logged
    public static int save(Car car) throws SQLException {
        int status = 0;
        Connection connection = null;
        try {
            log.info("Start saving object = {}", car);
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into wheels(brand,model,producingCountry,bodyType,isused) values (?,?,?,?,?)");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getProducingCountry());
            ps.setString(4, car.getBodyType());
            ps.setBoolean(5,car.getIsUsed());
            status = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            log.info("Failed to save");
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("save() - end = {}",status);
        return status;
    }
    @Logged
    public static int update(Car car) throws SQLException {

        int status = 0;

        Connection connection = null;
        try {
            log.info("Start updating object = {}", car);
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("update wheels set brand=?,model=?,producingCountry=?,bodyType=?,isused=? where id=?");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getProducingCountry());
            ps.setString(4, car.getBodyType());
            ps.setBoolean(5,car.getIsUsed());

            ps.setInt(6, car.getId());

            status = ps.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            log.info("Failed to update");
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("update() - end = {}", status);
        return status;
    }
    @Logged
    public static int delete(int id) throws SQLException {

        int status = 0;

        Connection connection = null;
        try {
            // log.info("Start deleting object = {}", id);
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
        //log.info("delete() - end = {}", status);
        return status;
    }

    @Logged
    public static int isDeleted(int id) throws SQLException {

        int status = 0;

        Connection connection = null;
        try {
            log.info("Start isDeleted() = {}", id);
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE wheels SET isdeletedcar=TRUE WHERE id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("isDeleted() - end = {}", status);
        return status;
    }

    @Logged
    public static Car getCarById(int id) throws SQLException {

        Car car = new Car();

        Connection connection = null;
        try {
            log.info("getCarById() - start = {}, ID: ", id);
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels where id=? AND isdeletedcar = FALSE");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setProducingCountry(rs.getString(4));
                car.setBodyType(rs.getString(5));
                car.setIsUsed(rs.getBoolean(6));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("getCarById() - end = {}", car);
        return car;
    }
    @Logged
    public static List<Car> getAllCars() throws SQLException {

        List<Car> listCars = new ArrayList<>();

        Connection connection = null;
        try {
            log.info("getAllCars() - start");
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
                car.setIsUsed(rs.getBoolean(6));
                car.setIsDeletedCar(rs.getBoolean(7));
                listCars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("getAllCars() - end");
        return listCars;
    }

    @Logged
    public static List<Car> getAllAvailableCars() throws SQLException {

        List<Car> listCars = new ArrayList<>();

        Connection connection = null;
        try {
            log.info("getAllAvailableCars() - start");
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels WHERE isdeletedcar = FALSE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Car car = new Car();

                car.setId(rs.getInt(1));
                car.setBrand(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setProducingCountry(rs.getString(4));
                car.setBodyType(rs.getString(5));
                car.setIsUsed(rs.getBoolean(6));
                car.setIsDeletedCar(rs.getBoolean(7));
                listCars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("getAllAvailableCars() - end");
        return listCars;
    }

    @Logged
    public static List<Car> getNewCar() throws SQLException {

        List<Car> listCars = new ArrayList<>();

        Connection connection = null;
        try {
            log.info("getNewCar() - start");
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels WHERE isdeletedcar = FALSE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Car newCar = new Car();

                newCar.setId(rs.getInt(1));
                newCar.setBrand(rs.getString(2));
                newCar.setModel(rs.getString(3));
                newCar.setProducingCountry(rs.getString(4));
                newCar.setBodyType(rs.getString(5));
                newCar.setIsDeletedCar(rs.getBoolean(6));
                newCar.setIsUsed(rs.getBoolean(7));

                if (!newCar.getIsUsed() && newCar.getIsUsed() != null){
                    listCars.add(newCar);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("getNewCar() - end");
        return listCars;
    }

    @Logged
    public static List<Car> getUsedCar() throws SQLException {

        List<Car> listCars = new ArrayList<>();

        Connection connection = null;
        try {
            log.info("getUsedCar() - start");
            connection = CarRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from wheels WHERE isdeletedcar = FALSE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Car newCar = new Car();

                newCar.setId(rs.getInt(1));
                newCar.setBrand(rs.getString(2));
                newCar.setModel(rs.getString(3));
                newCar.setProducingCountry(rs.getString(4));
                newCar.setBodyType(rs.getString(5));
                newCar.setIsDeletedCar(rs.getBoolean(6));
                newCar.setIsUsed(rs.getBoolean(7));

                if (newCar.getIsUsed() && newCar.getIsUsed() != null){
                    listCars.add(newCar);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        log.info("getUsedCar() - end");
        return listCars;
    }

}