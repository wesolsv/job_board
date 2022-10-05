package br.com.wszd.jboard.dto;

import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.util.CandidacyStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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
    private Optional<Job> job;

    @JsonIgnoreProperties(ignoreUnknown = true, value =  {"id", "phone", "cnpj", "jobs"})
    private Optional<Company> company;


    public static class Builder{
        private LocalDateTime dataCandidacy;
        private CandidacyStatus status;
        private Person person;
        private JobPublished jobPublished;
        private Optional<Job> job;
        private Optional<Company> company;

        public PersonCandidacyDTO.Builder dataCandidacy(LocalDateTime dataCandidacy){
            this.dataCandidacy = dataCandidacy;
            return this;
        }
        public PersonCandidacyDTO.Builder status(CandidacyStatus status){
            this.status = status;
            return this;
        }
        public PersonCandidacyDTO.Builder person(Person person){
            this.person = person;
            return this;
        }
        public PersonCandidacyDTO.Builder job(Optional<Job> job){
            this.job = job;
            return this;
        }
        public PersonCandidacyDTO.Builder company(Optional<Company> company){
            this.company = company;
            return this;
        }
        public PersonCandidacyDTO.Builder jobPublished(JobPublished jobPublished){
            this.jobPublished = jobPublished;
            return this;
        }

        public PersonCandidacyDTO build(){
            return new PersonCandidacyDTO(this);
        }

    }

    private PersonCandidacyDTO(PersonCandidacyDTO.Builder builder){
        this.dataCandidacy = dataCandidacy;
        this.status = status;
        this.person = person;
        this.jobPublished = jobPublished;
        this.job = Optional.ofNullable(jobPublished.getJobId());
        this.company = Optional.ofNullable(job.get().getCompanyId());
    }

    public PersonCandidacyDTO(LocalDateTime dataCandidacy, CandidacyStatus status, Person person, JobPublished jobPublished) {
        this.dataCandidacy = dataCandidacy;
        this.status = status;
        this.person = person;
        this.jobPublished = jobPublished;
        this.job = Optional.ofNullable(jobPublished.getJobId());
        this.company = Optional.ofNullable(job.get().getCompanyId());
    }
}
