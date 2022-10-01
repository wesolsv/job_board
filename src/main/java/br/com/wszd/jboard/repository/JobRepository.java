package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
