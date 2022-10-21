package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.*;
import br.com.wszd.jboard.util.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private CandidacyService candidacyService;
    @Autowired
    private PersonService personService;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserService createRoleUserService;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<CompanyDTO> getAllCompany(){
        log.info("Buscando todas as empresas");
       return repository.listCompany();
    }

    public CompanyDTO getCompanyDTO(Long id){
        log.info("Buscando empresa");
        Company realCompany = repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));

        return new CompanyDTO.Builder()
                .id(realCompany.getId())
                .name(realCompany.getName())
                .phone(realCompany.getPhone())
                .email(realCompany.getEmail())
                .cnpj(realCompany.getCnpj())
                .build();
    }

    private Company getCompany(Long id){
        log.info("Buscando empresa");
        return repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public CompanyDTO createNewCompany(Company novo) {
        log.info("Adicionando nova empresa");

        List<Long> listIdRoles = Arrays.asList(3L);

        try{
            //Validando a existencia do email ou cnpj nas tabelas de company ou users
            repository.findByEmail(novo.getEmail());
            repository.findByCnpj(novo.getCnpj());
            userService.findByEmail(novo.getEmail());

        }catch (ResourceBadRequestException e){
            throw new ResourceBadRequestException("Email ou CNPJ já cadastrado, verfique seus dados");
        }

        Company company = repository.save(new Company.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cnpj(novo.getCnpj().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build());

        //Criando usuário no repositorio
        Users user = userService.createUser(new Users.Builder()
                .email(company.getEmail())
                .password(company.getPassword())
                .personId(null)
                .companyId(company)
                .build());

        //Criando atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
        createRoleUserService.execute(userRoleDTO);

        return new CompanyDTO.Builder()
                .id(company.getId())
                .name(company.getName())
                .phone(company.getPhone())
                .email(company.getEmail())
                .cnpj(company.getCnpj())
                .build();
    }

    public CompanyDTO editCompany(Long id, Company novo){
        log.info("Editando empresa");
        getCompany(id);
        novo.setId(id);
        repository.save(novo);
        return new CompanyDTO.Builder()
                .id(novo.getId())
                .name(novo.getName())
                .phone(novo.getPhone())
                .email(novo.getEmail())
                .cnpj(novo.getCnpj())
                .build();
    }

    public void deleteCompany(Long id){
        log.info("Deletando empresa");

        Company company = getCompany(id);
        Users user = userService.getUserByCompanyId(company);
        userService.deleteUser(user.getId());

        repository.deleteById(id);
    }

    public List<Optional<PersonDTO>> getAllPersonByJob(Long jobId) {
        log.info("Buscando todas as pessoas da vaga de id " + jobId);

        List<Optional<PersonDTO>> pessoas = new ArrayList<>();
        List<Candidacy> candidaturas = candidacyService.getAllCandidacy();

        try {
            for(Candidacy cd : candidaturas){
                if(cd.getJob().getId() == jobId){
                    pessoas.add(personService.listPersonByCandidacyJobId(cd.getPersonId().getId()));
                }
            }
        }catch (ResourceBadRequestException e){
            throw new ResourceBadRequestException("O job id " + jobId + " não existe");
        }

       return pessoas;
    }

    public void hirePerson(Long personId, Long jobId) {
        //validando se pessoa existe e se job existe

        personService.getPerson(personId);
        Job job = jobService.getJob(jobId);

        List<Optional<PersonDTO>> pessoas = getAllPersonByJob(jobId);

        try{
            for(Optional<PersonDTO> p : pessoas){
                if(p.get().getId() == personId){
                    job = jobService.getJob(jobId);
                    job.setPersonId(personService.getPerson(personId));
                    job.setStatus(JobStatus.COMPLETED);

                    candidacyService.deleteAllCandidacy(jobId);
                    jobService.createNewJob(job);
                }
            }
        }catch(ResourceBadRequestException e){
            throw new ResourceBadRequestException("Não foi encontrada candidatura para a pessoa id " +personId);
        }
    }
}
