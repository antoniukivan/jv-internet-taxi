package mate.academy.service;

import mate.academy.dao.ManufacturerDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Manufacturer;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
