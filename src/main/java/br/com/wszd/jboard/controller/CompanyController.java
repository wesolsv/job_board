package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @ApiOperation(value = "Retorna todas as empresas")
    @GetMapping
    public ArrayList<Company> getAllPeople(){
        return service.getAllCompany();
    }

    @ApiOperation(value = "Retorna uma empresa")
    @GetMapping("/{id}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Long id){
        Company res = service.getCompany(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria nova empresa")
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company novo){
        Company res = service.createNewCompany(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Altera uma empresa")
    @PutMapping
    public ResponseEntity<Company> editCompany(@RequestBody Company novo){
        Company res = service.editCompany((novo));

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando uma empresa")
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id){
        service.deleteCompany(id);
    }
}
