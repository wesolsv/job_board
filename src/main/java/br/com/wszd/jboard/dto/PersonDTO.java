package br.com.wszd.jboard.dto;

import br.com.wszd.jboard.model.Job;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "person", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email")
    @Email(message = "Email deve ser valido")
    private String email;

    @NotNull
    @Column(name = "password")
    @Size(min = 6, max = 16, message = "Senha tamanho permitido min 6 max 16")
    private String password;

    public PersonDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //private String token;

    public static class Builder{

        private String email;
        private String password;

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public PersonDTO build(){
            return new PersonDTO(this);
        }
    }

    private PersonDTO(Builder builder){
        email = builder.email;
        password = builder.password;
    }
}
