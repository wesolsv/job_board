//package br.com.wszd.jboard.config.security;
//
//import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
//import br.com.wszd.jboard.model.Person;
//import br.com.wszd.jboard.repository.PersonRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class SecurityDataBaseService implements UserDetailsService {
//
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws ObjectNotFoundException {
//        Person personEntity = personRepository.findByEmail(email);
//        if(personEntity == null){
//            throw new ObjectNotFoundException("Usuário não encontrado com o email " + email);
//        }
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        personEntity.getRoles().forEach(role ->{
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        });
//
//        UserDetails person = new org.springframework.security.core.userdetails.User(personEntity.getEmail()
//                , personEntity.getPassword(), authorities);
//        return person;
//    }
//}
