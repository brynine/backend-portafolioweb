package ec.edu.ups.ppw.gproyecto.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

@ApplicationScoped
public class EmailService {

    private Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    "TU_CORREO@gmail.com",
                    "TU_APP_PASSWORD"
                );
            }
        });
    }

    public void enviarCorreo(String to, String subject, String body) {
        try {
            Session session = crearSesion();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@portafolio.com"));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
