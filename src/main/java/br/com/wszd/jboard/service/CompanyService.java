package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.CandidacyDTO;
import br.com.wszd.jboard.dto.CompanyDTO;
import br.com.wszd.jboard.dto.PersonDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.exceptions.ResourceObjectNotFoundException;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.*;
import br.com.wszd.jboard.util.JobStatus;
import br.com.wszd.jboard.util.LogStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private CandidacyService candidacyService;
    @Autowired
    private PersonService personService;
    @Autowired
    private JobService jobService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserService createRoleUserService;

    @Autowired
    private LogService logService;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<CompanyDTO> getAllCompany() {
        log.info("Buscando todas as empresas");
        return repository.listCompany();
    }

    public CompanyDTO getCompanyDTO(Long id) {
        log.info("Buscando empresa");

        Company realCompany = repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(realCompany, email.toString());

        return new CompanyDTO.Builder()
                .id(realCompany.getId())
                .name(realCompany.getName())
                .phone(realCompany.getPhone())
                .email(realCompany.getEmail())
                .cnpj(realCompany.getCnpj())
                .build();
    }

    public Company getCompany(Long id) {
        log.info("Buscando empresa");
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Objeto não encontrado com o id = " + id));
    }

    public void createUser(Company company) {
        List<Long> listIdRoles = Arrays.asList(3L);

        //Criando usuário no repositorio
        Users user = userService.createUser(new Users.Builder()
                .email(company.getEmail())
                .password(company.getPassword())
                .personId(null)
                .companyId(company)
                .build());

        //Criando e atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(user.getId(), listIdRoles);
        userService.addRoleInUser(userRoleDTO);

        //Criando log de inserção
        createLog(company.toString(), "/company", user.getId(), LogStatus.SUCESSO, HttpMethod.POST.toString());
    }

    public CompanyDTO createNewCompany(Company novo) {
        log.info("Adicionando nova empresa");

        if (repository.findByEmail(novo.getEmail()) != null && repository.findByCnpj(novo.getCnpj()) != null && userService.findByEmail(novo.getEmail()) != null) {
            createLog(novo.toString(), "/company", 0L, LogStatus.ERRO, HttpMethod.POST.toString());
            throw new ResourceBadRequestException("Email ou CNPJ já cadastrado, verfique seus dados");
        }

        Company company = new Company.Builder()
                .name(novo.getName())
                .phone(novo.getPhone().replaceAll("\\D", ""))
                .email(novo.getEmail())
                .cnpj(novo.getCnpj().replaceAll("\\D", ""))
                .password(passwordEncoder().encode(novo.getPassword()))
                .user(novo.getUser())
                .build();

        company = saveCompany(company);
        createUser(company);

        return new CompanyDTO.Builder()
                .id(company.getId())
                .name(company.getName())
                .phone(company.getPhone())
                .email(company.getEmail())
                .cnpj(company.getCnpj())
                .build();
    }

    public Company saveCompany(Company novo){
        return repository.save(novo);
    }

    public CompanyDTO editCompany(Long id, Company novo) {
        log.info("Editando empresa");
        //Validando a existencia de person com o id informado
        getCompany(id);
        novo.setId(id);

        //validando se a pessoa que está editando pode realizar a ação
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(getCompany(id), email.toString());

        //Salvando alteracao do usuario
        saveEditCompany(novo);

        //Editando email do usuario editado anteriormente
        Users user = userService.getUserByCompanyId(getCompany(id));
        user.setEmail(novo.getEmail());
        userService.editUser(user);

        //Salvando o log da edicao efetuada
        createLog(novo.toString(), "/company{" + id + "}",
                userService.getUserByCompanyId(getCompany(id)).getId(), LogStatus.SUCESSO, HttpMethod.PUT.toString());

        return new CompanyDTO.Builder()
                .id(novo.getId())
                .name(novo.getName())
                .phone(novo.getPhone())
                .email(novo.getEmail())
                .cnpj(novo.getCnpj())
                .build();
    }

    public Company saveEditCompany(Company novo) {
        return repository.save(novo);
    }

    public void deleteCompany(Long id) {
        log.info("Deletando empresa");
        //Validando a existencia da company e usuario vinculado e excluindo ambos
        Company company = getCompany(id);
        Users user = userService.getUserByCompanyId(company);
        userService.deleteUser(user.getId());
        deleteOneCompany(id);

        //Salvando o log do delete efetuada
        createLog("Delete Company", "/company{" + id + "}",
                user.getId(), LogStatus.SUCESSO, HttpMethod.DELETE.toString());
    }

    public void deleteOneCompany(Long id){
        repository.deleteById(id);
    }

    public List<Optional<PersonDTO>> getAllPersonByJob(Long jobId) {
        log.info("Buscando todas as pessoas da vaga de id " + jobId);

        List<Optional<PersonDTO>> pessoas = new ArrayList<>();
        List<CandidacyDTO> candidaturas = candidacyService.getAllCandidacy();
        Job job = jobService.getJob(jobId);
        Optional<Company> realCompany = repository.findById(job.getCompanyId().getId());

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(realCompany.get(), email.toString());

        try {
            for (CandidacyDTO cd : candidaturas) {
                if (cd.getJob().getId() == jobId) {
                    pessoas.add(personService.listPersonByCandidacyJobId(cd.getPerson().getId()));
                }
            }
        } catch (ResourceBadRequestException e) {
            throw new ResourceBadRequestException("O job id " + jobId + " não existe");
        }

        return pessoas;
    }

    public void hirePerson(Long personId, Long jobId) {
        //validando se pessoa existe e se job existe

        personService.getPerson(personId);
        Job job = jobService.getJob(jobId);
        Optional<Company> realCompany = repository.findById(job.getCompanyId().getId());

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validEmailUser(realCompany.get(), email.toString());

        List<Optional<PersonDTO>> pessoas = getAllPersonByJob(jobId);

        try {
            for (Optional<PersonDTO> p : pessoas) {
                if (p.get().getId() == personId) {
                    job = jobService.getJob(jobId);
                    job.setPersonId(personService.getPerson(personId));
                    job.setStatus(JobStatus.COMPLETED);

                    candidacyService.deleteAllCandidacy(jobId);
                    jobService.saveEditJob(job);
                }
            }
        } catch (ResourceBadRequestException e) {
            throw new ResourceBadRequestException("Não foi encontrada candidatura para a pessoa id " + personId);
        }
    }

    public void validEmailUser(Company company, String emailRequest) {

        Users user = userService.findByEmail(emailRequest);

        ArrayList<String> rolesRetorno = new ArrayList<>();

        for (int i = 0; i < user.getRoles().size(); i++) {
            String j = user.getRoles().get(i).getName() + "";
            rolesRetorno.add(j);
        }

        if (rolesRetorno.contains("ADMIN") || company.getId() == user.getCompanyId().getId()) {
            log.info("Validado email do usuario ou usuario é admin");
        } else {
            throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
        }
    }

    public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method) {

        LogTable log = new LogTable.Builder()
                .payload(payload)
                .endpoint(endpoint)
                .userId(userId)
                .status(status)
                .dataInclusao(LocalDateTime.now())
                .method(method)
                .build();

        logService.createLog(log);
    }
}
