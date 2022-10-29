package br.com.wszd.jboard.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
public class PersonDTO {

    private Long id;
    private String name;

    private String phone;

    private String email;

    private String cpf;

    public PersonDTO(Long id, String name, String phone, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cpf = cpf;
    }

    public static class Builder{

        private static Long id;
        private String name;
        private String phone;
        private String email;
        private String cpf;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

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

        public PersonDTO build(){
            return new PersonDTO(this);
        }
    }

    public PersonDTO(Builder builder){
        id = Builder.id;
        name = builder.name;
        phone = builder.phone;
        email = builder.email;
        cpf = builder.cpf;
    }
}
