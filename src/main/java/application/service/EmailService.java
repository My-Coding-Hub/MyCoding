package application.service;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService implements Serializable {

    private static final long serialVersionUID = 1L;

    public void enviarEmail(String destinatario, String assunto, String mensagemTexto) {
        // Configurações do Mailtrap
        String host = "sandbox.smtp.mailtrap.io";
        String usuario = "7927af1512a590";
        String senha = "b074d477949ca5";

        // Configurações do servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "2525");  // Porta para Mailtrap
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mycodinghub.tech"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);

            message.setContent(mensagemTexto, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}
