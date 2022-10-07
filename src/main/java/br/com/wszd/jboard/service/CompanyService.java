package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.repository.CompanyRepository;
import br.com.wszd.jboard.repository.PersonRepository;
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
    private PersonRepository personRepository;

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

    public List<Optional<Person>> getAllPersonByJob(Long id) {
        log.info("Buscando todas as pessoas da vaga de id " + id);

        List<Optional<Person>> pessoas = null;
        List<Candidacy> candidaturas =candidacyRepository.findAll();

        for(Candidacy cd : candidaturas){
            if(cd.getJob().getId() == id){
               pessoas.add(personRepository.findById(cd.getPersonId().getId()));
            }

        }
       return pessoas;
    }
}
