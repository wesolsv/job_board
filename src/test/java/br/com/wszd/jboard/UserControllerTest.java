package br.com.wszd.jboard;

import br.com.wszd.jboard.controller.UserController;
import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.model.Role;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.security.JWTFilter;
import br.com.wszd.jboard.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(value = UserController.class, includeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class)})
@RunWith(SpringRunner.class)
public class UserControllerTest{

    @Autowired
    private MockMvc mock;
    @MockBean
    private TestEntityManager entityManager;
    @MockBean
    private UserService service;

    @MockBean
    private UserRepository userRepository;

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

        SessaoDTO sessao = service.validLogin(user, usuario);

        System.out.println(sessao);

        entityManager.persistAndFlush(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        ResultMatcher expectedResult = MockMvcResultMatchers.status().isOk();

        mock.perform(request).andExpect(expectedResult);
        System.out.println("passou");
    }

    @Test
    public void shouldBeReturnBadRequest() throws Exception {
        URI path = new URI("/auth");
        String json = "{\"email\": \"invalido@email.com\", \"password\": \"123456\"}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        ResultMatcher expectedResult = MockMvcResultMatchers.status().isBadRequest();

        mock.perform(request).andExpect(expectedResult);
    }
}
