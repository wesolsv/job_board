package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT u "
            + "FROM Company u WHERE u.email = :email")
    Company findByEmail(@Param("email") String email);

    @Query("SELECT u "
            + "FROM Company u WHERE u.cnpj = :cnpj")
    Object findByCnpj(@Param("cnpj")String cnpj);

    @Query("SELECT new br.com.wszd.jboard.dto.CompanyDTO(c.id, c.name, c.phone, c.email, c.cnpj )" +
            " FROM Company c")
    List<CompanyDTO> listCompany();
}
