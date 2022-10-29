package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;
    @MockBean
    private UserRepository repository;

    @Test
    public void shouldDoLogin() throws Exception {
        String jsonString = "{\"email\": \"wes@teste.com.br\", \"password\": \"123456\"}";

       mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andDo(print())
                .andReturn();

    }
}
