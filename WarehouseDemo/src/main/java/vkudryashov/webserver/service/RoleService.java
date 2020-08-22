package vkudryashov.webserver.service;

import vkudryashov.webserver.model.Role;

/**
 * Service interface for {@link Role}
 *
 * @author Vladimir Kudryashov
 * version 1.0
 * */
public interface RoleService {
    void save(Role role);
    Role findByName(String name);
}
