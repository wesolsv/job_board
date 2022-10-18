package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT u "
            + "FROM Person u WHERE u.email = :email")
    Person findByEmail(@Param("email") String email);

    @Query("SELECT u "
            + "FROM Person u WHERE u.cpf = :cpf")
    Object findByCpf(@Param("cpf")String cpf);

    @Query("SELECT new br.com.wszd.jboard.dto.PersonDTO(p.id, p.name, p.phone, p.email, p.cpf )" +
            "FROM Person p")
    List<PersonDTO> listPerson();

    @Query("SELECT new br.com.wszd.jboard.dto.PersonDTO(p.id, p.name, p.phone, p.email, p.cpf )" +
            "FROM Person p " +
            "WHERE p.id = :id")
    Optional<PersonDTO> listPersonByCandidacyJobId(@Param("id") Long id);

    //"SELECT "
    //            + "new br.com.wszd.jboard.dto.PersonCandidacyDTO(cd.dateCandidacy, cd.status, cd.personId, cd.job) "
    //            + "FROM Candidacy cd "
}
