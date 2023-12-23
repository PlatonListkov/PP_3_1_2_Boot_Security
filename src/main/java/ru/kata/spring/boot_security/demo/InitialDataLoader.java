package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialDataLoader {

    private UserService userService;
    private RoleService roleService;

    public InitialDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void fillDataBase() {

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleService.add(adminRole);
        roleService.add(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User user1 = new User("User1", "User1LastNane", 11, "user.1@mail.mail", "user1", "user1");
        User user2 = new User("User2", "User2LastNane", 12, "user.2@mail.mail", "user2", "user2");
        User user3 = new User("User3", "User3LastNane", 13, "user.3@mail.mail", "user3", "user3");
        User user4 = new User("User4", "User4LastNane", 14, "user.4@mail.mail", "user4", "user4");
        User user5 = new User("User5", "User5LastNane", 15, "user.5@mail.mail", "user5", "user5");

        User admin = new User("Admin", "AdminLastNane", 21, "admin@mail.mail", "admin", "admin");

        user1.setRoles(userRoles);
        user2.setRoles(userRoles);
        user3.setRoles(userRoles);
        user4.setRoles(userRoles);
        user5.setRoles(userRoles);

        admin.setRoles(adminRoles);

        userService.saveUser(admin);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userService.saveUser(user5);
    }
}
