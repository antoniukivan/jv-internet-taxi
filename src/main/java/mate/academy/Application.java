package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;
import mate.academy.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer tesla = new Manufacturer("Tesla", "USA");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        manufacturerService.create(tesla);
        manufacturerService.create(toyota);
        manufacturerService.create(ford);
        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.get(2L));
        System.out.println(manufacturerService.get(3L));
        System.out.println();
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        honda.setId(3L);
        manufacturerService.update(honda);
        manufacturerService.getAll().forEach(System.out::println);
        System.out.println();

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver vlad = new Driver("Vlad", "#22543564");
        Driver oleg = new Driver("Oleg", "#52643574");
        Driver alex = new Driver("Alex", "#34553578");
        driverService.create(vlad);
        driverService.create(oleg);
        driverService.create(alex);
        System.out.println(driverService.get(1L));
        System.out.println(driverService.get(2L));
        System.out.println(driverService.get(3L));
        System.out.println();
        Driver john = new Driver("John", "#88551571");
        john.setId(1L);
        driverService.update(john);
        driverService.delete(3L);
        driverService.getAll().forEach(System.out::println);
        System.out.println();

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car teslaModel3 = new Car("Model3", tesla);
        Car toyotaRav4 = new Car("RAV4", toyota);
        Car fordMustang = new Car("Mustang", ford);
        carService.create(teslaModel3);
        carService.create(toyotaRav4);
        carService.create(fordMustang);
        carService.addDriverToCar(vlad, teslaModel3);
        carService.addDriverToCar(oleg, teslaModel3);
        carService.addDriverToCar(alex, teslaModel3);
        carService.addDriverToCar(oleg, toyotaRav4);
        carService.addDriverToCar(john, fordMustang);
        System.out.println(carService.get(1L));
        System.out.println(carService.get(2L));
        System.out.println(carService.get(3L));
        System.out.println();
        Car hondaAccord = new Car("Accord", honda);
        hondaAccord.setId(2L);
        carService.addDriverToCar(john, hondaAccord);
        carService.addDriverToCar(oleg, hondaAccord);
        carService.removeDriverFromCar(john, hondaAccord);
        carService.update(hondaAccord);
        carService.delete(3L);
        carService.getAll().forEach(System.out::println);
        System.out.println();
        carService.getAllByDriver(2L).forEach(System.out::println);
    }
}
