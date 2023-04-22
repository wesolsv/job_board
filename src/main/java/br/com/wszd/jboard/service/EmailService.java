package br.com.wszd.jboard.service;


import br.com.wszd.jboard.model.Company;
import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PersonService personService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    public void sendEmailToUserCreateUsers(Users user) {

        Company company = null;
        Person person = null;
        String toEmail = "";

        if(user.getCompanyId() != null){
            company = companyService.getCompany(user.getCompanyId().getId());
            toEmail = company.getEmail();
        }else{
            person = personService.getPerson(user.getPersonId().getId());
            toEmail = person.getEmail();
        }

        String subject = "Cadastro realizado com sucesso";
        String body = "Parabéns pelo cadastro em JobBoard, seu usuário é " + user.getEmail();

        createEmail(toEmail, subject, body);
    }

    public <T> void sendEmailEditUser(T entity) {
        String toEmail = "";

        if(entity instanceof Company){
            toEmail = ((Company) entity).getEmail();
        }else{
            toEmail = ((Person) entity).getEmail();
        }

        String subject = "Edição de usuario com sucesso";
        String body = "Usuário Editado";

        createEmail(toEmail, subject, body);
    }

    public void sendEmailNewCandidacy(Person person, Job job) {

        String toEmail = personService.getPerson(person.getId()).getEmail();

        String subject = "Parabéns pela nova candidatura";
        String body = "Agora é só aguardar o retorno da Empresa "
                + job.getCompanyId().getName() + " para a vaga " + job.getOpportunity();

        createEmail(toEmail, subject, body);
    }

    public void sendEmailNewHire(Person person, Job job) {

        String toEmail = personService.getPerson(person.getId()).getEmail();

        String subject = "Parabéns você foi aprovado";
        String body = "Parabéns você foi aprovado pela Empresa "
                + job.getCompanyId().getName() + " \n para a vaga " + job.getOpportunity() + " aguarde mais informações";

        createEmail(toEmail, subject, body);
    }

    private void createEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreplayjobboard@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Email enviado com sucesso");
    }
}
