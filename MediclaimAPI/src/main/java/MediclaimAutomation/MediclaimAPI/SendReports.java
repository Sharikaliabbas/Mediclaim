package MediclaimAutomation.MediclaimAPI;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class SendReports {
 
	public static void sendReportToDesiredPeopleAcme(){
		
		String[] to={"sharik.aliabbas@perfios.com","deepa.k@perfios.com","roopa.m@perfios.com","rahul.r@perfios.com","sarat.b@perfios.com","lalit.k@perfios.com","suhas@perfios.com","manjunath.r@perfios.com"};
 
		// Create object of Property file
		Properties props = new Properties();
 
		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");
 
		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");
 
		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
 
		// set the authentication to true
		props.put("mail.smtp.auth", "true");
 
		// set the port of SMTP server
		props.put("mail.smtp.port", "465");
 
		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,
 
				new javax.mail.Authenticator() {
 
					protected PasswordAuthentication getPasswordAuthentication() {
 
					return new PasswordAuthentication("sharik.aliabbas@perfios.com", "sharik_33836");
 
					}
 
				});
 
		try {
 
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
 
			// Set the from address
			message.setFrom(new InternetAddress("sharik.aliabbas@perfios.com"));
 
			// Set the recipient address
			
			for(int i=0;i<to.length;i++){
				
				
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
				
			}
			
			message.saveChanges();
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("sharik.abbss@gmail.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("kumarishalu312@gmail.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("deepa.k@perfios.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("roopa.m@perfios.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("rahul.r@perfios.com"));
            
            // Add the subject link
			message.setSubject("Insurance Automation Report for ACME Organisation in redhead server");
 
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
 
			// Set the body of email
			messageBodyPart1.setText("Please find the attached HTML report of Insurance Application for the latest execution of the Regression testcases for ACME Organisation. Please download the file and open it in any browser to view the file properly."+"\n"+"\n"+"Thanks and Regards"+"\n"+"Sharik");
 
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
 
			// Mention the file which you want to send
			String filename = System.getProperty("user.dir")+"//reports//index.html";
 
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
 
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
 
			// set the file
			messageBodyPart2.setFileName(filename);
 
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
 
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
 
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
 
			// set the content
			message.setContent(multipart);
 
			// finally send the email
			Transport.send(message, message.getAllRecipients());
 
			System.out.println("=====Email Sent=====");
 
		} catch (MessagingException e) {
 
			throw new RuntimeException(e);
 
		}
 
	}
	
public static void sendReportToDesiredPeopleMA(){
		
		String[] to={"sharik.aliabbas@perfios.com","deepa.k@perfios.com","roopa.m@perfios.com","rahul.r@perfios.com","sarat.b@perfios.com","lalit.k@perfios.com","suhas@perfios.com","manjunath.r@perfios.com"};
 
		// Create object of Property file
		Properties props = new Properties();
 
		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");
 
		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");
 
		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
 
		// set the authentication to true
		props.put("mail.smtp.auth", "true");
 
		// set the port of SMTP server
		props.put("mail.smtp.port", "465");
 
		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,
 
				new javax.mail.Authenticator() {
 
					protected PasswordAuthentication getPasswordAuthentication() {
 
					return new PasswordAuthentication("sharik.aliabbas@perfios.com", "sharik_33836");
 
					}
 
				});
 
		try {
 
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
 
			// Set the from address
			message.setFrom(new InternetAddress("sharik.aliabbas@perfios.com"));
 
			// Set the recipient address
			
			for(int i=0;i<to.length;i++){
				
				
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
				
			}
			
			message.saveChanges();
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("sharik.abbss@gmail.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("kumarishalu312@gmail.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("deepa.k@perfios.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("roopa.m@perfios.com"));
			
		//	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("rahul.r@perfios.com"));
            
            // Add the subject link
			message.setSubject("Insurance Automation Report for MEDIASSIST in redhead server");
 
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
 
			// Set the body of email
			messageBodyPart1.setText("Please find the attached HTML report of Insurance Application for the latest execution of the Regression testcases for MEDIASSIST Organisation. Please download the file and open it in any browser to view the file properly."+"\n"+"\n"+"Thanks and Regards"+"\n"+"Sharik");
 
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
 
			// Mention the file which you want to send
			String filename = System.getProperty("user.dir")+"//reports//index.html";
 
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
 
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
 
			// set the file
			messageBodyPart2.setFileName(filename);
 
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
 
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
 
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
 
			// set the content
			message.setContent(multipart);
 
			// finally send the email
			Transport.send(message, message.getAllRecipients());
 
			System.out.println("=====Email Sent=====");
 
		} catch (MessagingException e) {
 
			throw new RuntimeException(e);
 
		}
 
	}
 
}