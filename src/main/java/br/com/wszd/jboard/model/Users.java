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

    public Users(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public static class Builder{
        private String email;
        private String password;
        private Person personId;
        private Company companyId;
        private List<Role> roles;

        public Users.Builder email(String email){
            this.email = email;
            return this;
        }
        public Users.Builder password(String password){
            this.password = password;
            return this;
        }
        public Users.Builder personId(Person personId){
            this.personId = personId;
            return this;
        }
        public Users.Builder companyId(Company companyId){
            this.companyId = companyId;
            return this;
        }
        public Users.Builder roles(List<Role> roles){
            this.roles = roles;
            return this;
        }

        public Users build(){
            return new Users(this);
        }
    }

    private Users(Users.Builder builder){
        email = builder.email;
        password = builder.password;
        personId = builder.personId;
        companyId = builder.companyId;
        roles = builder.roles;
    }

}
