package br.com.wszd.jboard.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "job")
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

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
    @JoinColumn(name = "company_id")
    private Company companyId;
}
