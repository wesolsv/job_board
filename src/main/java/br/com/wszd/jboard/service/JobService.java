package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class JobService {

    @Autowired
    private JobRepository repository;

    public ArrayList<Job> getAllJobs(){
        log.info("Buscando todas os jobs");
       return (ArrayList<Job>) repository.findAll();
    }

    public Job getJob(Long id){
        log.info("Buscando job");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Job createNewJob(Job novo) {
        log.info("Adicionando nova job");

        Job job = new Job.Builder()
                .opportunity(novo.getOpportunity())
                .type(novo.getType())
                .salary(novo.getSalary())
                .benefits(novo.getBenefits())
                .status(novo.getStatus())
                .personId(novo.getPersonId())
                .companyId(novo.getCompanyId())
                .build();
        try{
            repository.save(job);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar job");
        }
        return job;
    }

    public Job editJob(Job novo){
        log.info("Editando Job");
        getJob(novo.getId());
        return repository.save(novo);
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        getJob(id);
        repository.deleteById(id);
    }
}
