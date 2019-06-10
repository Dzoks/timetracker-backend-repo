package rs.dzoks.timetracker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rs.dzoks.timetracker.common.BadRequestException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class Notification {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.username.personal}")
    private String personal;

    @Autowired
    public Notification(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendSimpleMail(String recipientMail, String subject, String body) throws BadRequestException {
        sendMail(recipientMail, subject, body, false, null, null);
    }

    @Async
    public void sendMailWithAttachments(String recipientMail, String subject, String body, List<Attachment> attachments) throws BadRequestException {
        sendMail(recipientMail, subject, body, true, null, attachments);

    }

    @Async
    public void sendMailWithInlineAttachments(String recipientMail, String subject, String body, List<Attachment> inlineAttachments) throws BadRequestException {
        sendMail(recipientMail, subject, body, true, inlineAttachments, null);

    }

    @Async
    public void sendMailWithInlineAndNormalAttachments(String recipientMail, String subject, String body, List<Attachment> inlineAttachments, List<Attachment> attachments) throws BadRequestException {
        sendMail(recipientMail, subject, body, true, inlineAttachments, attachments);
    }

    private void sendMail(String recipientMail, String subject, String body, boolean multipart, List<Attachment> inlineAttachments, List<Attachment> attachments) throws BadRequestException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "UTF-8");
            helper.setTo(recipientMail);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(username, personal);
            if (multipart) {
                if (inlineAttachments != null)
                    for (Attachment inlineAttachment : inlineAttachments) {
                        helper.addInline(inlineAttachment.contentId, inlineAttachment.resource);
                    }
                if (attachments != null)
                    for (Attachment attachment : attachments) {
                        helper.addAttachment(attachment.contentId, attachment.resource);
                    }
            }
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BadRequestException("Exception");
        }
    }

    public static class Attachment {
        private String contentId;
        private Resource resource;

        public Attachment(String contentId, Resource resource) {
            this.contentId = contentId;
            this.resource = resource;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }
    }

}

