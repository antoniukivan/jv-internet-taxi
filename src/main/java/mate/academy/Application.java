package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Manufacturer;
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
        manufacturerService.delete(1L);
        manufacturerService.getAll().forEach(System.out::println);
    }
}
