package br.com.wszd.jboard;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.service.CandidacyService;
import br.com.wszd.jboard.util.CandidacyStatus;
import br.com.wszd.jboard.util.JobStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Mock
    private CandidacyRepository repository;

    @InjectMocks
    private CandidacyService service;

    Candidacy candidacy;

    @BeforeEach
    public void setUp(){
        candidacy = new Candidacy();
        candidacy.setId(1L);
        candidacy.setDateCandidacy(LocalDateTime.now());
        candidacy.setStatus(CandidacyStatus.AGUARDANDO);
        candidacy.setPersonId(new Person());
        candidacy.setJob(new Job());
    }

    @Test
    public void shouldCreateCandidacy() throws Exception {

        when(repository.save(candidacy)).thenReturn(candidacy);
        Candidacy c = service.newCandidacy(candidacy);

        assertNotNull(c);
        assertEquals(CandidacyStatus.AGUARDANDO,c.getStatus());
    }
    @Test
    public void shouldGetCandidacy() throws Exception {

        when(repository.findById(anyLong())).thenReturn(Optional.of(candidacy));
        Candidacy c = service.getOneCandidacy(candidacy.getId());

        Assertions.assertEquals(Candidacy.class, c.getClass());
    }
    @Test
    public void shouldGetAllCandidacy() throws Exception {
        ArrayList<CandidacyDTO> lista = new ArrayList<>();

        when(repository.listAllCandidacy()).thenReturn(lista);
        List<CandidacyDTO> list = service.getAllCandidacy();
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
