package br.com.wszd.jboard.config;

import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class SecurityDataBaseService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person personEntity = personRepository.findByEmail(email);
        if(personEntity == null){
            throw new ObjectNotFoundException("Usuário não encontrado com o email " + email);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        return null;
    }
}
