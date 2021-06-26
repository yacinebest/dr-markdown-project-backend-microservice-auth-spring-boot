package com.besttocode.auth.initialise;

import com.besttocode.auth.daos.RoleDAO;
import com.besttocode.auth.daos.UserDAO;
import com.besttocode.auth.models.RoleModel;
import com.besttocode.auth.models.UserModel;
import com.besttocode.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Profile({"dev", "test"})
@Component
public class InitialiseTestData {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        addRoles();
        addUsers();
    }

    private void addUsers() {
        userDAO.deleteAll();

        UserModel admin = new UserModel();
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Collections.singletonList("ADMIN"));
        tokenService.generateToken(admin);
        admin.setDisplayName("admin");

        userDAO.save(admin);

        UserModel user = new UserModel();
        user.setUsername("user");
        user.setEmail("user@user.com");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(Collections.singletonList("USER"));
        tokenService.generateToken(user);
        admin.setDisplayName("user");

        userDAO.save(user);

    }

    private void addRoles() {


        roleDAO.deleteAll();

        RoleModel adminRole = new RoleModel();
        adminRole.setRole("ADMIN");
        RoleModel userRole = new RoleModel();
        userRole.setRole("USER");

        roleDAO.save(adminRole);
        roleDAO.save(userRole);

    }

}
