package mate.academy.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.Car;

@Dao
public class CarDaoImpl implements CarDao {
    @Inject
    private DriverDao driverDao;

    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(car -> Objects.equals(car.getId(), id))
                .findAny();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        Car oldValue = get(car.getId()).orElseThrow();
        Storage.cars.remove(oldValue);
        Storage.cars.add(car);
        return oldValue;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(car -> Objects.equals(id, car.getId()));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return getAll().stream()
                .filter(car -> car.getDrivers().contains(driverDao.get(driverId).orElseThrow()))
                .collect(Collectors.toList());
    }
}
