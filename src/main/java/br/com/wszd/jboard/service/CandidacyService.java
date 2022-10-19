package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.util.CandidacyStatus;
import br.com.wszd.jboard.util.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CandidacyService {

    @Autowired
    private CandidacyRepository repository;

    @Autowired
    private PersonService personService;

    @Autowired
    private JobService jobService;

    public ArrayList<Candidacy> getAllCandidacy(){
        log.info("Buscando todas as candidaturas");
       return (ArrayList<Candidacy>) repository.findAll();
    }

    public ArrayList<PersonCandidacyDTO> getAllCandidacyByPersonId(Long id){
        log.info("Buscando todas as candidaturas pelo id");
        return repository.returnCandidacyByPerson(id);
    }

    public Candidacy getCandidacy(Long id){
        log.info("Buscando candidatura");
        return repository.findById(id).orElseThrow(
                () ->  new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Candidacy createNewCandidacy(Candidacy novo) {
        log.info("Adicionando nova candidatura");

        //Validar se job e pessoa existe e se o status do job é completed, caso for, não é possível me candidatar
        Person p = personService.getPerson(novo.getPersonId().getId());
        Job job = jobService.getJob(novo.getJob().getId());
        if(job.getStatus() == JobStatus.COMPLETED){
            throw new ResourceBadRequestException("Esta vaga não está disponível");
        }

        //Validando se a pessoa e a vaga já tem a mesma combinação de registros

        ArrayList<Candidacy> lista = (ArrayList<Candidacy>) repository.findAll();
        for(Candidacy cd : lista){
            if(cd.getJob().getId() == novo.getJob().getId() && cd.getPersonId().getId() == novo.getPersonId().getId()){
                throw  new ResourceBadRequestException(cd.getPersonId().getName() +" já está se candidatou a esta vaga ");
            }
        }

        //Criando candidatura

        Candidacy candidacy;
        try{
            candidacy = new Candidacy.Builder()
                    .personId(novo.getPersonId())
                    .job(novo.getJob())
                    .build();

            repository.save(candidacy);
        }catch(ResourceBadRequestException e){
            throw new ResourceBadRequestException("Falha ao criar candidatura");
        }
        return candidacy;
    }

    public Candidacy editCandidacy(Long id, Candidacy novo){
        log.info("Editando candidatura");

        Candidacy candidacy = getCandidacy(id);
        candidacy.setStatus(novo.getStatus());
        if(candidacy.getStatus() == CandidacyStatus.RECUSADA) {
            deleteCandidacy(id);
            return null;
        }
        return repository.save(candidacy);
    }

    public void deleteCandidacy(Long id){
        log.info("Deletando candidatura");
        getCandidacy(id);
        repository.deleteById(id);
    }

    //Deletar todas as candidaturas com o job id.
    public void deleteAllCandidacy(Long jobId){
        log.info("Deletando todas as candidaturas");
        ArrayList<Candidacy> candidaturas = getAllCandidacy();

        for(Candidacy cd : candidaturas){
            if(cd.getJob().getId() == jobId){
                deleteCandidacy(cd.getId());
            }
        }
    }
}
