package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.LogTable;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.security.JWTFilter;
import br.com.wszd.jboard.util.LogStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private PersonRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;


    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<PersonDTO> getAllPerson(){
        log.info("Buscando todas as pessoas");
       return repository.listPerson();
    }

    public PersonDTO getPersonDTO(Long id){
        log.info("Buscando pessoa");

        Person realPerson = repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Não encontrado id = " + id));

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(realPerson, email.toString());

        return new PersonDTO.Builder()
                .id(realPerson.getId())
                .name(realPerson.getName())
                .phone(realPerson.getPhone())
                .email(realPerson.getEmail())
                .cpf(realPerson.getCpf())
                .build();
    }

    public Person getPerson(Long id){
        log.info("Buscando pessoa");
        return repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Não encontrada com id = " + id));
    }

    public void createUser(Person person) {
        log.info("Criando usuario");
        List<Long> listIdRoles = Arrays.asList(1L);

        //Criando usuário no repositorio
        Users user = userService.createUser(new Users.Builder()
                .email(person.getEmail())
                .password(person.getPassword())
                .personId(person)
                .companyId(null)
                .build());

        //Criando e atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
        userService.addRoleInUser(userRoleDTO);

        //Enviando Email
        emailService.sendEmailToUserCreateUsers(user);

        //Criando log de inserção
        createLog(person.toString(), "/person", user.getId(), LogStatus.SUCESSO, HttpMethod.POST.toString());
    }

    public PersonDTO createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

          if(repository.findByEmail(novo.getEmail()) != null && repository.findByCpf(novo.getCpf()) != null && userService.findByEmail(novo.getEmail())!= null){
              createLog(novo.toString(), "/person", 0L, LogStatus.ERRO, HttpMethod.POST.toString());
              throw new ResourceBadRequestException("Email ou CPF já cadastrado, verfique seus dados");
          }

        Person person = new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build();

        person = savePerson(person);

        createUser(person);

        return new PersonDTO.Builder()
                .id(person.getId())
                .name(person.getName())
                .phone(person.getPhone())
                .email(person.getEmail())
                .cpf(person.getCpf())
                .build();
    }

    public Person savePerson(Person novo) {
        log.info("Salvando pessoa");
        return repository.save(novo);
    }

    public PersonDTO editPerson(Long id, Person novo){
        log.info("Editando pessoa");
        //Validando a existencia de person com o id informado
        getPerson(id);
        novo.setId(id);

        //validando se a pessoa que está editando pode realizar a ação
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(getPerson(id), email.toString());

        //Salvando alteracao do usuario
        saveEditPerson(novo);

        //Editando email do user de person que foi editado anteriormente
        Users user = userService.getUserByPersonId(getPerson(id));
        user.setEmail(novo.getEmail());
        userService.editUser(user);

        //Enviando email
        emailService.sendEmailEditUser(user);

        //Salvando o log da edicao efetuada
        createLog(novo.toString(),"/person{" + id +"}",
                userService.getUserByPersonId(getPerson(id)).getId(), LogStatus.SUCESSO, HttpMethod.PUT.toString());

        return new PersonDTO.Builder()
                .id(novo.getId())
                .name(novo.getName())
                .phone(novo.getPhone())
                .email(novo.getEmail())
                .cpf(novo.getCpf())
                .build();
    }

    public Person saveEditPerson(Person novo){
        log.info("Salvando pessoa editada");
        return repository.save(novo);
    }

    public void deletePerson(Long id){
        log.info("Deletando pessoa");

        //Validando a existencia de person e usuario e deletando ambos
        Person person = getPerson(id);
        Users user = userService.getUserByPersonId(person);
        if(user != null) {
            userService.deleteUser(user.getId());
        }
        deleteOnePerson(id);

        //Salvando o log do delete efetuada
        createLog("Delete Person","/person{" + id +"}",
                user.getId(), LogStatus.SUCESSO, HttpMethod.DELETE.toString());
    }

    public void deleteOnePerson(Long id){
        log.info("Deletando uma pessoa");
        repository.deleteById(id);
    }

    public Optional<PersonDTO> listPersonByCandidacyJobId(Long id) {
        log.info("Listando candidaturas por pessoa");
        return repository.listPersonByCandidacyJobId(id);
    }

    public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method){
        log.info("Criando log");
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

    public void validEmailUser(Person person, String emailRequest){
        log.info("Validando usuario");
        //Validando se o email do usuario da requisicao é ADMIN ou pertence ao id para a qual foi feita a requisicao

        Users user = userService.findByEmail(emailRequest);

        ArrayList<String> rolesRetorno = new ArrayList<>();

        for(int i = 0; i < user.getRoles().size(); i++) {
            String j = user.getRoles().get(i).getName() + "";
            rolesRetorno.add(j);
        }

        if(rolesRetorno.contains("ADMIN") || person.getId().equals(user.getPersonId().getId())){
            log.info("Validado email do usuario ou usuario é admin");
        }else {
            throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
        }
    }
}
