package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.scheduler.EmailScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getTo());
            messageHelper.setSubject(mail.getSubject());
            if (mail.getSubject().equals(TrelloService.SUBJECT)) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            }
            if (mail.getSubject().equals(EmailScheduler.SUBJECT)) {
                messageHelper.setText(mailCreatorService.buildSummaryScheduledEmail(mail.getMessage()), true);
            }
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getTo());
        Optional.ofNullable(mail.getCc()).ifPresent(mailMessage::setCc);
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
