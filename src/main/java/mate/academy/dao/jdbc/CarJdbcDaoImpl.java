package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CarDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.util.ConnectionUtil;

@Dao
public class CarJdbcDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String insertCarQuery = "INSERT INTO cars (manufacturer_id, model) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement
                        = connection.prepareStatement(insertCarQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
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
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.id = ? AND c.deleted = FALSE AND m.deleted = FALSE";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectCarStatement = connection.prepareStatement(selectCar)) {
            selectCarStatement.setLong(1, id);
            ResultSet carResultSet = selectCarStatement.executeQuery();
            if (carResultSet.next()) {
                car = getCar(carResultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id " + id, e);
        }
        if (car != null) {
            car.setDrivers(getCarDrivers(id));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String selectCar = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = FALSE AND m.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectCarStatement = connection.prepareStatement(selectCar)) {
            ResultSet carResultSet = selectCarStatement.executeQuery();
            while (carResultSet.next()) {
                cars.add(getCar(carResultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        for (Car car : cars) {
            car.setDrivers(getCarDrivers(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String update = "UPDATE cars SET manufacturer_id = ?, model = ?"
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update)) {
            updateStatement.setLong(1, car.getManufacturer().getId());
            updateStatement.setString(2, car.getModel());
            updateStatement.setLong(3, car.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
        deleteDriversFromCar(car);
        insertDriversToCar(car.getDrivers(), car);
        return car;
    }

    private void insertDriversToCar(List<Driver> drivers, Car car) {
        String insert = "INSERT INTO cars_drivers VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            for (Driver driver : drivers) {
                insertStatement.setLong(1, driver.getId());
                insertStatement.setLong(2, car.getId());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add drivers to the car " + car, e);
        }
    }

    private void deleteDriversFromCar(Car car) {
        String delete = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(delete)) {
            deleteStatement.setLong(1, car.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers from the car " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String update = "UPDATE cars SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update)) {
            updateStatement.setLong(1, id);
            int updatedRows = updateStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String selectCars = "SELECT * FROM cars_drivers cd "
                + "JOIN cars c ON c.id = cd.car_id "
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "JOIN drivers d ON cd.driver_id = d.id "
                + "WHERE cd.driver_id = ? AND c.deleted = FALSE AND d.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectCarStatement = connection.prepareStatement(selectCars)) {
            selectCarStatement.setLong(1, driverId);
            ResultSet carResultSet = selectCarStatement.executeQuery();
            while (carResultSet.next()) {
                Car car = getCar(carResultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars by driver id " + driverId, e);
        }
        for (Car car : cars) {
            car.setDrivers(getCarDrivers(car.getId()));
        }
        return cars;
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("id", Long.class);
        String carModel = resultSet.getObject("model", String.class);
        Car car = new Car(carModel, getManufacturer(resultSet));
        car.setId(carId);
        return car;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String manufacturerName = resultSet.getObject("name", String.class);
        String manufacturerCountry = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }

    private List<Driver> getCarDrivers(Long id) {
        List<Driver> drivers = new ArrayList<>();
        String selectDrivers = "SELECT * FROM cars_drivers cd "
                + "JOIN drivers d ON cd.driver_id = d.id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectDriversStatement
                        = connection.prepareStatement(selectDrivers)) {
            selectDriversStatement.setLong(1, id);
            ResultSet driversResultSet = selectDriversStatement.executeQuery();
            while (driversResultSet.next()) {
                Long driverId = driversResultSet.getObject("id", Long.class);
                String name = driversResultSet.getObject("name", String.class);
                String licenseNumber = driversResultSet.getObject("license_number", String.class);
                Driver driver = new Driver(name, licenseNumber);
                driver.setId(driverId);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get drivers by car id " + id, e);
        }
        return drivers;
    }
}
