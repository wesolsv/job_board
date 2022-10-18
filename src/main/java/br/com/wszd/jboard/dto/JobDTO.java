package br.com.wszd.jboard.dto;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.util.JobStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class JobDTO {

    private Long id;
    private String opportunity;
    private String description;
    private String type;
    private Double salary;
    private String benefits;
    private JobStatus status;
    private String companyName;
    private LocalDateTime datePublish;

    public JobDTO(Long id, String opportunity, String description, String type, Double salary, String benefits, JobStatus status, LocalDateTime datePublish, String companyName) {
        this.id = id;
        this.opportunity = opportunity;
        this.description = description;
        this.type = type;
        this.salary = salary;
        this.benefits = benefits;
        this.status = status;
        this.companyName = companyName;
        this.datePublish = datePublish;
    }

    public static class Builder{
        private Long id;
        private String opportunity;
        private String description;
        private String type;
        private Double salary;
        private String benefits;
        private JobStatus status;
        private String companyName;
        private LocalDateTime datePublish;

        public JobDTO.Builder id(Long id){
            this.id = id;
            return this;
        }

        public JobDTO.Builder opportunity(String opportunity){
            this.opportunity = opportunity;
            return this;
        }

        public JobDTO.Builder description(String description){
            this.description = description;
            return this;
        }
        public JobDTO.Builder type(String type){
            this.type = type;
            return this;
        }
        public JobDTO.Builder salary(Double salary){
            this.salary = salary;
            return this;
        }
        public JobDTO.Builder benefits(String benefits){
            this.benefits = benefits;
            return this;
        }
        public JobDTO.Builder status(JobStatus status){
            this.status = status;
            return this;
        }
        public JobDTO.Builder companyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public JobDTO.Builder datePublish(LocalDateTime datePublish){
            this.datePublish = datePublish;
            return this;
        }

        public JobDTO build(){
            return new JobDTO(this);
        }
    }

    private JobDTO(JobDTO.Builder builder){
        id = builder.id;
        opportunity = builder.opportunity;
        description = builder.description;
        type = builder.type;
        salary = builder.salary;
        benefits = builder.benefits;
        status = builder.status;
        companyName = builder.companyName;
        datePublish = builder.datePublish;
    }
}
