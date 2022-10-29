package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void shouldCreateUser() throws Exception {
        Users user = new Users.Builder()
                .email("wes@test2e.com.br")
                .password("123456")
                .personId(new Person())
                .companyId(null)
                .build();

        when(repository.save(user)).thenReturn(user);
        user = service.createUser(user);

        assertNotNull(user);
        assertEquals("wes@test2e.com.br", user.getEmail());
    }

    @Test
    public void shouldLogin() throws Exception {
        List<Role> roles = Arrays.asList(new Role(1L, "ADMIN"));

        Users user = new Users.Builder()
                .email("wes@test2e.com.br")
                .password("$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK")
                .personId(new Person())
                .companyId(null)
                .build();

        user.setRoles(roles);

        UserLoginDTO login = new UserLoginDTO("wes@test2e.com.br","123456");

        SessaoDTO session = new SessaoDTO();

        session = service.validLogin(user, login);

        assertNotNull(session);
        assertEquals("wes@test2e.com.br", session.getLogin());
    }
}
