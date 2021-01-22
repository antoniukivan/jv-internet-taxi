package mate.academy.service;

import mate.academy.model.Driver;

import java.util.Optional;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
