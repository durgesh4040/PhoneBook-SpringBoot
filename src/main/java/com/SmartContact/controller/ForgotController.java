
package com.SmartContact.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.demo.Message.Helper;
//import com.example.demo.Service.EmailService;
//import com.example.demo.User.User;
//import com.example.demo.dao.UserRepository;
import com.SmartContact.message.Helper;
import com.SmartContact.service.EmailService;
import com.SmartContact.user.User;
import com.SmartContact.dao.UserRepository;



import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
  // private static final String required = null;
      
	
	
    //  @Autowired
	//private EmailService emailService;
	
    
    Random random=new Random(100000);
	
    @Autowired (required=true)
	private EmailService emailService;
    
    
    @Autowired
    private UserRepository userrepository;
    
    @Autowired 
    private BCryptPasswordEncoder bcrypt;
	
	@GetMapping("/forgot")
	public String forgot() {
return "forgot";
	}
	@PostMapping("/send-otp")
	public String process(@RequestParam("email") String email,HttpSession session) {
		System.out.println(email);
		int Otp=random.nextInt(999999);
		System.out.println(Otp);
		//subject 
	String subject="Otp from PhoneBOOK";
	//String Otp=Integer.toString(otp);
	String message=""
			+"<div style='border:1px solid #e2e2e2; padding:20px;'>"
			+"<h1>"
			+"OTP is"
			+"<b>"+Otp
			+"</b>"
			+"</h1>"
			+"</div>";
	//sending email service
	boolean res=this.emailService.sendemail(email, subject, message);
		
	if(res==true) {
	System.out.println("done");	
	//return "change-password";
	session.setAttribute("myotp",Otp);
	session.setAttribute("email",email);
	return "verify";
	}else {
		System.out.println("Something went wrong false");
		session.setAttribute("message",new Helper("check your email id","danger"));
		return "forgot";
	}
	//return "verify";
	}
	//
	@PostMapping("/veryfy-otp")
	public String verifyotp(@RequestParam("otp") int otp,HttpSession session) {
		
	   int  myotp=(Integer) session.getAttribute("myotp");
	    String emailvar=(String) session.getAttribute("email");
	    System.out.println(myotp);
	    System.out.println(otp);
	    if(myotp==otp) {
	        User user=this.userrepository.getUserByUserName(emailvar);
	    	
	        if(user==null) {
	        	session.setAttribute("message",new Helper("You have entered wrong user","danger"));
	    		return "forgot";
	        }else {
	        
	        return "change-password";
	        }
	    }
	    
	    else {
	    session.setAttribute("message",new Helper("You have entered wrong otp","danger"));
		return "forgot";
	    }
		}
	
	//new Password
	@PostMapping("/new-password")
	public String newPassword(@RequestParam("password")String password,HttpSession session) {
		
	String emailvar=(String) session.getAttribute("email");
	//System.out.println(emailvar);
		User user=this.userrepository.getUserByUserName(emailvar);
		System.out.println(user);
		user.setPassword(this.bcrypt.encode(password));
		this.userrepository.save(user);
		return "redirect:/signin?change=password changed successfully......";
	}
	
	
}