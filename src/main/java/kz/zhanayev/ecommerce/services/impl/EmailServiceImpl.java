//package kz.zhanayev.ecommerce.services.impl;
//
//import kz.zhanayev.ecommerce.exceptions.EmailSendException;
//import kz.zhanayev.ecommerce.services.EmailService;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailServiceImpl implements EmailService {
//
//    private final JavaMailSender mailSender;
//
//    public EmailServiceImpl(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Override
//    public void sendEmail(String to, String subject, String text) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(text);
//            mailSender.send(message);
//        } catch (Exception e) {
//            // Логирование ошибки
//            System.err.println("Ошибка при отправке email: " + e.getMessage());
//            throw new EmailSendException("Не удалось отправить письмо: " + e.getMessage(), e);
//        }
//    }
//}
