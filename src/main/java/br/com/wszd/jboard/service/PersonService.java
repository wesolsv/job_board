package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService createRoleUserService;

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
                () ->  new ObjectNotFoundException("Não encontrado id = " + id));

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
                () ->  new ObjectNotFoundException("Não encontrado id = " + id));
    }

    public PersonDTO createNewPerson(Person novo) {
        log.info("Adicionando nova pessoa");

        List<Long> listIdRoles = Arrays.asList(1L);

        try{
            //Validando a existencia do email ou cnpj nas tabelas de company ou users
            repository.findByEmail(novo.getEmail());
            repository.findByCpf(novo.getCpf());
            userRepository.findByEmail(novo.getEmail());

        }catch (BadRequestException e){
            throw new BadRequestException("Email ou CNPJ já cadastrado, verfique seus dados");
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
        Users user = userRepository.save(new Users.Builder()
                .email(person.getEmail())
                .password(person.getPassword())
                .personId(person)
                .companyId(null)
                .build());

        //Criando atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
        createRoleUserService.execute(userRoleDTO);

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
        getPerson(id);
        novo.setId(id);

        repository.save(novo);

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
        getPerson(id);
        repository.deleteById(id);
    }
}
