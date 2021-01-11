package mate.academy.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer != null) {
            Storage.addManufacturer(manufacturer);
        }
        return manufacturer == null ? null
                : Storage.manufacturers.get(Storage.manufacturers.size() - 1);
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
        Manufacturer oldValue = get(manufacturer.getId()).orElseThrow();
        Storage.manufacturers.remove(oldValue);
        Storage.manufacturers.add(manufacturer);
        return oldValue;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.remove(get(id).orElseThrow(() ->
                new RuntimeException("Can't find Manufacturer with this id: " + id)));
    }
}
