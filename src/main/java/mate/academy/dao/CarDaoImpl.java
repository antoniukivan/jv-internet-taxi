package mate.academy.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.model.Car;

@Dao
public class CarDaoImpl implements CarDao {
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
        Car oldValue = get(car.getId()).get();
        Storage.cars.remove(oldValue);
        Storage.cars.add(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(car -> Objects.equals(id, car.getId()));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return getAll().stream()
                .filter(car -> car.getDrivers().stream()
                        .anyMatch(driver -> Objects.equals(driver.getId(), driverId)))
                .collect(Collectors.toList());
    }
}
