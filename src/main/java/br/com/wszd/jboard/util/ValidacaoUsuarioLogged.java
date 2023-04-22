package br.com.wszd.jboard.util;

import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public final class ValidacaoUsuarioLogged {

    public static void validarEmailPessoaUsuario(Person pessoa, Users usuario) {
        log.info("Validando pessoa/usuario");

        ArrayList<String> rolesRetorno = new ArrayList<>();

        if (usuario.getRoles() != null) {
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                String j = usuario.getRoles().get(i).getName() + "";
                rolesRetorno.add(j);
            }
        }
        if (usuario.getPersonId() != null) {
            if (rolesRetorno.contains("ADMIN") || pessoa.getId().equals(usuario.getPersonId().getId())) {
                log.info("Validado email do usuario ou usuario é admin");
            } else {
                throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
            }
        }
    }

    public static void validEmailCompanyUsuario(Company company, Users usuario) {
        log.info("Validando company/usuario");

        ArrayList<String> rolesRetorno = new ArrayList<>();

        if (usuario.getRoles() != null) {
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                String j = usuario.getRoles().get(i).getName() + "";
                rolesRetorno.add(j);
            }
        }
        if (usuario.getPersonId() != null) {
            if (rolesRetorno.contains("ADMIN") || company.getId().equals(usuario.getPersonId().getId())) {
                log.info("Validado email do usuario ou usuario é admin");
            } else {
                throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
            }
        }
    }

//    public static void validarUsuarioNota(Nota nota, Usuario usuario) {
//        log.info("Validando usuario");
//        if (usuario.getPessoa() != null) {
//            if (nota.getPessoa().getId() != usuario.getPessoa().getId()) {
//                throw new ResourceBadRequestException("O usuário não tem acesso a esta nota");
//            }
//        }
//    }
//
//    public static void validarUsuarioCategoria(Categoria categoria, Usuario usuario) {
//        log.info("Validando usuario");
//        if (usuario.getPessoa() != null) {
//            if (categoria.getPessoa().getId() != usuario.getPessoa().getId()) {
//                throw new ResourceBadRequestException("O usuário não tem acesso a esta categoria");
//            }
//        }
//    }
}
