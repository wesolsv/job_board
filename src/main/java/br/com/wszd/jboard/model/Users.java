package br.com.wszd.jboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", schema = "job_board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties("user")
    private Person personId;

    @OneToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("user")
    private Company companyId;

    @ManyToMany
    private List<Role> roles;

}
