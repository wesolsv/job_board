package br.com.wszd.jboard;

import br.com.wszd.jboard.controller.PersonController;
import br.com.wszd.jboard.controller.UserController;
import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.security.JWTFilter;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@WebMvcTest(value = PersonController.class, includeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class)})
@RunWith(SpringRunner.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;
    @MockBean
    private TestEntityManager entityManager;
    @MockBean
    private PersonService service;

    @MockBean
    private PersonRepository userRepository;

    @Test
    public void shouldBeReturnValidToken() throws Exception {
        URI path = new URI("/api/v1/users/login");
        String json = "{\"email\": \"wes@teste.com.br\", \"password\": \"123456\"}";

        UserLoginDTO usuario = new UserLoginDTO();
        usuario.setEmail("wes@teste.com.br");
        usuario.setPassword("123456");

        Users user = new Users();
        user.setEmail("wes@teste.com.br");
        user.setPassword("123456");

        //SessaoDTO sessao = service.validLogin(user, usuario);

        entityManager.persistAndFlush(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        ResultMatcher expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
        System.out.println("passou");
    }

}
