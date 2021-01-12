package mate.academy.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.db.Storage;
import mate.academy.model.Driver;

public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return Storage.drivers.get(Storage.drivers.size() - 1);
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers.stream()
                .filter(driver -> Objects.equals(driver.getId(), id))
                .findAny();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        Driver oldValue = get(driver.getId()).orElseThrow();
        Storage.drivers.remove(oldValue);
        Storage.drivers.add(driver);
        return oldValue;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(driver -> Objects.equals(id, driver.getId()));
    }
}
