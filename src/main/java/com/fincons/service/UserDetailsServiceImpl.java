package com.fincons.service;

import com.fincons.entity.User;
import com.fincons.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;
    //recuperiamo l'oggetto user e se esiste lo ritorna
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    // TODO
                    Arrays.asList(new SimpleGrantedAuthority(""), new SimpleGrantedAuthority(""))
                    );
        }
    }
}


