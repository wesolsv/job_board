package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.Sessao;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.security.JWTCreator;
import br.com.wszd.jboard.security.JWTObject;
import br.com.wszd.jboard.security.SecurityConfig;
import br.com.wszd.jboard.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/users")
@Api(value = "Users")
public class UserController {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService service;

    @ApiOperation(value = "Cria nova Role")
    @PostMapping("/role")
    public Users role(@RequestBody UserRoleDTO userRoleDTO){

        return service.execute(userRoleDTO);
    }

    @ApiOperation(value = "Deletando um usuario")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deleteUser(id);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Realiza o Login do usuario e retorna o seu token")
    public Sessao logar(@RequestBody Users infoLogin){
        Users user = service.findByEmail(infoLogin.getEmail());
        if(user!=null) {
            boolean passwordOk =  encoder.matches(infoLogin.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new ResourceBadRequestException("Senha incorreta para o email: " + infoLogin.getEmail());
            }

            //Enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getEmail());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return sessao;
        }else {
            throw new ResourceBadRequestException("Erro ao tentar fazer login");
        }
    }
}
