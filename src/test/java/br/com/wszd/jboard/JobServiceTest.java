package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.JobRepository;
import br.com.wszd.jboard.service.JobServiceImpl;
import br.com.wszd.jboard.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class JobServiceTest {

    @MockBean
    private JobRepository repository;
    @MockBean
    private UserServiceImpl userServiceImpl;
    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private JobServiceImpl service;

    @Test
    public void shouldCreateJob() throws Exception {

        Job jobT = mock(Job.class);
        Users user = mock(Users.class);
        Company companyT = mock(Company.class);

        List<Role> listIdRoles = new ArrayList<>();

        when(jobT.getCompanyId()).thenReturn(companyT);
        when(jobT.getOpportunity()).thenReturn("Emprego novo");
        when(jobT.getDescription()).thenReturn("Emprego novo descricao");
        when(jobT.getType()).thenReturn("Temporario");
        when(jobT.getBenefits()).thenReturn("Sem beneficios");
        when(companyT.getId()).thenReturn(0L);
        when(user.getRoles()).thenReturn(listIdRoles);
        when(userServiceImpl.returnEmailUser()).thenReturn(user);
        when(user.getCompanyId()).thenReturn(companyT);
        when(companyRepository.findById(0L)).thenReturn(Optional.of(companyT));
        when(repository.save(any(Job.class)))
                .thenAnswer(invocation -> {
                    Job j = invocation.getArgument(0);
                    j.setId(1L); // seta o ID do objeto salvo
                    return j;
                });
        service.createNewJob(jobT);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Job.class));
    }
    @Test
    public void shouldGetJobDTO() throws Exception {
        Job jobT = mock(Job.class);
        jobT.setId(0L);
        Company companyT = mock(Company.class);
        Users user = mock(Users.class);

        when(repository.findById(0L)).thenReturn(Optional.of(jobT));
        when(companyT.getId()).thenReturn(0L);
        when(jobT.getCompanyId()).thenReturn(companyT);
        when(companyRepository.findById(0L)).thenReturn(Optional.of(companyT));
        when(userServiceImpl.returnEmailUser()).thenReturn(user);
        when(repository.findById(anyLong())).thenReturn(Optional.of(jobT));
        JobDTO j = service.getJobDTO(jobT.getId());

        Assertions.assertEquals(JobDTO.class, j.getClass());
        verify(repository, times(1)).findById(anyLong());
    }
    @Test
    public void shouldGetAllJobs() throws Exception {

        Job jobT = mock(Job.class);
        Company companyT = mock(Company.class);
        Users user = mock(Users.class);

        when(userServiceImpl.returnEmailUser()).thenReturn(user);
        when(user.getCompanyId()).thenReturn(companyT);
        when(jobT.getCompanyId()).thenReturn(companyT);
        when(repository.listJobs()).thenReturn(List.of(new JobDTO()));
        service.getAllJobs();

        verify(userServiceImpl, times(2)).returnEmailUser();
        verify(repository, times(1)).listJobs();
    }
    @Test
    public void shouldEditJob() throws Exception {
        Job jobT = mock(Job.class);
        jobT.setId(0L);
        Company companyT = mock(Company.class);
        Users user = mock(Users.class);

        when(jobT.getDatePublish()).thenReturn(LocalDateTime.now());
        when(jobT.getCompanyId()).thenReturn(companyT);
        when(jobT.getOpportunity()).thenReturn("Emprego novo");
        when(jobT.getDescription()).thenReturn("Emprego novo descricao");
        when(jobT.getType()).thenReturn("Temporario");
        when(jobT.getBenefits()).thenReturn("Sem beneficios");
        when(companyT.getId()).thenReturn(0L);
        when(companyRepository.findById(0L)).thenReturn(Optional.of(companyT));
        when(userServiceImpl.returnEmailUser()).thenReturn(user);
        when(repository.findById(0L)).thenReturn(Optional.of(jobT));
        when(repository.save(any(Job.class)))
                .thenAnswer(invocation -> {
                    Job j = invocation.getArgument(0);
                    j.setId(0L); // seta o ID do objeto salvo
                    return j;
                });
        service.editJob(0L, jobT);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Job.class));
    }
    @Test
    public void shouldDeleteJob() throws Exception {
        Job jobT = mock(Job.class);
        jobT.setId(0L);

        doNothing().when(repository).deleteById(anyLong());
        service.deleteOneJob(jobT.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
