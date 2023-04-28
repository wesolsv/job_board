package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@Api(value = "Users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @ApiOperation(value = "Deletando um usuario")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deleteUser(id);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Realiza o Login do usuario e retorna o seu token")
    @ResponseBody
    public SessaoDTO logar(@RequestBody UserLoginDTO infoLogin){
        return service.validLogin(infoLogin);
    }
}
