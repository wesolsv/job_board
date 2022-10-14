package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p "
            + "FROM Person p "
            + "WHERE p.email = :email"
    )
    public Person findByEmail(@Param("email") String email);

    //@Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")

//    @Query("SELECT "
//            + "new br.com.wszd.jboard.dto.PersonCandidacyDTO
//            (cd.dateCandidacy, cd.status, cd.personId, cd.job) "
//            + "FROM Candidacy cd ")

    /*@Query("SELECT p.id, p.cpf, p.email, p.name, p.password, p.phone "
            + "FROM Person p "
            + "WHERE p.email = :email"*/
}
