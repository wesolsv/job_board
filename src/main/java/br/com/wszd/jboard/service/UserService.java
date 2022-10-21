package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public Users execute(UserRoleDTO userRoleDTO) {

    Optional<Users> userExists = userRepository.findById(userRoleDTO.getIdUser());
    List<Role> roles = new ArrayList<>();

    if (userExists.isEmpty()) {
      throw new Error("User does not exists!");
    }

    roles = userRoleDTO.getIdsRoles().stream().map(role -> {
      return new Role(role);
    }).collect(Collectors.toList());

    Users users = userExists.get();

    users.setRoles(roles);

    userRepository.save(users);

    return users;
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public Users findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Users createUser(Users user) {
    return userRepository.save(user);
  }

  public Users getUserByPersonId(Person id) {
    return userRepository.findByPersonId(id);
  }
}
