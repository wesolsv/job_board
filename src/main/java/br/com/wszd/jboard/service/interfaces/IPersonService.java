package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    public List<PersonDTO> getAllPerson();

    public PersonDTO getPersonDTO(Long id);

    public Person getPerson(Long id);

    public PersonDTO createNewPerson(Person novo);

    public PersonDTO editPerson(Long id, Person novo);

    public void deletePerson(Long id);

    public void deleteOnePerson(Long id);

    public Optional<PersonDTO> listPersonByCandidacyJobId(Long id);
}
