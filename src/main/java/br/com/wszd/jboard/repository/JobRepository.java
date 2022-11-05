package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT new br.com.wszd.jboard.dto.JobDTO "
            + "(j.id, j.opportunity, j.description, j.type, j.salary, j.benefits, j.status, j.datePublish, cp.name, cp.id) "
            + "FROM Job j "
            + "INNER JOIN Company cp "
            + "ON cp.id = j.companyId")
    List<JobDTO> listJobs();

    @Query("SELECT new br.com.wszd.jboard.dto.JobDTO "
            + "(j.id, j.opportunity, j.description, j.type, j.salary, j.benefits, j.status, j.datePublish, cp.name, cp.id) "
            + "FROM Job j "
            + "INNER JOIN Company cp "
            + "ON cp.id = j.companyId "
            + "WHERE j.opportunity LIKE %:param%")
    List<JobDTO> listJobsByParam(String param);
}
