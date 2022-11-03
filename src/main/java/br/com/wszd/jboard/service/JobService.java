package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JobService {

    @Autowired
    private JobRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    public List<JobDTO> getAllJobs(){
        log.info("Buscando todas os jobs");

        List<JobDTO> list = repository.listJobs();
        List<JobDTO> listReturn = new ArrayList<>();

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(validAdminUser(email.toString())){
            return list;
        }

        Users user = userService.findByEmail(email.toString());

        for(JobDTO job : list){
            if(job.getCompanyId() == user.getCompanyId().getId()){
                listReturn.add(job);
            };
        }
        return listReturn;
    }

    public JobDTO getJobDTO(Long id){
        log.info("Buscando job");

        Job realJob =  repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));

        Company company = companyService.getCompany(realJob.getCompanyId().getId());

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(company, email.toString());

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
                        .companyId(company.getId())
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

        Job job;
        try{
            job = new Job.Builder()
                    .opportunity(novo.getOpportunity())
                    .description(novo.getDescription())
                    .type(novo.getType())
                    .salary(novo.getSalary())
                    .benefits(novo.getBenefits())
                    .status(novo.getStatus())
                    .personId(novo.getPersonId())
                    .companyId(novo.getCompanyId())
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
    public Job saveEditJob(Job novo){
        return repository.save(novo);
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        getJob(id);
        repository.deleteById(id);
    }

    public void deleteOneJob(Long id){
        repository.deleteById(id);
    }

    public void validEmailUser(Company company, String emailRequest) {

        Users user = userService.findByEmail(emailRequest);

        ArrayList<String> rolesRetorno = new ArrayList<>();

        for (int i = 0; i < user.getRoles().size(); i++) {
            String j = user.getRoles().get(i).getName() + "";
            rolesRetorno.add(j);
        }

        if (company.getId() == user.getCompanyId().getId()) {
            log.info("Validado email do usuario ou usuario é admin");
        } else {
            throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
        }
    }

    public boolean validAdminUser(String emailRequest){

            Users user = userService.findByEmail(emailRequest);

            ArrayList<String> rolesRetorno = new ArrayList<>();

            for (int i = 0; i < user.getRoles().size(); i++) {
                String j = user.getRoles().get(i).getName() + "";
                rolesRetorno.add(j);
            }

            if (rolesRetorno.contains("ADMIN")) {
                log.info("Usuário é ADMIN");
                return true;
            }
            return false;
    }
}
