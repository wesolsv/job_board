package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.security.JWTCreator;
import br.com.wszd.jboard.security.JWTObject;
import br.com.wszd.jboard.security.SecurityConfig;
import br.com.wszd.jboard.dto.Login;
import br.com.wszd.jboard.dto.Sessao;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequestMapping("api/v1/users")
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
        Users user = repository.findByEmail(login.getEmail());
        if(user!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new ResourceBadRequestException("Senha inválida para o login: " + login.getEmail());
            }

            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getEmail());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(String.valueOf(user.getRoles()));
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return sessao;
        }else {
            throw new ResourceBadRequestException("Erro ao tentar fazer login");
        }
    }
}
