package vkudryashov.webserver.model;

import java.util.HashSet;
import java.util.Set;

public class RestUser {
    private Long id;
    private String username;
    private Set<String> restRoles;

    public RestUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        Set<String> roleNames = new HashSet<>();
        for (Role role :user.getRoles()) {
            roleNames.add(role.getName());
        }
        this.restRoles = roleNames;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRestRoles() {
        return restRoles;
    }
}
