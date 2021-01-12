package mate.academy.service;

import mate.academy.dao.CarDao;
import mate.academy.dao.DriverDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Car;
import mate.academy.model.Driver;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;
    @Inject
    private DriverDao driverDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).orElseThrow();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        car.getDrivers().add(driver);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        car.getDrivers().removeIf(x -> Objects.equals(driver, x));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAll().stream()
                .filter(car -> car.getDrivers().contains(driverDao.get(driverId).orElseThrow()))
                .collect(Collectors.toList());
    }
}