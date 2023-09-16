package com.SmartContact.service;

import java.util.Properties;

//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;	


@Service
public class EmailService{
   public boolean sendemail (String to,String subject,String message) {
	   
	   String from="infinity9335@gmail.com";
	   String host="smtp.gmail.com";
	   Properties properties=System.getProperties();
	   properties.put("mail.smtp.host", host);
	   properties.put("mail.smtp.port","465");
	   properties.put("mail.smtp.auth", "true");
	   properties.put("mail.smtp.ssl.enable","true");
	   //lamda session function
	   Session session=Session.getInstance(properties,new Authenticator() {
		  protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication("infinity9335@gmail.com","tibzvipvclcfdvtm");
		  }
	   });
	   session.setDebug(true);
	   MimeMessage m=new MimeMessage(session);
	   try {
		   //set from 
		   m.setFrom(from);
		   //set to
          m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
          //set message
          m.setSubject(subject);
          //set message
          //m.setText(message);
          m.setContent(message,"text/html");
          //send data
          Transport.send(m);
          System.out.println("succcessful send");
          return true;
	   }catch (Exception e) {
		   e.printStackTrace();
		   return false;
	   }
	   
	  
   }


}
