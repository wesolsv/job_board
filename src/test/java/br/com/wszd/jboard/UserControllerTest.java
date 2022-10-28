package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldDoLogin() throws Exception {
        String json = "{\"email\": \"wes@teste.com.br\", \"password\": \"123456\"}";

        SessaoDTO sessao = new SessaoDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.login").value("wes@teste.com.br"))
                .andExpect(status().isOk());

        //MvcResult result String content = result.getResponse().getContentAsString();
    }
}
