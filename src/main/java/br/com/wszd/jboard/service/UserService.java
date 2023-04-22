package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.security.JWTCreator;
import br.com.wszd.jboard.security.JWTObject;
import br.com.wszd.jboard.security.SecurityConfig;
import br.com.wszd.jboard.util.LogStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private LogService logService;

  public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  public Users addRoleInUser(UserRoleDTO userRoleDTO) {
    log.info("Adicionando role ao usuario");
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
    log.info("Deletando usuario");
    userRepository.deleteById(id);
  }

  public Users findByEmail(String email) {
    log.info("Buscando usuario por email");
    return userRepository.findByEmail(email);
  }

  public Users createUser(Users user) {
    log.info("Salvando novo usuario");
    return userRepository.save(user);
  }

  public <T> void createUsers(T entity){
    log.info("Criando usuario");
    List<Long> listIdRoles = Arrays.asList(3L);
    Users userT = null;
    if (entity instanceof Person) {
      userT = new Users.Builder()
              .email(((Person) entity).getEmail())
              .password(((Person) entity).getPassword())
              .personId((Person) entity)
              .companyId(null)
              .build();
    }else{
      userT = new Users.Builder()
              .email(((Company) entity).getEmail())
              .password(((Company) entity).getPassword())
              .personId(null)
              .companyId((Company) entity)
              .build();
    }

    //Criando usuário no repositorio
    Users user = userRepository.save(userT);

    //Criando e atribuindo a role ao user
    UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
    addRoleInUser(userRoleDTO);

    //Enviando Email
    emailService.sendEmailToUserCreateUsers(user);

    //Criando log de inserção
    createLog(entity.toString(), "/"+entity.getClass().getSimpleName(), user.getId(), LogStatus.SUCESSO, HttpMethod.POST.toString());
  }

  public Users getUserByPersonId(Person person) {
    log.info("Buscando usuario por pessoa id");
    return userRepository.findByPersonId(person);
  }

  public Users getUserByCompanyId(Company company) {
    log.info("Buscando usuario por empresa id");
    return userRepository.findByCompanyId(company);
  }

  public SessaoDTO validLogin(Users user, UserLoginDTO infoLogin) {
    log.info("Validando senha e retornando sessao");
    if(user!=null) {
      boolean passwordOk = passwordEncoder().matches(infoLogin.getPassword(), user.getPassword());
      if (!passwordOk) {
        throw new ResourceBadRequestException("Senha incorreta para o email: " + infoLogin.getEmail());
      }

      //Cria o objeto de sessão para retornar email e token do usuario
      SessaoDTO sessaoDTO = new SessaoDTO();
      sessaoDTO.setLogin(user.getEmail());

      JWTObject jwtObject = new JWTObject();
      jwtObject.setSubject(user.getEmail());
      jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
      jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
      jwtObject.setRoles(user.getRoles());

      sessaoDTO.setToken(JWTCreator.createToken(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

      return sessaoDTO;
    }else {
      throw new ResourceBadRequestException("Erro ao tentar fazer login");
    }
  }

  public void editUser(Users user) {
    log.info("Editando usuario");
    userRepository.save(user);
  }

  public Users returnEmailUser(){
    Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return findUserByName(email+"");
  }

  public Users findUserByName(String nomeUsuario){

    try{
      return userRepository.findByEmail(nomeUsuario);
    }catch (ResourceObjectNotFoundException e){
      throw new ResourceObjectNotFoundException("Usuário não encotrado");
    }

  }

  public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method) {
    log.info("Gerando Log");
    LogTable log = new LogTable.Builder()
            .payload(payload)
            .endpoint(endpoint)
            .userId(userId)
            .status(status)
            .dataInclusao(LocalDateTime.now())
            .method(method)
            .build();

    logService.createLog(log);
  }

}
