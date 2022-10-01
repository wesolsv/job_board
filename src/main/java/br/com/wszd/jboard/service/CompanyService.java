package br.com.wszd.jboard.service;

import br.com.wszd.jboard.exceptions.BadRequestException;
import br.com.wszd.jboard.exceptions.ObjectNotFoundException;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

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

        Company Company = new Company.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cnpj(novo.getCnpj().replaceAll("\\D", ""))
                .build();
        try{
            repository.save(Company);
        }catch(BadRequestException e){
            throw new BadRequestException("Falha ao criar empresa");
        }
        return Company;
    }

    public Company editCompany(Company novo){
        log.info("Editando empresa");
        getCompany(novo.getId());
        return repository.save(novo);
    }

    public void deleteCompany(Long id){
        log.info("Deletando empresa");
        getCompany(id);
        repository.deleteById(id);
    }
}
