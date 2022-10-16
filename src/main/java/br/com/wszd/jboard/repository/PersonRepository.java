package br.com.wszd.jboard.repository;

import br.com.wszd.jboard.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    @Query("SELECT u "
            + "FROM Person u JOIN FETCH u.roles WHERE u.email = :email")
    Person findByEmailFetchRoles(@Param("email") String email);

    @Query("SELECT u "
            + "FROM Person u WHERE u.cpf = :cpf")
    Object findByCpf(@Param("cpf")String cpf);

    @Query("SELECT u "
            + "FROM Person u WHERE u.phone = :phone")
    Object findByPhone(@Param("phone")String phone);
}
