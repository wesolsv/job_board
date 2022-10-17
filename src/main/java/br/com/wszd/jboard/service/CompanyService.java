package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
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
    private CandidacyRepository candidacyRepository;

    @Autowired
    private CandidacyService candidacyService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService createRoleUserService;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public ArrayList<Company> getAllCompany(){
        log.info("Buscando todas as empresas");
       return (ArrayList<Company>) repository.findAll();
    }

    public Company getCompany(Long id){
        log.info("Buscando empresa");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public CompanyDTO createNewCompany(Company novo) {
        log.info("Adicionando nova empresa");

        List<Long> listIdRoles = Arrays.asList(3L);

        if(repository.findByEmail(novo.getEmail()) != null || repository.findByCnpj(novo.getCnpj()) != null){
            throw new BadRequestException("Email ou CNPJ já cadastrado, verfique seus dados");
        }
        Company company = repository.save(new Company.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cnpj(novo.getCnpj().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build());

        //Criando atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(company.getId(), listIdRoles);
        createRoleUserService.execute(userRoleDTO);

        userRepository.save(new Users.Builder()
                .email(company.getEmail())
                .password(company.getPassword())
                .personId(null)
                .companyId(company)
                .build());

        return new CompanyDTO.Builder()
                .id(company.getId())
                .name(company.getName())
                .phone(company.getPhone())
                .email(company.getEmail())
                .cnpj(company.getCnpj())
                .build();
    }

    public Company editCompany(Long id, Company novo){
        log.info("Editando empresa");
        getCompany(id);
        novo.setId(id);
        return repository.save(novo);
    }

    public void deleteCompany(Long id){
        log.info("Deletando empresa");
        getCompany(id);
        repository.deleteById(id);
    }

    public List<Optional<Person>> getAllPersonByJob(Long jobId) {
        log.info("Buscando todas as pessoas da vaga de id " + jobId);

        List<Optional<Person>> pessoas = new ArrayList<>();
        List<Candidacy> candidaturas =candidacyRepository.findAll();

        try {
            for(Candidacy cd : candidaturas){
                if(cd.getJob().getId() == jobId){
                    pessoas.add(personRepository.findById(cd.getPersonId().getId()));
                }
            }
        }catch (BadRequestException e){
            throw new BadRequestException("O job id " + jobId + " não existe");
        }

       return pessoas;
    }

    public void hirePerson(Long personId, Long jobId) {
        List<Optional<Person>> pessoas = getAllPersonByJob(jobId);

        try{
            for(Optional<Person> p : pessoas){
                if(p.get().getId() == personId){
                    Job job = jobService.getJob(jobId);

                    job.setPersonId(personService.getPerson(personId));
                    job.setStatus(JobStatus.COMPLETED);
                    candidacyService.deleteAllCandidacy(jobId);
                    jobRepository.save(job);
                }
            }
        }catch(BadRequestException e){
            throw new BadRequestException("Não foi encontrada candidatura para a pessoa id " +personId);
        }
    }
}
