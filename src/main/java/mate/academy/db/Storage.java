package mate.academy.db;

import mate.academy.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static long manufacturerId = 0;
    public static final List<Manufacturer> manufacturers = new ArrayList<>();

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }
}
