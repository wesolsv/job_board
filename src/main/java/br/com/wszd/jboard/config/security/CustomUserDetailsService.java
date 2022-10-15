package br.com.wszd.jboard.config.security;

import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person personExists = personRepository.findByEmailFetchRoles(email);

        return UserPrincipal.create(personExists);
    }
}
