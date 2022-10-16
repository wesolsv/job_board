package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.JobRepository;
import br.com.wszd.jboard.repository.PersonRepository;
import br.com.wszd.jboard.util.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ArrayList<Company> getAllCompany(){
        log.info("Buscando todas as empresas");
       return (ArrayList<Company>) repository.findAll();
    }

    public Company getCompany(Long id){
        log.info("Buscando empresa");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Company createNewCompany(Company novo) {
        log.info("Adicionando nova empresa");

        Company company = new Company.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cnpj(novo.getCnpj().replaceAll("\\D", ""))
                .password(novo.getPassword())
                .user(novo.getUser())
                .build();
        try{
            repository.save(company);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar empresa");
        }
        return company;
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
