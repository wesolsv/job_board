package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/company")
@Api(value = "Company")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @ApiOperation(value = "Retorna todas as empresas")
    @GetMapping
    public ArrayList<Company> getAllCompany(){
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
    @PutMapping("/{id}")
    public ResponseEntity<Company> editCompany(@PathVariable Long id, @RequestBody Company novo){
        Company res = service.editCompany(id,novo);

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

    @ApiOperation(value = "Retorna todos os candidatos de uma vaga")
    @GetMapping("/candidate/{id}")
    public List<Optional<Person>> getAllPersonByJob(@PathVariable Long id ){
        return service.getAllPersonByJob(id);
    }
}
