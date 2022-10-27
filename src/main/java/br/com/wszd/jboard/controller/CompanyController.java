package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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
    public List<CompanyDTO> getAllCompany(){
        return service.getAllCompany();
    }

    @ApiOperation(value = "Retorna uma empresa")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getOneCompany(@PathVariable Long id, HttpServletRequest request){
        CompanyDTO res = service.getCompanyDTO(id, request);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Cria nova empresa")
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody Company novo){
        CompanyDTO res = service.createNewCompany(novo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Altera uma empresa")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> editCompany(@PathVariable Long id, @RequestBody Company novo, HttpServletRequest request){
        CompanyDTO res = service.editCompany(id,novo,request);

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
    public List<Optional<PersonDTO>> getAllPersonByJob(@PathVariable Long id ){
        return service.getAllPersonByJob(id);
    }

    @ApiOperation(value = "Seleciona candidato a vaga")
    @PostMapping("/candidate/hire")
    public String hirePerson(@RequestParam Long personId, Long jobId ){
       service.hirePerson(personId, jobId);
       return "Pessoa contratada";
    }
}
