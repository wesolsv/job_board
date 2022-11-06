package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.LogTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogTable, Long> {
}
