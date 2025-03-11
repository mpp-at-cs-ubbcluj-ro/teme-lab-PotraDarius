package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public CarsDBRepository(Properties props){
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Car elem){
        logger.traceEntry(  "saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(  "insert into cars (manufacturer, model, year) values (?,?,?)") ){

            preStmt.setString(  1,elem.getManufacturer());
            preStmt.setString( 2,elem.getModel());
            preStmt.setInt( 3,elem.getYear());
            int result=preStmt.executeUpdate();
            logger.trace(  "Saved {} instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry("updating task {}", integer);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement(("update cars set manufacturer = ?, model = ?, year = ? where id = ?"))){
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            preStmt.setInt(3,elem.getYear());
            preStmt.setInt(4,integer);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e );
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll(){
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(  "select * from cars") ) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findByManufacturer(String manufacturer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(  "select * from cars where manufacturer = ?") ) {
            preStmt.setString(1,manufacturer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String mnfct = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(mnfct, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(  "select * from cars where year between ? and ?") ) {
            preStmt.setInt(1,min);
            preStmt.setInt(2,max);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String manufacturer = result.getString("manufacturer");
                    String model = result.getString("model");
                    int year = result.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);

                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(cars);
        return cars;
    }
}

