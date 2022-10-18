package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JobService {

    @Autowired
    private JobRepository repository;

    public List<JobDTO> getAllJobs(){
        log.info("Buscando todas os jobs");
       return repository.listJobs();
    }

    public Job getJob(Long id){
        log.info("Buscando job");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Job createNewJob(Job novo) {
        log.info("Adicionando nova job");

        Job job;
        try{
            job = repository.save(new Job.Builder()
                    .opportunity(novo.getOpportunity())
                    .description(novo.getDescription())
                    .type(novo.getType())
                    .salary(novo.getSalary())
                    .benefits(novo.getBenefits())
                    .status(novo.getStatus())
                    .personId(novo.getPersonId())
                    .companyId(novo.getCompanyId())
                    .build());
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar job");
        }
        return job;
    }

    public Job editJob(Long id, Job novo){
        Job returnJob = getJob(id);
        log.info("Editando Job");
        novo.setDatePublish(returnJob.getDatePublish());
        novo.setId(id);
        return repository.save(novo);
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        getJob(id);
        repository.deleteById(id);
    }
}
