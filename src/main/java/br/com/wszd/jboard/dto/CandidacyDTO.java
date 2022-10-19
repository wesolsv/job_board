package br.com.wszd.jboard.dto;

import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.util.CandidacyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class CandidacyDTO {

    private Long id;
    private LocalDateTime dateCandidacy;
    private CandidacyStatus status;
    private PersonDTO person;
    private JobDTO job;

    public static class Builder{
        private PersonDTO person;
        private JobDTO job;

        private Long id;

        public CandidacyDTO.Builder id(Long id){
            this.id = id;
            return this;
        }

        public CandidacyDTO.Builder person(PersonDTO person){
            this.person = person;
            return this;
        }
        public CandidacyDTO.Builder job(JobDTO job){
            this.job = job;
            return this;
        }
        public CandidacyDTO build(){
            return new CandidacyDTO(this);
        }

    }

    private CandidacyDTO(CandidacyDTO.Builder builder){
        id = builder.id;
        dateCandidacy = LocalDateTime.now();
        status = CandidacyStatus.AGUARDANDO;
        person = builder.person;
        job = builder.job;
    }

}
