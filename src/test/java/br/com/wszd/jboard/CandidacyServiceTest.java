package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.service.EmailServiceImpl;
import br.com.wszd.jboard.service.JobService;
import br.com.wszd.jboard.service.PersonService;
import br.com.wszd.jboard.util.CandidacyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
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
    private JobService jobService;
    @MockBean
    private EmailServiceImpl emailService;
    @Autowired
    private CandidacyService service;

    @Test
    @DisplayName("deve criar uma nova candidatura")
    public void shouldCreateCandidacy() throws Exception {
        Long idPessoa = 0L;
        Long idJob = 1L;
        Candidacy candidatura = mock(Candidacy.class);
        Person pessoa = mock(Person.class);
        Job job = mock(Job.class);
        Company company = mock(Company.class);

        when(candidatura.getPersonId()).thenReturn(pessoa);
        when(candidatura.getPersonId().getId()).thenReturn(idPessoa);
        when(candidatura.getJob()).thenReturn(job);
        when(candidatura.getJob().getId()).thenReturn(idJob);
        when(company.getName()).thenReturn("Test Company");
        when(job.getCompanyId()).thenReturn(company);
        when(personService.getPerson(idPessoa)).thenReturn(pessoa);
        when(jobService.getJob(idJob)).thenReturn(job);
        when(repository.save(candidatura)).thenReturn(candidatura);
        service.createNewCandidacy(candidatura);

        verify(repository, times(1)).save(any(Candidacy.class));
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("deve editar uma candidatura")
    public void shouldEditCandidacy() throws Exception {
        Long idCandidatura = 1L;
        Candidacy candidatura = mock(Candidacy.class);
        Person pessoa = mock(Person.class);
        Job job = mock(Job.class);
        Company company = mock(Company.class);

        when(repository.findById(idCandidatura)).thenReturn(Optional.ofNullable(candidatura));
        when(candidatura.getStatus()).thenReturn(CandidacyStatus.ACEITO);
        when(candidatura.getPersonId()).thenReturn(pessoa);
        when(candidatura.getJob()).thenReturn(job);
        when(company.getName()).thenReturn("Test Company");
        when(job.getCompanyId()).thenReturn(company);
        when(repository.save(candidatura)).thenReturn(candidatura);
        service.editCandidacy(idCandidatura, candidatura);

        verify(repository, times(1)).save(any(Candidacy.class));
        verify(repository, times(1)).findById(idCandidatura);
    }
    @Test
    @DisplayName("deve retornar uma candidatura")
    public void shouldGetCandidacy() throws Exception {
        Long idCandidatura = 1L;
        Candidacy candidatura = mock(Candidacy.class);

        when(repository.findById(1L)).thenReturn(Optional.of(candidatura));
        when(candidatura.getId()).thenReturn(idCandidatura);
        service.getOneCandidacy(candidatura.getId());

        verify(repository, times(1)).findById(1L);
    }
    @Test
    @DisplayName("deve retornar uma lista de candidaturas")
    public void shouldGetAllCandidacy() throws Exception {
        List<CandidacyDTO> lista = new ArrayList<>();

        when(repository.listAllCandidacy()).thenReturn(lista);
        service.getAllCandidacy();
        verify(repository, times(1)).listAllCandidacy();
    }

    @Test
    @DisplayName("deve deletar uma candidatura")
    public void shouldDeleteCandidacy() throws Exception {
        Long idCandidatura = 1L;
        Candidacy candidatura = mock(Candidacy.class);

        doNothing().when(repository).deleteById(anyLong());
        when(candidatura.getId()).thenReturn(idCandidatura);
        service.deleteCandidacy(candidatura.getId());
        verify(repository, times(1)).deleteById(anyLong());
    }
}
