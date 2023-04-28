package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import br.com.wszd.jboard.util.LogStatus;

public interface IUserService {
    public Users addRoleInUser(UserRoleDTO userRoleDTO);
    public void deleteUser(Long id);
    public Users findByEmail(String email);
    public <T> void createUsers(T entity);
    public Users getUserByPersonId(Person person);
    public Users getUserByCompanyId(Company company);
    public SessaoDTO validLogin(UserLoginDTO infoLogin);
    public void editUser(Users user);
    public Users returnEmailUser();
    public Users findUserByName(String nomeUsuario);
    public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method);
}
