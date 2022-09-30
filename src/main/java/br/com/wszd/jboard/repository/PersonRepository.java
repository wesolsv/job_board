package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
