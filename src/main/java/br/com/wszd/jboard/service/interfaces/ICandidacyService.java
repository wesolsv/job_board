package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.model.Candidacy;

import java.util.ArrayList;
import java.util.List;

public interface ICandidacyService {

    public List<CandidacyDTO> getAllCandidacy();

    public ArrayList<PersonCandidacyDTO> getAllCandidacyByPersonId(Long id);

    public CandidacyDTO getCandidacy(Long id);

    public Candidacy getOneCandidacy(Long id);

    public Candidacy createNewCandidacy(Candidacy novo);

    public CandidacyDTO editCandidacy(Long id, Candidacy novo);

    public void deleteCandidacy(Long id);

    public void deleteAllCandidacy(Long jobId);
}
