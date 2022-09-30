package br.com.wszd.jboard.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "candidacy")
@NoArgsConstructor
@AllArgsConstructor
public class Candidacy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "date_candidacy")
    private LocalDateTime dateCandidacy;

    @NotNull
    @Column(name = "status")
    @NotBlank(message = "Status é obrigatorio")
    private String status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "job_published_id")
    private JobPublished jobPublished;
}
