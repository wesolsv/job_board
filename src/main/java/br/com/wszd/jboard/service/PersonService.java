package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.LogTable;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.security.JWTCreator;
import br.com.wszd.jboard.security.UserDetailData;
import br.com.wszd.jboard.util.LogStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserService createRoleUserService;

    @Autowired
    private LogService logService;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<PersonDTO> getAllPerson(){
        log.info("Buscando todas as pessoas");
       return repository.listPerson();
    }

    public PersonDTO getPersonDTO(Long id, HttpServletRequest request){
        log.info("Buscando pessoa");

        Person realPerson = repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Não encontrado id = " + id));

        boolean validAdminAndEmail = validEmailUser(request, realPerson);

        if(!validAdminAndEmail){
            throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
        }

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
                () ->  new ResourceObjectNotFoundException("Pessoa não encontrada com id = " + id));
    }

    public PersonDTO createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

        List<Long> listIdRoles = Arrays.asList(1L);

          if(repository.findByEmail(novo.getEmail()) != null && repository.findByCpf(novo.getCpf()) != null && userService.findByEmail(novo.getEmail())!= null){
              createLog(novo.toString(), "/person", 0L, LogStatus.ERRO, HttpMethod.POST.toString());
              throw new ResourceBadRequestException("Email ou CPF já cadastrado, verfique seus dados");
          }

        Person person = repository.save(new Person.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cpf(novo.getCpf().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build());

        //Criando usuário no repositorio
        Users user = userService.createUser(new Users.Builder()
                .email(person.getEmail())
                .password(person.getPassword())
                .personId(person)
                .companyId(null)
                .build());

        //Criando atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
        createRoleUserService.execute(userRoleDTO);

        //Criando log de inserção
        createLog(novo.toString(), "/person", user.getId(), LogStatus.SUCESSO, HttpMethod.POST.toString());

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
        //Salvando alteracao do usuario
        repository.save(novo);

        //Editando email do usuario editado anteriormente
        Users user = userService.getUserByPersonId(getPerson(id));
        user.setEmail(novo.getEmail());
        userService.editUser(user);

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

    public void deletePerson(Long id){
        log.info("Deletando pessoa");

        //Validando a existencia de person e usuario e deletando ambos
        Person person = getPerson(id);
        Users user = userService.getUserByPersonId(person);
        userService.deleteUser(user.getId());
        repository.deleteById(id);

        //Salvando o log do delete efetuada
        createLog("Delete Person","/person{" + id +"}",
                user.getId(), LogStatus.SUCESSO, HttpMethod.DELETE.toString());
    }

    public Optional<PersonDTO> listPersonByCandidacyJobId(Long id) {
        return repository.listPersonByCandidacyJobId(id);
    }

    public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method){

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

    public boolean validEmailUser(HttpServletRequest request, Person person){
        HttpSession session = request.getSession();
        SecurityContextImpl sec = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Users user = userService.findByEmail((String) sec.getAuthentication().getPrincipal());

        ArrayList<String> rolesRetorno = new ArrayList<>();

        for(int i = 0; i < user.getRoles().size(); i++) {
            String j = user.getRoles().get(i).getName() + "";
            rolesRetorno.add(j);
        }

        if(rolesRetorno.contains("ADMIN") || person.getEmail().equals(user.getEmail())){
            return true;
        }

        return false;
    }
}
