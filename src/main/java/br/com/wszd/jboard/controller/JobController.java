package br.com.wszd.jboard.controller;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/job")
@Api(value = "Job")
public class JobController {

    @Autowired
    private JobService service;

    @ApiOperation(value = "Retorna todas os empregos")
    @GetMapping
    public List<JobDTO> getAllJob(){
        return service.getAllJobs();
    }

    @ApiOperation(value = "Retorna um emprego")
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getOneJob(@PathVariable Long id){
        JobDTO res = service.getJobDTO(id);
        if(res != null){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
    @ApiOperation(value = "Cria novo emprego")
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job novo){
        Job res = service.createNewJob(novo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(res.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Altera um emprego")
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> editJob(@PathVariable Long id, @RequestBody Job novo){
        JobDTO res = service.editJob(id,novo);

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
