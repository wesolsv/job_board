package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u "
            + "FROM Users u JOIN FETCH u.roles WHERE u.email = :email")
    Users findByEmailFetchRoles(@Param("email") String email);

    @Query("SELECT u "
            + "FROM Users u WHERE u.email = :email")
    Users findByEmail(@Param("email") String email);
}
