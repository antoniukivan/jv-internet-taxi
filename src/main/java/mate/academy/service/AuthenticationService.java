package mate.academy.service;

import mate.academy.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password);
}
