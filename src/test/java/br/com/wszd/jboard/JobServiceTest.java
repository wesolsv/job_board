package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.repository.JobRepository;
import br.com.wszd.jboard.service.JobService;
import br.com.wszd.jboard.util.JobStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class JobServiceTest {

    @Mock
    private JobRepository repository;

    @InjectMocks
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

        when(repository.save(job)).thenReturn(job);
        Job j = service.createNewJob(job);

        assertNotNull(j);
        assertEquals(JobStatus.OPEN, j.getStatus());
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
        job.setOpportunity("VAGA ALTERADA NOVAMENTE");
        when(repository.save(job)).thenReturn(job);
        Job obj = service.saveEditJob(job);

        assertNotNull(obj);
        assertEquals("VAGA ALTERADA NOVAMENTE", obj.getOpportunity());
    }
    @Test
    public void shouldDeleteJob() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        service.deleteOneJob(job.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
