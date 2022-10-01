package br.com.wszd.jboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_published", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class JobPublished {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio")
    private String description;

    @NotNull
    @Column(name = "date_publish")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime datePublish;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job jobId;

    public static class Builder{

        private String description;
        private Job jobId;
        public Builder description(String description){
            this.description = description;
            return this;
        }
        public Builder jobId(Job jobId){
            this.jobId = jobId;
            return this;
        }

        public JobPublished build(){
            return new JobPublished(this);
        }
    }

    private JobPublished(JobPublished.Builder builder){
        description = builder.description;
        datePublish = LocalDateTime.now();
        jobId = builder.jobId;
    }
}
