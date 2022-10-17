package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT u "
            + "FROM Company u WHERE u.email = :email")
    Company findByEmail(@Param("email") String email);

    @Query("SELECT u "
            + "FROM Company u WHERE u.cnpj = :cnpj")
    Object findByCnpj(@Param("cnpj")String cnpj);

}
