package br.com.wszd.jboard.dto;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class PersonDTO {

    private String name;

    private String phone;

    private String email;

    private String cpf;

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

        public PersonDTO build(){
            return new PersonDTO(this);
        }
    }

    public PersonDTO(Builder builder){
        name = builder.name;
        phone = builder.phone;
        email = builder.email;
        cpf = builder.cpf;
    }
}
