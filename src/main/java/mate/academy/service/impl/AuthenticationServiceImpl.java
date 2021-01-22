package mate.academy.service.impl;

import mate.academy.dao.DriverDao;
import mate.academy.lib.Inject;
import mate.academy.model.Driver;
import mate.academy.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverDao driverDao;

    @Override
    public Driver login(String login, String password) {
        return driverDao.findByLogin(login).get();
    }
}
