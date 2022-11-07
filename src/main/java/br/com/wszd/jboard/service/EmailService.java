package br.com.wszd.jboard.service;


import br.com.wszd.jboard.model.Company;
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

        //createEmail(toEmail)

        String subject = "Cadastro realizado com sucesso";
        String body = "Prabéns pelo cadastro em JobBoard, seu usuário é " + user.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreplayjobboard@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Email enviado com sucesso");

    }
}
