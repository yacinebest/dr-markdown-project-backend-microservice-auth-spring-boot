package com.besttocode.auth.controller;

import com.besttocode.auth.dtos.UserInfoDTO;
import com.besttocode.auth.dtos.UserLoginDTO;
import com.besttocode.auth.services.UserService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ANONYMOUS','ADMIN')")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO) {

        Preconditions.checkNotNull(userInfoDTO);

        userService.createUser(userInfoDTO);
        return userInfoDTO;
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyRole('ANONYMOUS','USER','ADMIN')")
    public UserInfoDTO getUserInfo(@PathVariable String id, HttpServletRequest request) {
        return userService.retrieveUserInfo(id);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('ANONYMOUS')")
    public UserInfoDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        Preconditions.checkNotNull(userLoginDTO);
        return userService.loginUser(userLoginDTO);
    }


//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable String id) {
//
//        log.info("deleting user by id: " + id);
//    }
//
//    @PutMapping("/update/{id}")
//    public UserInfoDTO updateUser(@PathVariable String id, @RequestBody UserInfoDTO userInfoDTO) {
//        log.info("updating user with id: " + id);
//        return null;
//    }
}
