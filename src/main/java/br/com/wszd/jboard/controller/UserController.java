package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.SessaoDTO;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/users")
@Api(value = "Users")
public class UserController {

    @Autowired
    private UserService service;

    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

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
    public SessaoDTO logar(@RequestBody Users infoLogin){
        Users user = service.findByEmail(infoLogin.getEmail());
        if(user!=null) {
            boolean passwordOk = passwordEncoder().matches(infoLogin.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new ResourceBadRequestException("Senha incorreta para o email: " + infoLogin.getEmail());
            }

            //Cria o objeto de sessão para retornar email e token do usuario
            SessaoDTO sessaoDTO = new SessaoDTO();
            sessaoDTO.setLogin(user.getEmail());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());

            sessaoDTO.setToken(JWTCreator.createToken(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return sessaoDTO;
        }else {
            throw new ResourceBadRequestException("Erro ao tentar fazer login");
        }
    }
}
