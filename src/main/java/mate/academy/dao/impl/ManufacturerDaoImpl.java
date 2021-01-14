package mate.academy.dao.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import mate.academy.dao.ManufacturerDao;
import mate.academy.db.Storage;
import mate.academy.model.Manufacturer;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return Storage.manufacturers.get(Storage.manufacturers.size() - 1);
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter(m -> Objects.equals(id, m.getId()))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer oldValue = get(manufacturer.getId()).get();
        Storage.manufacturers.remove(oldValue);
        Storage.manufacturers.add(manufacturer);
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(x -> Objects.equals(x.getId(), id));
    }
}
