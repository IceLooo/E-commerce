package kz.zhanayev.ecommerce.services;


public interface EmailService {
     void sendEmail(String to, String subject, String text);
}