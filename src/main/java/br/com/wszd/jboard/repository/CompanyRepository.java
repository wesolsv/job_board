package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
