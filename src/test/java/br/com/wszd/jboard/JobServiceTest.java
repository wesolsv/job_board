package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.JobRepository;
import br.com.wszd.jboard.service.JobService;
import br.com.wszd.jboard.service.UserService;
import br.com.wszd.jboard.util.JobStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    private UserService userService;
    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private JobService service;

    Job job;

    @BeforeEach
    public void setUp(){
        job = new Job();
        job.setId(1L);
        job.setOpportunity("Emprego novo");
        job.setDescription("Emprego novo descricao");
        job.setType("Temporario");
        job.setSalary(1500.0);
        job.setBenefits("Sem beneficios");
        job.setStatus(JobStatus.OPEN);
        job.setPersonId(null);
        job.setCompanyId(new Company());
        job.setDatePublish(LocalDateTime.now());
    }

    @Test
    public void shouldCreateJob() throws Exception {

        Job jobT = mock(Job.class);
        Users user = mock(Users.class);
        Company companyT = mock(Company.class);

        List<Role> listIdRoles = new ArrayList<>();

        when(jobT.getCompanyId()).thenReturn(companyT);
        when(companyT.getId()).thenReturn(0L);
        when(user.getRoles()).thenReturn(listIdRoles);
        when(userService.returnEmailUser()).thenReturn(user);
        when(user.getCompanyId()).thenReturn(companyT);
        when(companyRepository.findById(0L)).thenReturn(Optional.of(companyT));
        when(repository.save(any(Job.class)))
                .thenAnswer(invocation -> {
                    Job j = invocation.getArgument(0);
                    j.setId(1L); // seta o ID do objeto salvo
                    return j;
                });
        service.createNewJob(job);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Job.class));
    }
    @Test
    public void shouldGetJob() throws Exception {

        when(repository.findById(anyLong())).thenReturn(Optional.of(job));
        Job j = service.getJob(job.getId());

        Assertions.assertEquals(Job.class, j.getClass());
    }
    @Test
    public void shouldGetAllJobs() throws Exception {

        when(repository.listJobs()).thenReturn(List.of(new JobDTO()));
        List<JobDTO> list = service.getAllJobs();
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
        when(userService.returnEmailUser()).thenReturn(user);
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
        doNothing().when(repository).deleteById(anyLong());
        service.deleteOneJob(job.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
