package vkudryashov.webserver.service;

import vkudryashov.webserver.configuration.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementation of (@link SecurityService) interface
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication findLoggedInUser() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        return userDetails.isAuthenticated() ? userDetails : null;
    }
}
