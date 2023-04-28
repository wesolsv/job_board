package br.com.wszd.jboard.service;

import br.com.wszd.jboard.model.Job;
import br.com.wszd.jboard.model.Person;
import br.com.wszd.jboard.model.Users;

public interface IEmailService {
    public void sendEmailToUserCreateUsers(Users user);

    public <T> void sendEmailEditUser(T entity);

    public void sendEmailNewCandidacy(Person person, Job job);

    public void sendEmailNewHire(Person person, Job job);

    public void createEmail(String toEmail, String subject, String body);
}
