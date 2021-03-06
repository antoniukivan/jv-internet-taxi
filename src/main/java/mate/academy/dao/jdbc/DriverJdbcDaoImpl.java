package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.DriverDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Driver;
import mate.academy.util.ConnectionUtil;

@Dao
public class DriverJdbcDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers (name, license_number, login, password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement
                         = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("The driver with this login already exist: "
                    + driver.getLogin(), e);
        }
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers WHERE id = ? "
                + "AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
        return drivers;
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers SET name = ?, license_number = ?, login = ?, password = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setLong(3, driver.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver " + driver, e);
        }
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver by id " + id, e);
        }
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        String query = "SELECT * FROM drivers WHERE login = ? "
                + "AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by login " + login, e);
        }
        return Optional.empty();
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Long driverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String licenseNumber = resultSet.getObject("license_number", String.class);
        String login = resultSet.getObject("login", String.class);
        String password = resultSet.getObject("password", String.class);
        Driver driver = new Driver(name, licenseNumber, login, password);
        driver.setId(driverId);
        return driver;
    }
}
