package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.service.CompanyService;
import br.com.wszd.jboard.service.EmailService;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
public class CompanyServiceTest {

    @MockBean
    private CompanyRepository repository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private PersonService personService;
    @MockBean
    private UserService userService;

    @Autowired
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
        Company companyT = mock(Company.class);

        when(companyT.getEmail()).thenReturn("teste@teste.com");
        when(companyT.getCnpj()).thenReturn("12345678911");
        when(companyT.getPhone()).thenReturn("12345678911");
        when(companyT.getPassword()).thenReturn("123456");
        when(repository.save(any(Company.class)))
                .thenAnswer(invocation -> {
                    Company company = invocation.getArgument(0);
                    company.setId(1L); // seta o ID do objeto salvo
                    return company;
                });
        service.createNewCompany(companyT);

        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any(Company.class));
    }
    @Test
    public void shouldGetCompany() throws Exception {
        Company companyT = mock(Company.class);
        companyT.setId(0L);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(companyT));
        service.getCompany(companyT.getId());

        verify(repository, times(1)).findById(0L);
    }
    @Test
    public void shouldGetAllCompany() throws Exception {

        when(repository.listCompany()).thenReturn(List.of(new CompanyDTO()));
        service.getAllCompany();

        verify(repository, times(1)).listCompany();
    }
    @Test
    public void shouldEditCompany() throws Exception {

        Company companyT = mock(Company.class);
        Users user = mock(Users.class);
        user.setCompanyId(companyT);

        when(userService.getUserByCompanyId(companyT)).thenReturn(user);
        when(userService.returnEmailUser()).thenReturn(new Users());
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(companyT));
        when(repository.save(any(Company.class)))
                .thenAnswer(invocation -> {
                    Company company = invocation.getArgument(0);
                    company.setId(1L); // seta o ID do objeto salvo
                    return company;
                });

        service.editCompany(0L, companyT);

        verify(repository, times(1)).save(any(Company.class));
    }
    @Test
    public void shouldDeleteCompany() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        service.deleteOneCompany(company.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
