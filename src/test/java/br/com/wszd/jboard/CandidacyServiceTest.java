package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.service.EmailService;
import br.com.wszd.jboard.service.JobService;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.util.CandidacyStatus;
import br.com.wszd.jboard.util.JobStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class CandidacyServiceTest {

    @MockBean
    private CandidacyRepository repository;
    @MockBean
    private PersonService personService;
    @MockBean
    private EmailService emailService;
    @MockBean
    private JobService jobService;

    @Autowired
    private CandidacyService service;

    Candidacy candidacy;

    Job job;

    @BeforeEach
    public void setUp(){
        candidacy = new Candidacy();
        candidacy.setId(1L);
        candidacy.setDateCandidacy(LocalDateTime.now());
        candidacy.setStatus(CandidacyStatus.AGUARDANDO);
        candidacy.setPersonId(new Person());
        candidacy.setJob(job);
    }

    @Test
    @DisplayName("deve criar uma nova candidatura")
    public void shouldCreateCandidacy() throws Exception {
        Long idPessoa = 0L;
        Long idJob = 1L;
        Candidacy candidatura = mock(Candidacy.class);
        Person pessoa = mock(Person.class);
        Job jobT = mock(Job.class);

        when(candidatura.getPersonId()).thenReturn(pessoa);
        when(candidatura.getPersonId().getId()).thenReturn(idPessoa);
        when(candidatura.getJob()).thenReturn(jobT);
        when(candidatura.getJob().getId()).thenReturn(idJob);
        when(personService.getPerson(idPessoa)).thenReturn(pessoa);
        when(jobService.getJob(idJob)).thenReturn(jobT);
        when(repository.save(candidatura)).thenReturn(candidatura);
        service.createNewCandidacy(candidatura);

        verify(repository, times(1)).save(any(Candidacy.class));
        verify(repository, times(1)).findAll();
    }
    @Test
    public void shouldGetCandidacy() throws Exception {
        Candidacy candidatura = mock(Candidacy.class);

        when(repository.findById(1L)).thenReturn(Optional.of(candidatura));
        service.getOneCandidacy(candidacy.getId());

        verify(repository, times(1)).findById(1L);
    }
    @Test
    public void shouldGetAllCandidacy() throws Exception {
        List<CandidacyDTO> lista = new ArrayList<>();

        when(repository.listAllCandidacy()).thenReturn(lista);
        service.getAllCandidacy();
        verify(repository, times(1)).listAllCandidacy();
    }
    @Test
    public void shouldEditCandidacy() throws Exception {
        candidacy.setStatus(CandidacyStatus.ACEITO);
        when(repository.save(candidacy)).thenReturn(candidacy);
        Candidacy obj = service.saveEditCandidacy(candidacy);

        assertNotNull(obj);
        assertEquals(CandidacyStatus.ACEITO, obj.getStatus());
    }
    @Test
    public void shouldDeleteCandidacy() throws Exception {
        doNothing().when(repository).deleteById(anyLong());
        service.deleteCandidacy(candidacy.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
