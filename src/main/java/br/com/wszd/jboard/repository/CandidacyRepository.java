package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Candidacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidacyRepository extends JpaRepository<Candidacy, Long> {
}
