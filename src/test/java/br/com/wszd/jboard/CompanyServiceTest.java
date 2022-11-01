package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class CompanyServiceTest {

    @Mock
    private CompanyRepository repository;

    @Mock
    private CandidacyService candidacyService;

    @Mock
    private CandidacyRepository candidacyRepository;

    @InjectMocks
    private CompanyService service;

    Company company;

    @BeforeEach
    public void setUp(){
        company = new Company();
        company.setId(1L);
        company.setName("Wesley");
        company.setPhone("(34)991307618");
        company.setEmail("wes@test2e.com.br");
        company.setCnpj("12096105472");
        company.setPassword("123456");
        company.setJobs(new ArrayList<>());
        company.setUser(new Users());
    }

    @Test
    public void shouldCreateCompany() throws Exception {

        when(repository.save(company)).thenReturn(company);
        Company c = service.saveCompany(company);

        assertNotNull(c);
        assertEquals("wes@test2e.com.br", c.getEmail());
    }
    @Test
    public void shouldGetCompany() throws Exception {

        when(repository.findById(anyLong())).thenReturn(Optional.of(company));
        Company c = service.getCompany(company.getId());

        Assertions.assertEquals(Company.class, c.getClass());
    }
    @Test
    public void shouldGetAllCompany() throws Exception {

        when(repository.listCompany()).thenReturn(List.of(new CompanyDTO()));
        List<CompanyDTO> list = service.getAllCompany();
        verify(repository, times(1)).listCompany();
    }
    @Test
    public void shouldEditCompany() throws Exception {
        company.setEmail("email@alterado.com");
        when(repository.save(company)).thenReturn(company);
        Company obj = service.saveEditCompany(company);

        assertNotNull(obj);
        assertEquals("email@alterado.com", obj.getEmail());
    }
    @Test
    public void shouldDeleteCompany() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        service.deleteOneCompany(company.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
