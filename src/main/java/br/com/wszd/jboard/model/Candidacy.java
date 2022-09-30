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
    @NotBlank(message = "Status é obrigatorio")
    private String status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "job_published_id")
    private JobPublished jobPublished;
}
