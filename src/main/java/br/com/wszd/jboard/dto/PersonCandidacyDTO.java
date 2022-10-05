package br.com.wszd.jboard.dto;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.JobPublished;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.util.CandidacyStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCandidacyDTO {

    private LocalDateTime dataCandidacy;

    @Enumerated(EnumType.STRING)
    private CandidacyStatus status;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "email", "job"})
    private Person person;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "jobId"})
    private JobPublished jobPublished;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {"id","personId","companyId"})
    private Job job;

    @JsonIgnoreProperties(ignoreUnknown = true, value =  {"id", "phone", "cnpj", "jobs"})
    private Company company;


    public PersonCandidacyDTO(LocalDateTime dataCandidacy, CandidacyStatus status, Person person, JobPublished jobPublished) {
        this.dataCandidacy = dataCandidacy;
        this.status = status;
        this.person = person;
        this.jobPublished = jobPublished;
        this.job = jobPublished.getJobId();
        this.company = job.getCompanyId();
    }
}
