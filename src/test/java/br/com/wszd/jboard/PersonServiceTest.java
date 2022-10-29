package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.LogService;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Test
    public void shouldCreatePerson() throws Exception {
        Person person = createPersonTest();

        when(repository.save(person)).thenReturn(person);
        Person obj = service.savePerson(person);

        assertNotNull(obj);
        assertEquals("wes@test2e.com.br", obj.getEmail());
    }

    @Test
    public void shouldGetPerson() throws Exception {
        Person person = createPersonTest();

        when(repository.findById(anyLong())).thenReturn(Optional.of(person));
        Person p = service.getPerson(person.getId());

        Assertions.assertEquals(Person.class, p.getClass());
    }

    public Person createPersonTest(){
        Person person = new Person();
        person.setId(1L);
        person.setName("Wesley");
        person.setPhone("(34)991307618");
        person.setEmail("wes@test2e.com.br");
        person.setCpf("12096105472");
        person.setPassword("123456");
        person.setJob(new Job());
        person.setUser(new Users());

        return person;
    }

}
