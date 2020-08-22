package vkudryashov.webserver.service;

import org.springframework.security.core.Authentication;

/**
 * Service for Security
 * @author Vladimir Kudryashov
 * @version 1.0
 * */


public interface SecurityService {
    Authentication findLoggedInUser();
    void autoLogin(String userName, String password);
}
