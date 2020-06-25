package main.java;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import tray.notification.NotificationType;
import com.github.plushaze.traynotification.notification.*;

public class CheckEmail {
	static boolean testMail(String email, String pass) {
		try{
			final Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			Session mailSession = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, pass);
				}
			});
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(email));
			message.setSubject("");
			message.setText("");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("SanekGreed@gmail.com"));
			Transport.send(message);
		
			AlertDialog.notific("Email checked. You can send from this email", Notifications.SUCCESS);
			return true;
		} catch (MessagingException e){
			AlertDialog.notific("Email checked. You can't send from this email", Notifications.ERROR);
			return false;
		}
	}
}
