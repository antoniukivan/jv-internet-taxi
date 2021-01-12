package mate.academy.db;

import java.util.ArrayList;
import java.util.List;

import mate.academy.model.Car;
import mate.academy.model.Manufacturer;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    private static Long manufacturerId = 0L;
    private static Long carId = 0L;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void addCar(Car car) {
        car.setId(++carId);
        cars.add(car);
    }
}
