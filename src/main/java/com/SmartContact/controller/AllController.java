package com.SmartContact.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.demo.Message.Helper;
//import com.example.demo.User.User;
//import com.example.demo.dao.UserRepository;
//import com.SmartContact.message;
import com.SmartContact.message.Helper;
import com.SmartContact.user.User;
import com.SmartContact.dao.UserRepository;
import java.lang.String;


//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class AllController {
	@Autowired
	private BCryptPasswordEncoder passWordEncoder;
	@Autowired
	private UserRepository userrepo;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title","home");
		return"home";
	}
	
 @GetMapping("/about")
 public String about(Model model) {
	 model.addAttribute("title","about");
	return "about";
  }
   
   @GetMapping("/signUp")
    
   public String signUp(Model model) {
	 model.addAttribute("user", new User());
	 model.addAttribute("title","signUp");
	return "signUp";
      }
      @PostMapping("/do_register")
    public String process(@Valid @ModelAttribute("user") User user,BindingResult result1,
 @RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model, HttpSession session) {
    	  
    	  try {
    	  if(!agreement) {
    		System.out.println("You are not singed in");
     throw new Exception("you have not agreed term and condition");
    	  }
      
    	  if(result1.hasErrors()) {
    		 return "signUp";
    	  }
    	  
    	  user.setRole("ROLE_USER");
    	  user.setEnabled(true);
    	  user.setImageUrl("default.png");
    	  user.setPassword(passWordEncoder.encode(user.getPassword()));
    	  //savinf data in data base
    User result=this.userrepo.save(user);
    	  model.addAttribute("user",result);
    	  System.out.println("agreement="+agreement);
    	  System.out.println("key:="+user);
    	  model.addAttribute("user",new User());
    	 // System.out.println(session);
    	  session.setAttribute("message", new Helper("Successfully registered !!","alert-success"));
    	  return "signUp";
    	  }
    	  catch(Exception e) {
    		  e.printStackTrace();
    		  model.addAttribute("user",user);
    		 session.setAttribute("message", new Helper("something went wrong!!"+e.getMessage(),"alert-danger"));
    		 return "signUp";
    	  }
    	}
      @GetMapping("/signin")
      public java.lang.String login(Model model) {
    	  model.addAttribute("title","signIn");
    	  return "login";
      }
     /* @PostMapping("/create_order")
      @ResponseBody
      public String create_order() {
    	  System.out.println("order is created");
    	  return "done";
      }*/
}
