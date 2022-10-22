package br.com.wszd.jboard.service;

import br.com.wszd.jboard.security.UserDetailData;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> user = repository.findByEmailFetchRoles(email);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário [ " +email + " ] não encontrado");
        }

        return UserDetailData.create(user);
    }
}
