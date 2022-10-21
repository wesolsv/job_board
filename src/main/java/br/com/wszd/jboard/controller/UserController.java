package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@Api(value = "Users")
public class UserController {

    @Autowired
    private UserService createRoleUserService;

    @ApiOperation(value = "Cria nova Role")
    @PostMapping("/role")
    public Users role(@RequestBody UserRoleDTO userRoleDTO){

        return createRoleUserService.execute(userRoleDTO);
    }

    @ApiOperation(value = "Deletando um usuario")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        createRoleUserService.deleteUser(id);
    }
}
