package br.com.wszd.jboard.config.security;

import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users usersExists = userRepository.findByEmailFetchRoles(email);

        return UserPrincipal.create(usersExists);
    }
}
