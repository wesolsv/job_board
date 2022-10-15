package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateRoleUserService {

  @Autowired
  PersonRepository personRepository;

  public Person execute(UserRoleDTO userRoleDTO) {

    Optional<Person> userExists = personRepository.findById(userRoleDTO.getIdUser());
    List<Role> roles = new ArrayList<>();

    if (userExists.isEmpty()) {
      throw new Error("User does not exists!");
    }

    roles = userRoleDTO.getIdsRoles().stream().map(role -> {
      return new Role(role);
    }).collect(Collectors.toList());

    Person user = userExists.get();

    user.setRoles(roles);

    personRepository.save(user);

    return user;

  }
}
