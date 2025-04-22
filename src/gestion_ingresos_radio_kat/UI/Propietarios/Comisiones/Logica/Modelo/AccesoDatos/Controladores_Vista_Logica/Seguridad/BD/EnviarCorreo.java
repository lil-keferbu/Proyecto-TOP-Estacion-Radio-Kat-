/*package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica.Seguridad.BD;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EnviarCorreo {

    public static boolean enviarCorreo(String destinatario, String nuevaPassword) {
        final String remitente = "";
        final String clave = "";

        // Configuración para TLS 1.2
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // Forzar TLS 1.2
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.ciphersuites", "TLS_AES_128_GCM_SHA256");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Recuperación de contraseña");
            message.setText("Hola,\n\nTu nueva contraseña es: " + nuevaPassword + "\n\nSaludos,\nRadio Kat");

            Transport.send(message);
            System.out.println("Correo enviado correctamente.");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}*/

