package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.model.Candidacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CandidacyRepository extends JpaRepository<Candidacy, Long> {

    @Query("SELECT "
            + "new br.com.wszd.jboard.dto.PersonCandidacyDTO(cd.dateCandidacy, cd.status, cd.personId, cd.job) "
            + "FROM Candidacy cd ")
    public ArrayList<PersonCandidacyDTO> returnCandidacyByPerson(
            @Param("id")Long id);

    @Query("SELECT new br.com.wszd.jboard.dto.CandidacyDTO(cd.id, cd.dateCandidacy, cd.status, cd.personId, cd.job ) " +
            "FROM Candidacy cd")
    List<CandidacyDTO> listAllCandidacy();
}
