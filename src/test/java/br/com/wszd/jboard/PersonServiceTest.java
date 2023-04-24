package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.service.EmailService;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PersonServiceTest {

    @MockBean
    private PersonRepository repository;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailService emailService;

    @Autowired
    private PersonService service;

    @Test
    public void shouldCreatePerson() throws Exception {

        Person persont = mock(Person.class);

        when(persont.getEmail()).thenReturn("teste@teste.com");
        when(persont.getCpf()).thenReturn("10023497862");
        when(persont.getPhone()).thenReturn("12345678911");
        when(persont.getPassword()).thenReturn("123456");
        when(repository.save(any(Person.class)))
                .thenAnswer(invocation -> {
                    Person person = invocation.getArgument(0);
                    person.setId(1L); // seta o ID do objeto salvo
                    return person;
                });

        service.createNewPerson(persont);

        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldGetPerson() throws Exception {

        Person persont = mock(Person.class);
        persont.setId(0L);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(persont));
        service.getPerson(persont.getId());

        verify(repository, times(1)).findById(0L);
    }
    @Test
    public void shouldGetAllPerson() throws Exception {

        when(repository.listPerson()).thenReturn(List.of(new PersonDTO()));
        service.getAllPerson();

        verify(repository, times(1)).listPerson();
    }
    @Test
    public void shouldListPersonByCandidacyJobId() throws Exception {

        Optional<PersonDTO> personDTO = null;

        when(repository.listPersonByCandidacyJobId(anyLong())).thenReturn(personDTO);
        service.listPersonByCandidacyJobId(anyLong());

        verify(repository, times(1)).listPersonByCandidacyJobId(anyLong());
    }
    @Test
    public void shouldEditPerson() throws Exception {
        Person persont = mock(Person.class);
        Users user = mock(Users.class);
        user.setPersonId(persont);

        when(persont.getEmail()).thenReturn("teste@teste.com");
        when(userService.getUserByPersonId(persont)).thenReturn(user);
        when(userService.returnEmailUser()).thenReturn(new Users());
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(persont));
        when(repository.save(any(Person.class)))
                .thenAnswer(invocation -> {
                    Person p = invocation.getArgument(0);
                    p.setId(1L); // seta o ID do objeto salvo
                    return p;
                });

        service.editPerson(0L, persont);

        verify(repository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        Person persont = mock(Person.class);
        persont.setId(0L);

        doNothing().when(repository).deleteById(anyLong());
        service.deleteOnePerson(persont.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
