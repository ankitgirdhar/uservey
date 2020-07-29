package com.spring.uservey.services;

import com.sendgrid.*;
import com.spring.uservey.models.Survey;
import com.spring.uservey.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;

@Service
public class SendgridService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SurveyRepository surveyRepository;

    public String sendMail(Survey survey, String username) throws IOException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        survey.setUsername(username);
        surveyRepository.save(survey);

        Personalization personalization = new Personalization();
        Email from = new Email("stanleycooper136@gmail.com");
        Email to = new Email();
        String[] mailList = survey.getRecipientEmails().split(",");
        for(int i=0;i<mailList.length;i++) {
            mailList[i] = mailList[i].trim();
        }
        helper.setTo(mailList);
        String subject = survey.getSubject();
        helper.setSubject(subject);
        String mailContent = new StringBuilder()
                .append("<html>")
                .append("<body>")
                .append("<div style=\"text-align: center;\">")
                .append("<h3>I'd like your input!</h3>")
                .append("<p>Please answer the following question:</p>")
                .append("<p>").append(survey.getContent()).append("</p>")
                .append("<div>").append("<a href=http://localhost:8080/sendgrid/response/").append(survey.getId()).append("/yes>Yes</a>")
                .append("</div>")
                .append("<div>")
                .append("<div>").append("<a href=http://localhost:8080/sendgrid/response/").append(survey.getId()).append("/no>No</a>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>")
                .toString();
        helper.setText(mailContent,true);
        javaMailSender.send(message);
        return "Sent Successfully";
    }

    public Iterable<Survey> findAllProjects(String name) {
        return surveyRepository.findAllByUsername(name);
    }

    public void deleteSurveyByIdentifier(String surveyId, String name) {
        Long surveyIdLong = Long.parseLong(surveyId);
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyIdLong);
        if(optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if(survey.getUsername().equals(name))
                surveyRepository.delete(survey);
        }

    }
}
















