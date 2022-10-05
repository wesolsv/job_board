package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/job")
@Api(value = "Job")
public class JobController {

    @Autowired
    private JobService service;

    @ApiOperation(value = "Retorna todas os empregos")
    @GetMapping
    public ArrayList<Job> getAllJob(){
        return service.getAllJobs();
    }

    @ApiOperation(value = "Retorna um emprego")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getOneJob(@PathVariable Long id){
        Job res = service.getJob(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria novo emprego")
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job novo){
        Job res = service.createNewJob(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Altera um emprego")
    @PutMapping("/{id}")
    public ResponseEntity<Job> editJob(@PathVariable Long id, @RequestBody Job novo){
        Job res = service.editJob(id,novo);

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando um emprego")
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id){
        service.deleteJob(id);
    }
}
