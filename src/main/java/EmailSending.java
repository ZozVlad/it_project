package main.java;

import java.io.*;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.models.Prospect;

public class EmailSending {
	static void sendMessage(String login, String pass, Prospect recipient, String subject, String text) throws MessagingException {
		String finalText = text;
		finalText = finalText.replaceAll("%EMAIL%", recipient.getEmail());
		finalText = finalText.replaceAll("%FIRSTNAME%", recipient.getFirstName());
		finalText = finalText.replaceAll("%LASTNAME%", recipient.getLastName());
		finalText = finalText.replaceAll("%FULLNAME%", recipient.getFullName());
		finalText = finalText.replaceAll("%COMPANY%", recipient.getCompany());
		finalText = finalText.replaceAll("%PHONE%", recipient.getPhone());
		finalText = finalText.replaceAll("%ADDRESS%", recipient.getAddress());
		finalText = finalText.replaceAll("%CITY%", recipient.getCity());
		finalText = finalText.replaceAll("%SNIPPET1%", recipient.getSnippet1());
		finalText = finalText.replaceAll("%SNIPPET2%", recipient.getSnippet2());
		finalText = finalText.replaceAll("%SNIPPET3%", recipient.getSnippet3());
		finalText = finalText.replaceAll("%SNIPPET4%", recipient.getSnippet4());
		finalText = finalText.replaceAll("%SNIPPET5%", recipient.getSnippet5());
		
		final Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session mailSession = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, pass);
			}
		});

		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(login));

		message.setSubject(subject);
		message.setDataHandler(new DataHandler(new HTMLDataSource(finalText)));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));

		Transport.send(message);
		return;
	}

	static class HTMLDataSource implements DataSource {

		private String html;

		public HTMLDataSource(String htmlString) {
			html = htmlString;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			if (html == null) throw new IOException("html message is null!");
			return new ByteArrayInputStream(html.getBytes());
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			throw new IOException("This DataHandler cannot write HTML");
		}

		@Override
		public String getContentType() {
			return "text/html";
		}

		@Override
		public String getName() {
			return "HTMLDataSource";
		}
	}
}


