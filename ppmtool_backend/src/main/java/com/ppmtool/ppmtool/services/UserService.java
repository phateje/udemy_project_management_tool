package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserRepository userRepository;
}
