package mate.academy.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.db.Storage;
import mate.academy.model.Car;

public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return Storage.cars.get(Storage.cars.size() - 1);
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
}
