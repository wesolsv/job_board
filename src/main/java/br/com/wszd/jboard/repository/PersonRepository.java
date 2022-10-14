package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.PersonCandidacyDTO;
import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT "
            + "new br.com.wszd.jboard.dto.PersonDTO(p.email, p.password) "
            + "FROM Person p "
            + "WHERE email = :email "
            + "AND password = :password")
    Person findByEmail(
            @Param("email") String email,
            @Param("password") String password);
}
