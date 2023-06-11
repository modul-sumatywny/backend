package restaurant.model;


import org.springframework.security.access.prepost.PreAuthorize;

public enum Role {
    MANAGER,
    CLIENT,
    ADMIN,
    EMPLOYEE
}

