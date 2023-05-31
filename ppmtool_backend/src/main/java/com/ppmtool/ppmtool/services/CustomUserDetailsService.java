package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return u;
    }

    @Transactional
    public User loadUserById(Long id) {
        User u = userRepository.getById(id);
        if (u == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return u;
    }
}
