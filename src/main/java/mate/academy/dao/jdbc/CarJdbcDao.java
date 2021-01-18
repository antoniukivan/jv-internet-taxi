package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.dao.CarDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.util.ConnectionUtil;

@Dao
public class CarJdbcDao implements CarDao {
    @Override
    public Car create(Car car) {
        String insertCar = "INSERT INTO `cars` (`manufacturer_id`, `car_model`) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement insertStatement
                        = connection.prepareStatement(insertCar, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setLong(1, car.getManufacturer().getId());
            insertStatement.setString(2, car.getModel());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert car to the DB"
                    + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String selectCar = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.car_id = ? AND c.deleted = FALSE AND m.manufacturer_deleted = FALSE";
        String selectDrivers = "SELECT * FROM cars_drivers cd "
                + "JOIN drivers d ON cd.driver_id = d.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectCarStatement = connection.prepareStatement(selectCar);
                 PreparedStatement selectDriversStatement
                         = connection.prepareStatement(selectDrivers)) {
            selectCarStatement.setLong(1, id);
            selectDriversStatement.setLong(1, id);
            ResultSet carResultSet = selectCarStatement.executeQuery();
            ResultSet driversResultSet = selectDriversStatement.executeQuery();
            Car car = null;
            if (carResultSet.next()) {
                car = getCar(carResultSet);
            }
            Objects.requireNonNull(car).setDrivers(getCarDrivers(driversResultSet));
            return Optional.of(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String selectCar = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.deleted = FALSE AND m.manufacturer_deleted = FALSE";
        String selectDrivers = "SELECT * FROM cars_drivers cd "
                + "JOIN drivers d ON cd.driver_id = d.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectCarStatement = connection.prepareStatement(selectCar);
                    PreparedStatement selectDriversStatement
                        = connection.prepareStatement(selectDrivers)) {
            ResultSet carResultSet = selectCarStatement.executeQuery();
            while (carResultSet.next()) {
                cars.add(getCar(carResultSet));
            }
            for (Car car : cars) {
                selectDriversStatement.setLong(1, car.getId());
                ResultSet driversResultSet = selectDriversStatement.executeQuery();
                while (driversResultSet.next()) {
                    car.setDrivers(getCarDrivers(driversResultSet));
                }
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    @Override
    public Car update(Car car) {
        String update = "UPDATE cars SET manufacturer_id = ?, car_model = ?"
                + "WHERE (car_id = ?) AND (deleted = FALSE)";
        String delete = "DELETE FROM cars_drivers WHERE car_id = ?";
        String insert = "INSERT INTO cars_drivers VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update);
                PreparedStatement deleteStatement = connection.prepareStatement(delete);
                     PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            updateStatement.setLong(1, car.getManufacturer().getId());
            updateStatement.setString(2, car.getModel());
            updateStatement.setLong(3, car.getId());
            updateStatement.execute();
            deleteStatement.setLong(1, car.getId());
            deleteStatement.execute();
            for (Driver driver : car.getDrivers()) {
                insertStatement.setLong(1, driver.getId());
                insertStatement.setLong(2, car.getId());
                insertStatement.execute();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String update = "UPDATE cars SET deleted = TRUE WHERE car_id = ?";
        String delete = "DELETE FROM cars_drivers WHERE car_id = ?";
        int updatedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update);
                PreparedStatement deleteStatement = connection.prepareStatement(delete)) {
            updateStatement.setLong(1, id);
            updatedRows = updateStatement.executeUpdate();
            deleteStatement.setLong(1, id);
            deleteStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
        return updatedRows > 0;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String selectCars = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
                + "JOIN cars_drivers cd ON c.car_id = cd.car_id "
                + "JOIN drivers d ON cd.driver_id = d.driver_id "
                + "WHERE cd.driver_id = ? AND c.deleted = FALSE AND d.deleted = FALSE";
        String selectDrivers = "SELECT * FROM cars_drivers cd "
                + "JOIN drivers d ON cd.driver_id = d.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectCarStatement = connection.prepareStatement(selectCars);
                 PreparedStatement selectDriversStatement
                         = connection.prepareStatement(selectDrivers)) {
            selectCarStatement.setLong(1, driverId);
            ResultSet carResultSet = selectCarStatement.executeQuery();
            while (carResultSet.next()) {
                Car car = getCar(carResultSet);
                selectDriversStatement.setLong(1, car.getId());
                ResultSet driversResultSet = selectDriversStatement.executeQuery();
                car.setDrivers(getCarDrivers(driversResultSet));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars by driver id " + driverId, e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("car_id", Long.class);
        String carModel = resultSet.getObject("car_model", String.class);
        Car car = new Car(carModel, getManufacturer(resultSet));
        car.setId(carId);
        return car;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getObject("manufacturer_name", String.class);
        String manufacturerCountry = resultSet.getObject("manufacturer_country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }

    private List<Driver> getCarDrivers(ResultSet resultSet) throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getObject("driver_id", Long.class);
            String name = resultSet.getObject("driver_name", String.class);
            String licenseNumber = resultSet.getObject("driver_license_number", String.class);
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(id);
            drivers.add(driver);
        }
        return drivers;
    }
}
