package br.com.wszd.jboard.util;

import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ValidacaoUsuarioLogged {

    public static <T> void validEmailUsuario(T entity, Users usuario) {
        log.info("Validando company/usuario");

        ArrayList<String> rolesRetorno = new ArrayList<>();

        if (usuario.getRoles() != null) {
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                String j = usuario.getRoles().get(i).getName() + "";
                rolesRetorno.add(j);
            }
        }
        if (usuario.getPersonId() != null) {
            if(entity instanceof Company){
                if (rolesRetorno.contains("ADMIN") || ((Company) entity).getId().equals(usuario.getCompanyId().getId())) {
                    log.info("Validado email do usuario ou usuario é admin");
                } else {
                    throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
                }
            }else{
                if (rolesRetorno.contains("ADMIN") || ((Person) entity).getId().equals(usuario.getPersonId().getId())) {
                    log.info("Validado email do usuario ou usuario é admin");
                } else {
                    throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
                }
            }
        }
    }
}
