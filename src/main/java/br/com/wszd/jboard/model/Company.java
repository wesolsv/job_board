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
import java.util.List;

@Data
@Entity
@Table(name = "company", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class Company{

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
    @Column(name = "cnpj")
    @NotBlank(message = "CNPJ é obrigatorio")
    private String cnpj;
    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "companyId")
    @JsonIgnoreProperties("companyId")
    private List<Job> jobs;

    @OneToOne(mappedBy = "companyId")
    @JsonIgnoreProperties("companyId")
    private Users user;

    public static class Builder{

        private String name;
        private String phone;
        private String email;
        private String cnpj;
        private String password;
        private Users user;

        public Company.Builder name(String name){
            this.name = name;
            return this;
        }
        public Company.Builder phone(String phone){
            this.phone = phone;
            return this;
        }
        public Company.Builder email(String email){
            this.email = email;
            return this;
        }
        public Company.Builder cnpj(String cnpj){
            this.cnpj = cnpj;
            return this;
        }
        public Company.Builder password(String password){
            this.password = password;
            return this;
        }
        public Company.Builder user(Users user){
            this.user = user;
            return this;
        }

        public Company build(){
            return new Company(this);
        }
    }

    private Company(Company.Builder builder){
        name = builder.name;
        phone = builder.phone;
        email = builder.email;
        cnpj = builder.cnpj;
        password = builder.password;
        user = builder.user;
    }
}
