package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.JobPublished;
import br.com.wszd.jboard.repository.JobPublishedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class JobPublishedService {

    @Autowired
    private JobPublishedRepository repository;

    public ArrayList<JobPublished> getAllJobPublished(){
        log.info("Buscando todas os empregos publicados");
       return (ArrayList<JobPublished>) repository.findAll();
    }

    public JobPublished getJobPublished(Long id){
        log.info("Buscando emprego publicado");
        return repository.findById(id).orElseThrow(
                () ->  new ObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public JobPublished createNewJobPublished(JobPublished novo) {
        log.info("Adicionando nova publicacao de emprego");

        JobPublished jobPublished = new JobPublished.Builder()
                .description(novo.getDescription())
                .jobId(novo.getJobId())
                .build();
        try{
            repository.save(jobPublished);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao publicar emprego");
        }
        return jobPublished;
    }

    public JobPublished editJobPublished(Long id, JobPublished novo){
        log.info("Editando emprego publicado");
        getJobPublished(id);
        novo.setId(id);
        return repository.save(novo);
    }

    public void deleteJobPublished(Long id){
        log.info("Deletando emprego publicado");
        getJobPublished(id);
        repository.deleteById(id);
    }
}
