package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.model.JobPublished;
import br.com.wszd.jboard.service.JobPublishedService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/jobpublished")
public class JobPublishedController {

    @Autowired
    private JobPublishedService service;

    @ApiOperation(value = "Retorna todas os empregos")
    @GetMapping
    public ArrayList<JobPublished> getAllJobPulished(){
        return service.getAllJobPublished();
    }

    @ApiOperation(value = "Retorna um emprego")
    @GetMapping("/{id}")
    public ResponseEntity<JobPublished> getOneJobPublished(@PathVariable Long id){
        JobPublished res = service.getJobPublished(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria novo emprego")
    @PostMapping
    public ResponseEntity<JobPublished> createJobPublished(@RequestBody JobPublished novo){
        JobPublished res = service.createNewJobPublished(novo);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Altera um emprego")
    @PutMapping
    public ResponseEntity<JobPublished> editJobPublished(@RequestBody JobPublished novo){
        JobPublished res = service.editJobPublished((novo));

        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Deletando um emprego")
    @DeleteMapping("/{id}")
    public void deleteJobPublished(@PathVariable Long id){
        service.deleteJobPublished(id);
    }
}
