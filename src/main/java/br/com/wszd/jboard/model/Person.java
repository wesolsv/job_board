package br.com.wszd.jboard.model;

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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    @NotBlank(message = "Nome é obrigatorio")
    @Size(min = 3, message = "Nome tamnho min 3")
    private String name;

    @NotNull
    @Column(name = "phone")
    @Size(min = 10, max = 15, message = "Telefone tamanho permitido min 10 max 15")
    private String phone;

    @NotNull
    @Column(name = "email")
    @Email(message = "Email deve ser valido")
    private String email;

    @NotNull
    @Column(name = "cpf")
    @NotBlank(message = "CPF é obrigatorio")
    private String cpf;

    @OneToOne(mappedBy = "personId")
    @JsonIgnoreProperties("personId")
    private Job job;

    public static class Builder{

        private String name;
        private String phone;
        private String email;
        private String cpf;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder cpf(String cpf){
            this.cpf = cpf;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    private Person(Builder builder){
        name = builder.name;
        phone = builder.phone;
        email = builder.email;
        cpf = builder.cpf;
    }
}
