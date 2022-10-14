package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p "
            + "WHERE p.email = :email"
    )
    public Person findByEmail(@Param("email") String email);
}
