package br.com.wszd.jboard.dto;

import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    private String name;

    private String phone;

    private String email;

    private String cpf;

    public CompanyDTO(Long id, String name, String phone, String email, String cpf) {
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

        public CompanyDTO build(){
            return new CompanyDTO(this);
        }
    }

    public CompanyDTO(Builder builder){
        id = Builder.id;
        name = builder.name;
        phone = builder.phone;
        email = builder.email;
        cpf = builder.cpf;
    }
}
