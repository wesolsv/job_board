package br.com.wszd.jboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    private String status;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    //@JoinColumn(name = "company_id")
    private Company companyId;
}
