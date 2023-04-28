package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.EmailServiceImpl;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @MockBean
    private EmailServiceImpl emailService;

    @Autowired
    private UserService service;

    @Test
    public void shouldCreateUserPerson() throws Exception {
        Person person = mock(Person.class);
        when(person.getEmail()).thenReturn("email@email.com");
        when(person.getPassword()).thenReturn("123456");

        Users user = mock(Users.class);

        when(user.getId()).thenReturn(0L);
        when(repository.save(any(Users.class)))
                .thenAnswer(invocation -> {
                    Users u = invocation.getArgument(0);
                    u.setId(1L); // seta o ID do objeto salvo
                    return u;
                });
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.createUsers(person);

        verify(repository, times(2)).save(any(Users.class));
    }

    @Test
    public void shouldCreateUserCompany() throws Exception {
        Company company = mock(Company.class);
        when(company.getEmail()).thenReturn("email@email.com");
        when(company.getPassword()).thenReturn("123456");

        Users user = mock(Users.class);

        when(user.getId()).thenReturn(0L);
        when(repository.save(any(Users.class)))
                .thenAnswer(invocation -> {
                    Users u = invocation.getArgument(0);
                    u.setId(1L); // seta o ID do objeto salvo
                    return u;
                });
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.createUsers(company);

        verify(repository, times(2)).save(any(Users.class));
    }

    @Test
    public void shouldLogin() throws Exception {
        List<Role> roles = Arrays.asList(new Role(1L, "ADMIN"));

        Users user = mock(Users.class);

        when(user.getEmail()).thenReturn("wes@test2e.com.br");
        when(user.getPassword()).thenReturn("$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK");
        when(user.getPersonId()).thenReturn(new Person());
        when(user.getRoles()).thenReturn(roles);

        UserLoginDTO login = mock(UserLoginDTO.class);
        when(login.getEmail()).thenReturn("wes@test2e.com.br");
        when(login.getPassword()).thenReturn("123456");

        when(repository.findByEmail(anyString())).thenReturn(user);

        service.validLogin(login);

        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    public void shouldGetUserByPersonId() throws Exception {

        Person persont = mock(Person.class);
        persont.setId(0L);

        Users user = mock(Users.class);

        when(repository.findByPersonId(persont)).thenReturn(user);
        service.getUserByPersonId(persont);

        verify(repository, times(1)).findByPersonId(persont);
    }

    @Test
    public void shouldGetUserByEmail() throws Exception {

        Users user = mock(Users.class);
        String email = "teste@teste.com";

        when(repository.findByEmail(email)).thenReturn(user);
        service.findByEmail(email);

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    public void shouldGetUserByCompanyId() throws Exception {

        Company company = mock(Company.class);
        company.setId(0L);

        Users user = mock(Users.class);

        when(repository.findByCompanyId(company)).thenReturn(user);
        service.getUserByCompanyId(company);

        verify(repository, times(1)).findByCompanyId(company);
    }

    @Test
    public void editarUsuario() throws Exception {

        Users users = mock(Users.class);
        service.editUser(users);

        verify(repository, times(1)).save(users);
    }
}
