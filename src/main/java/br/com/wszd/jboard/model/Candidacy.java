package br.com.wszd.jboard.model;

import br.com.wszd.jboard.util.CandidacyStatus;
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
@Table(name = "candidacy", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class Candidacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_candidacy")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dateCandidacy;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CandidacyStatus status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public static class Builder{
        private Person personId;
        private Job job;

        public Candidacy.Builder personId(Person personId){
            this.personId = personId;
            return this;
        }
        public Candidacy.Builder jobPublished(Job job){
            this.job = job;
            return this;
        }

        public Candidacy build(){
            return new Candidacy(this);
        }

    }

    private Candidacy(Candidacy.Builder builder){
        dateCandidacy = LocalDateTime.now();
        status = CandidacyStatus.AGUARDANDO;
        personId = builder.personId;
        job = builder.job;
    }

}
