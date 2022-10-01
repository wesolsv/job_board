package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.service.JobService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/job")
public class JobController {

    @Autowired
    private JobService service;

    @ApiOperation(value = "Retorna todas os empregos")
    @GetMapping
    public ArrayList<Job> getAllPeople(){
        return service.getAllJobs();
    }

    @ApiOperation(value = "Retorna um emprego")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getOnePerson(@PathVariable Long id){
        Job res = service.getJob(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria novo emprego")
    @PostMapping
    public ResponseEntity<Job> createPerson(@RequestBody Job novo){
        Job res = service.createNewJob(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Altera um emprego")
    @PutMapping
    public ResponseEntity<Job> editPerson(@RequestBody Job novo){
        Job res = service.editJob((novo));

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando um emprego")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        service.deleteJob(id);
    }
}
