package br.com.wszd.jboard.model;

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
}
