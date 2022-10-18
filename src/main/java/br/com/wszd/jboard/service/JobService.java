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

    public JobDTO getJobDTO(Long id){
        log.info("Buscando job");

        Job realJob =  repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));

        JobDTO job = new JobDTO.Builder()
                        .id(realJob.getId())
                        .opportunity(realJob.getOpportunity())
                        .description(realJob.getDescription())
                        .type(realJob.getType())
                        .salary(realJob.getSalary())
                        .benefits(realJob.getBenefits())
                        .status(realJob.getStatus())
                        .companyName(realJob.getCompanyId().getName())
                        .datePublish(realJob.getDatePublish())
                        .build();
        return job;
    }

    public Job getJob(Long id){
        log.info("Buscando JOB FULL");

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

    public JobDTO editJob(Long id, Job novo){
        Job returnJob = getJob(id);
        log.info("Editando Job");
        novo.setDatePublish(returnJob.getDatePublish());
        novo.setId(id);

        returnJob = repository.save(novo);

        return new JobDTO.Builder()
                .id(returnJob.getId())
                .opportunity(returnJob.getOpportunity())
                .description(returnJob.getDescription())
                .type(returnJob.getType())
                .salary(returnJob.getSalary())
                .benefits(returnJob.getBenefits())
                .status(returnJob.getStatus())
                .companyName(returnJob.getCompanyId().getName())
                .datePublish(returnJob.getDatePublish())
                .build();
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        getJob(id);
        repository.deleteById(id);
    }
}
