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
import java.util.Optional;

@Profile({"prod"})
@Component
public class InitialiseProdData {

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

        Optional<UserModel> adminAccount = userDAO.findByUsername("admin");

        if (!adminAccount.isPresent()) {
            UserModel admin = new UserModel();
            admin.setUsername("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Collections.singletonList("ADMIN"));
            tokenService.generateToken(admin);
            admin.setDisplayName("admin");

            userDAO.save(admin);
        }

        Optional<UserModel> userAccount = userDAO.findByUsername("user");

        if (!userAccount.isPresent()) {
            UserModel user = new UserModel();
            user.setUsername("user");
            user.setEmail("user@user.com");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Collections.singletonList("USER"));
            tokenService.generateToken(user);
            user.setDisplayName("user");

            userDAO.save(user);
        }


    }

    private void addRoles() {


        Optional<RoleModel> roleAdmin = roleDAO.findByRole("ADMIN");

        if (!roleAdmin.isPresent()) {
            RoleModel adminRole = new RoleModel();
            adminRole.setRole("ADMIN");

            roleDAO.save(adminRole);
        }


        Optional<RoleModel> roleUser = roleDAO.findByRole("USER");

        if (!roleUser.isPresent()) {
            RoleModel userRole = new RoleModel();
            userRole.setRole("USER");

            roleDAO.save(userRole);

        }

    }
}
