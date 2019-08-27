package com.astro.webautomation.com.astro.webautomation;

import com.astro.webautomation.*;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
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



public class email {

	static String strLine = null;
	static String log;
	static String productlisting;
	static String dateresponse;
	static String campaign;
	static String chatbottestresult;
	static String screencapturepageone;
	static String screencapturepagetwo;
	static String conversation;
	static String workingdir = System.getProperty("user.dir");

	private static MimeBodyPart messageBodyPart;

	public static void main(String [] args) throws IOException { 

		String logcreateddate;
		logcreateddate = ecommerceTest.requiredDate;
		log = workingdir + "/test-log/" + logcreateddate + "_webUI.log";
		productlisting = workingdir + "/test-log/" + "productlisting"+logcreateddate;
		screencapturepageone = workingdir + "/screencapture/" + "Lazada_" + logcreateddate +".png";
		screencapturepagetwo = workingdir + "/screencapture/" + "Amazon_" + logcreateddate +".png";

		//Update gmail username and password
		//Provide email to to send email out
		final String username = "";
		final String password = "";
		String emailto = "";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		@SuppressWarnings("deprecation")
		String logdetails = Files.toString(new File(log), Charsets.UTF_8);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailto));
			message.setSubject(ecommerceTest.requiredDate + ": AUTOMATION ALERT - E-Commerce Web Comparision");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(logdetails);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			//Attachment 1 Log
			messageBodyPart = new MimeBodyPart();
			String filename = ecommerceTest.requiredDate + "_webUI.log";
			DataSource source = new FileDataSource(log);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			
			//Attachment 2 Product Listing
			messageBodyPart = new MimeBodyPart();
			String filenameproductlisting = ecommerceTest.requiredDate + "Product_Listing.log";
			DataSource sourceproductlisting = new FileDataSource(productlisting);
			messageBodyPart.setDataHandler(new DataHandler(sourceproductlisting));
			messageBodyPart.setFileName(filenameproductlisting);
			multipart.addBodyPart(messageBodyPart);

			//Attachment 3 Screen capture Page One
			messageBodyPart = new MimeBodyPart();
			String filenameimagepageone = ecommerceTest.requiredDate + "Lazada.png";
			DataSource sourceimagepageone = new FileDataSource(screencapturepageone);
			messageBodyPart.setDataHandler(new DataHandler(sourceimagepageone));
			messageBodyPart.setFileName(filenameimagepageone);
			multipart.addBodyPart(messageBodyPart);
			
			//Attachment 4 Screen capture Page One
			messageBodyPart = new MimeBodyPart();
			String filenameimagepagetwo = ecommerceTest.requiredDate + "Lazada.png";
			DataSource sourceimagepagetwo = new FileDataSource(screencapturepagetwo);
			messageBodyPart.setDataHandler(new DataHandler(sourceimagepagetwo));
			messageBodyPart.setFileName(filenameimagepagetwo);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("eMail Sent....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}