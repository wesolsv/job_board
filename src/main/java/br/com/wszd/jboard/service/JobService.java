package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.JobRepository;
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

    public List<JobDTO> searchJobsByParam(String param) {
        log.info("Buscando todas os jobs com parametro de busca");
        List<JobDTO> list = null;
        list =  repository.listJobsByParam(param);
        return list;
    }

    public JobDTO getJobDTO(Long id){
        log.info("Buscando job");

        Job realJob =  repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));

        Optional<Company> company = companyRepository.findById(realJob.getCompanyId().getId());
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(validAdminUser(email.toString())){
        }else{
            validEmailUser(company.get(), email.toString());
        }

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

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user =  userService.findByEmail(email.toString());
        Optional<Company> company = companyRepository.findById(user.getCompanyId().getId());

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

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(company.get(), email.toString());

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
                .companyId(returnJob.getCompanyId().getId())
                .build();
    }
    public Job saveEditJob(Job novo){
        return repository.save(novo);
    }

    public void deleteJob(Long id){
        log.info("Deletando Job");
        Optional<Company> company = companyRepository.findById(getJob(id).getCompanyId().getId());

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(company.get(), email.toString());

        deleteOneJob(id);
    }

    public void deleteOneJob(Long id){
        repository.deleteById(id);
    }

    public void validEmailUser(Company company, String emailRequest) {
        log.info("Validando usuario");
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
            log.info("Validando usuario admin");
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
