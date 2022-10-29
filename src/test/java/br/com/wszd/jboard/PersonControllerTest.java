package br.com.wszd.jboard;

import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;
    @MockBean
    private TestEntityManager entityManager;
    @MockBean
    private PersonService service;

    @MockBean
    private PersonRepository userRepository;


}
