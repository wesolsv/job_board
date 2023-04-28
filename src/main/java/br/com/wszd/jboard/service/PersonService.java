package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.LogTable;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.util.LogStatus;
import br.com.wszd.jboard.util.ValidacaoUsuarioLogged;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private IEmailService emailService;
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

        ValidacaoUsuarioLogged.validEmailUsuario(getPerson(id), userService.returnEmailUser());

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

        person = repository.save(person);;
        userService.createUsers(person);

        return new PersonDTO.Builder()
                .id(person.getId())
                .name(person.getName())
                .phone(person.getPhone())
                .email(person.getEmail())
                .cpf(person.getCpf())
                .build();
    }

    public PersonDTO editPerson(Long id, Person novo){
        log.info("Editando pessoa");
        //Validando a existencia de person com o id informado
        getPerson(id);
        novo.setId(id);

        //validando se a pessoa que está editando pode realizar a ação
        ValidacaoUsuarioLogged.validEmailUsuario(getPerson(id), userService.returnEmailUser());

        //Salvando alteracao de person
        repository.save(novo);

        //Editando email do user de person que foi editado anteriormente
        Users user = userService.getUserByPersonId(getPerson(id));
        user.setEmail(novo.getEmail());
        userService.editUser(user);

        //Salvando o log da edicao efetuada
        createLog(novo.toString(),"/person{" + id +"}",
                userService.getUserByPersonId(getPerson(id)).getId(), LogStatus.SUCESSO, HttpMethod.PUT.toString());

        //Enviando email
        emailService.sendEmailEditUser(novo);

        return new PersonDTO.Builder()
                .id(novo.getId())
                .name(novo.getName())
                .phone(novo.getPhone())
                .email(novo.getEmail())
                .cpf(novo.getCpf())
                .build();
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
}
