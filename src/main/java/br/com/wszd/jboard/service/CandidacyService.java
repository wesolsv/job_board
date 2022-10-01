package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Candidacy;
import br.com.wszd.jboard.repository.CandidacyRepository;
import br.com.wszd.jboard.repository.CompanyRepository;
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

    public Candidacy getCandidacy(Long id){
        log.info("Buscando candidatura");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public Candidacy createNewCandidacy(Candidacy novo) {
        log.info("Adicionando nova candidatura");

        Candidacy candidacy = new Candidacy.Builder()
                .personId(novo.getPersonId())
                .jobPublished(novo.getJobPublished())
                .build();
        try{
            repository.save(candidacy);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar candidatura");
        }
        return candidacy;
    }

    public Candidacy editCandidacy(Candidacy novo){
        log.info("Editando candidatura");
        getCandidacy(novo.getId());
        return repository.save(novo);
    }

    public void deleteCandidacy(Long id){
        log.info("Deletando candidatura");
        getCandidacy(id);
        repository.deleteById(id);
    }
}
