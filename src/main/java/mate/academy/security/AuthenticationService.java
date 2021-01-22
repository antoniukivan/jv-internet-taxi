package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.model.Driver;

import java.util.Optional;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
