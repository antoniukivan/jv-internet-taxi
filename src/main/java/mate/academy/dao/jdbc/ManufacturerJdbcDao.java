package mate.academy.dao.jdbc;

import mate.academy.dao.ManufacturerDao;
import mate.academy.model.Manufacturer;
import mate.academy.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ManufacturerJdbcDao implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try(Connection connection = ConnectionUtil.getConnection()) {

        } catch (SQLException e) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
