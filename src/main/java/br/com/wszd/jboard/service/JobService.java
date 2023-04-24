package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.JobRepository;
import br.com.wszd.jboard.util.ValidacaoUsuarioLogged;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JobService {

    @Autowired
    private JobRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyRepository companyRepository;

    public List<JobDTO> getAllJobs(){
        log.info("Buscando todas os jobs");

        List<JobDTO> list = null;
        list =  repository.listJobs();
        List<JobDTO> listReturn = new ArrayList<>();

        Users user = userService.returnEmailUser();

        ValidacaoUsuarioLogged.validEmailUsuario(user.getCompanyId(), userService.returnEmailUser());

        for(JobDTO job : list){
            if(job.getCompanyId() == user.getCompanyId().getId()){
                listReturn.add(job);
            };
        }
        return listReturn;
    }

    public List<JobDTO> searchJobsByParam(String opportunity) {
        log.info("Buscando todas os jobs com parametro de busca");
        return repository.listJobsByOpportunity(opportunity.toUpperCase());
    }
    public List<JobDTO> searchJobsByType(String type) {
        log.info("Buscando todas os jobs com parametro de busca");
        return repository.listJobsByType(type.toUpperCase());
    }

    public JobDTO getJobDTO(Long id){
        log.info("Buscando job");

        Job realJob =  repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));

        Optional<Company> company = companyRepository.findById(realJob.getCompanyId().getId());
        ValidacaoUsuarioLogged.validEmailUsuario(company.get().getId(), userService.returnEmailUser());

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
                        .companyId(company.get().getId())
                        .build();
        return job;
    }

    public Job getJob(Long id){
        log.info("Buscando JOB FULL");

        return repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Job createNewJob(Job novo) {
        log.info("Adicionando nova job");

        ValidacaoUsuarioLogged.validEmailUsuario(novo.getCompanyId(), userService.returnEmailUser());
        Users user =  userService.returnEmailUser();
        Optional<Company> company = companyRepository.findById(user.getCompanyId().getId());

        Job job;
        try{
            job = new Job.Builder()
                    .opportunity(novo.getOpportunity().toUpperCase())
                    .description(novo.getDescription().toUpperCase())
                    .type(novo.getType().toUpperCase())
                    .salary(novo.getSalary())
                    .benefits(novo.getBenefits().toUpperCase())
                    .status(novo.getStatus())
                    .personId(novo.getPersonId())
                    .companyId(company.get())
                    .build();

            repository.save(job);
        }catch(ResourceBadRequestException e){
            throw new ResourceBadRequestException("Falha ao criar job");
        }
        return job;
    }

    public JobDTO editJob(Long id, Job novo){
        Job returnJob = getJob(id);
        log.info("Editando Job");
        novo.setDatePublish(returnJob.getDatePublish());
        novo.setId(id);
        novo.setCompanyId(returnJob.getCompanyId());

        Optional<Company> company = companyRepository.findById(returnJob.getCompanyId().getId());

        ValidacaoUsuarioLogged.validEmailUsuario(novo.getCompanyId(), userService.returnEmailUser());

        returnJob = repository.save(novo);

        return new JobDTO.Builder()
                .id(returnJob.getId())
                .opportunity(returnJob.getOpportunity().toUpperCase())
                .description(returnJob.getDescription().toUpperCase())
                .type(returnJob.getType().toUpperCase())
                .salary(returnJob.getSalary())
                .benefits(returnJob.getBenefits().toUpperCase())
                .status(returnJob.getStatus())
                .companyName(returnJob.getCompanyId().getName())
                .datePublish(returnJob.getDatePublish())
                .companyId(returnJob.getCompanyId().getId())
                .build();
    }
    public Job saveEditJob(Job novo){
        return repository.save(novo);
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        Optional<Company> company = companyRepository.findById(getJob(id).getCompanyId().getId());

        ValidacaoUsuarioLogged.validEmailUsuario(getJob(id).getCompanyId(), userService.returnEmailUser());

        deleteOneJob(id);
    }

    public void deleteOneJob(Long id){
        repository.deleteById(id);
    }

}
