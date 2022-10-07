package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.util.CandidacyStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CandidacyService {

    @Autowired
    private CandidacyRepository repository;

    public ArrayList<Candidacy> getAllCandidacy(){
        log.info("Buscando todas as candidaturas");
       return (ArrayList<Candidacy>) repository.findAll();
    }

    public ArrayList<PersonCandidacyDTO> getAllCandidacy(Long id){
        log.info("Buscando todas as candidaturas pelo id");
        return (ArrayList<PersonCandidacyDTO>) repository.returnCandidacyByPerson(id);
    }

    public Candidacy getCandidacy(Long id){
        log.info("Buscando candidatura");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Candidacy createNewCandidacy(Candidacy novo) {
        log.info("Adicionando nova candidatura");

        Candidacy candidacy;

        try{
            candidacy = new Candidacy.Builder()
                    .personId(novo.getPersonId())
                    .job(novo.getJob())
                    .build();

            repository.save(candidacy);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar candidatura");
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
}
