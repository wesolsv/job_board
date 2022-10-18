package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT new br.com.wszd.jboard.dto.JobDTO "
            + "(j.id, j.opportunity, j.description, j.type, j.salary, j.benefits, j.status,  "
            + "j.datePublish, "
            + "(SELECT c.name FROM Company c WHERE j.companyId = c.id) ) "
            + "FROM Job j ")
    List<JobDTO> listJobs();
}
