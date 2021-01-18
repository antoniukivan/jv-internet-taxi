package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.ManufacturerDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Manufacturer;
import mate.academy.util.ConnectionUtil;

@Dao
public class ManufacturerJdbcDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers (name, country) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to the DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET deleted = TRUE "
                + "WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer;
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        manufacturer = new Manufacturer(name, country);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }
}
