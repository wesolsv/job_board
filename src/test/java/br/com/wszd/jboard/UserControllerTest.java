package br.com.wszd.jboard;

import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest{

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

//    @BeforeEach
//    public void setUp() throws Exception {
//        createPerson();
//    }
    @Test
    public void shouldCreateUser() throws Exception {
        Person person = new Person();

        Users user = new Users.Builder()
                .email("wes@test2e.com.br")
                .password("123456")
                .personId(person)
                .companyId(null)
                .build();

        when(repository.save(user)).thenReturn(user);
        user = service.createUser(user);

        assertNotNull(user);
        assertEquals("wes@test2e.com.br", user.getEmail());
    }
}
