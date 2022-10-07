package br.com.wszd.jboard.model;

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
import java.util.Optional;

@Data
@Entity
@Table(name = "job", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "opportunity")
    @NotBlank(message = "Oportunidade é obrigatorio")
    private String opportunity;

    @NotNull
    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio")
    private String description;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "salary")
    private Double salary;

    @NotNull
    @Column(name = "benefits")
    private String benefits;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties("job")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("jobs")
    private Company companyId;

    @Column(name = "date_publish")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime datePublish;


    public static class Builder{

        private String opportunity;
        private String description;
        private String type;
        private Double salary;
        private String benefits;
        private JobStatus status;

        private Person personId;
        private Company companyId;

        private LocalDateTime dateCandidacy;

        public Job.Builder opportunity(String opportunity){
            this.opportunity = opportunity;
            return this;
        }

        public Job.Builder description(String description){
            this.description = description;
            return this;
        }
        public Job.Builder type(String type){
            this.type = type;
            return this;
        }
        public Job.Builder salary(Double salary){
            this.salary = salary;
            return this;
        }
        public Job.Builder benefits(String benefits){
            this.benefits = benefits;
            return this;
        }
        public Job.Builder status(JobStatus status){
            this.status = status;
            return this;
        }
        public Job.Builder personId(Person personId){
            this.personId = personId;
            return this;
        }
        public Job.Builder companyId(Company companyId){
            this.companyId = companyId;
            return this;
        }

        public Job build(){
            return new Job(this);
        }
    }

    private Job(Job.Builder builder){
        opportunity = builder.opportunity;
        description = builder.description;
        type = builder.type;
        salary = builder.salary;
        benefits = builder.benefits;
        status = builder.status;
        personId = builder.personId;
        companyId = builder.companyId;
        datePublish = LocalDateTime.now();
    }
}
