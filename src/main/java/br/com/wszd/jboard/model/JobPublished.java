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
@Table(name = "job_published")
@NoArgsConstructor
@AllArgsConstructor
public class JobPublished {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "description")
    @NotBlank(message = "Descricao é obrigatorio")
    private String description;

    @NotNull
    @Column(name = "date_publish")
    private LocalDateTime datePublish;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job jobId;
}
