package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.LogTable;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LogRepository extends JpaRepository<LogTable, Long> {
}
