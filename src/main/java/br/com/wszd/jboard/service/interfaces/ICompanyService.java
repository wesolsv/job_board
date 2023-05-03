package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Company;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {
    public List<CompanyDTO> getAllCompany();

    public CompanyDTO getCompanyDTO(Long id);

    public Company getCompany(Long id);

    public CompanyDTO createNewCompany(Company novo);

    public CompanyDTO editCompany(Long id, Company novo);

    public void deleteCompany(Long id);

    public void deleteOneCompany(Long id);

    public List<Optional<PersonDTO>> getAllPersonByJob(Long jobId);

    public void hirePerson(Long personId, Long jobId);

}
