package com.gelo.amo_labs.service;

import com.gelo.amo_labs.model.User;
import com.gelo.amo_labs.web.dto.UserRegistrationDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
