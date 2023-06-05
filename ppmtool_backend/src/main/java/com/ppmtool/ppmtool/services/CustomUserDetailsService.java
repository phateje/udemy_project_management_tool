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

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return u;
    }

    public User loadUserById(Long id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            return u.get();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
