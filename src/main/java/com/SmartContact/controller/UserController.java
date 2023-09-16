package com.SmartContact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//import com.example.demo.Message.Helper;
//import com.example.demo.User.Contact;
//import com.example.demo.User.User;
//import com.example.demo.dao.ContactRepository;
//import com.example.demo.dao.UserRepository;
//import jakarta.servlet.http.HttpSession;
//import com.razorpay.*;
import com.SmartContact.message.Helper;
import com.SmartContact.user.Contact;
import com.SmartContact.user.User;
import com.SmartContact.dao.UserRepository;
import com.SmartContact.dao.ContactRepository;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	//common user 
	 @Autowired
	  private BCryptPasswordEncoder bcryptpasswordencoder;
	@ModelAttribute
	public void addCommondata(Model m,Principal principal) {
		String name=principal.getName();
		System.out.println("UserName="+name);
		User user=userRepository.getUserByUserName(name);
		System.out.println("User="+user);
		m.addAttribute("user",user);
	}
	@GetMapping("/index")
	public String index(Model model,Principal principle) {
		/*String name=principle.getName();
		System.out.println("UserName="+name);
		User user=userRepository.getUserByUserName(name);
		System.out.println("User="+user);
		model.addAttribute("user",user);*/
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	@GetMapping("/addcontact")
	public String addcontact(Model model) {
		model.addAttribute("title","Add contact");
		model.addAttribute("contact",new Contact());
		return "normal/add-contact-form";
	}
	//save contact data in database
	@PostMapping("/process-contact")
	public String saveContact(@ModelAttribute Contact contact,
			@RequestParam("profileimage")MultipartFile file,
			Principal principal,HttpSession session) {
try {
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
	
	if(file.isEmpty()) {
			System.out.println("file is empty");
		contact.setImageurl("contact.png");
		}else {
			// upload the file in folder upload the name and contact
		contact.setImageurl(file.getOriginalFilename());
	File saveFile=	new ClassPathResource("static/images").getFile();
		Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("image is uploaded");
		// message success
		//session.setAttribute("message",new Helper("your contact is add !! More Add","success"));

		
		}
		contact.setUser(user);
		user.getContacts().add(contact);
	    this.userRepository.save(user);
		System.out.println("Data "+contact);
		System.out.println("Added to data user");
		session.setAttribute("message",new Helper("your contact is add !! More Add","success"));
}catch (Exception e) {
	System.out.println("Error:"+e.getMessage());
	//show error in database
	session.setAttribute("message",new Helper("No-contact is added","danger"));
}
		return "normal/add-contact-form";	
		}
	
	
	// show contacts handler 
  @GetMapping("/show-contacts/{page}")
  public String view(@PathVariable("page") Integer page,Model m,Principal principal) {
	  m.addAttribute("title","show user contacts");
	  String userName=principal.getName();
	  Pageable pageable=PageRequest.of(page, 5);
	  User user =this.userRepository.getUserByUserName(userName);
	  Page<Contact>contacts=this.contactRepository.findConatactByUser(user.getId(),pageable);
	  //User user =this.userRepository.getUserByUserName(userName);
	  //using list
	  //List<Contact>contacts=this.contactRepository.findConatactsByUser(user.getId());
	  m.addAttribute("contacts",contacts);
	  m.addAttribute("currentPage",page);
	  m.addAttribute("totalPages",contacts.getTotalPages());
	  return "normal/show-contacts";
  } 
  
  //show show contact detail 
   @RequestMapping("/{cId}/contact")
   public String contactDetail(@PathVariable("cId") Integer cId,Model model,Principal principal) {
Optional<Contact> contactOptional	=   this.contactRepository.findById(cId);
Contact contact=contactOptional.get();
String username=principal.getName();
User user=this.userRepository.getUserByUserName(username);
if(user.getId()==contact.getUser().getId())
model.addAttribute("contact",contact);
	   return "normal/ContactDetail";
   }
   
  //Delete the contact details
   @GetMapping("/delete/{cid}")
 public String  delete(@PathVariable("cid") Integer cid,Principal principal,HttpSession session) {
	   String username=principal.getName();
	   User user=this.userRepository.getUserByUserName(username);
	  Optional<Contact> optionalcontact=this.contactRepository.findById(cid);
	 Contact contact=optionalcontact.get();
	 
	 if(user.getId()==contact.getUser().getId()) {
     user.getContacts().remove(contact);
     this.userRepository.save(user);
	 System.out.println("Deleted");
	 session.setAttribute("message",new Helper("Your contacat is deleted","success"));
	 }
	  return"redirect:/user/show-contacts/0";
 }
   // Opent the update contact
   @RequestMapping("/update-contact/{cid}")
   public String update(@PathVariable("cid")Integer cid,Model m) {
	   m.addAttribute("title","Update-Contact");
	   Contact contact =this.contactRepository.findById(cid).get();
	   m.addAttribute("contact",contact);
	   
	   return "/normal/update-form";
   }
   //Update  the process-form
   @RequestMapping(value="/process-update" ,method=RequestMethod.POST)
   public String  processUpdate(@ModelAttribute Contact contact,@RequestParam("profileimage") MultipartFile file,HttpSession session,Principal principal) {
	  System.out.println("contact="+contact.getName());
	  try {
		  Contact oldcontact=this.contactRepository.findById(contact.getCid()).get();
		  if(!file.isEmpty()) {
			  //delete old photo

			 File deleteFile=new ClassPathResource("static/images").getFile();
			  File file1=new File(deleteFile,oldcontact.getImageurl());
			  file1.delete();
			 
			 //update file 
		  File saveFile=new ClassPathResource("/static/images").getFile();
	      Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		  Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
		  contact.setImageurl(file.getOriginalFilename());
		  System.out.println("file is updated");
		  }
		  else {
			 contact.setImageurl(oldcontact.getImageurl()) ;
		  }
		  String username=principal.getName();
		  User user=this.userRepository.getUserByUserName(username);
	      contact.setUser(user);
	      this.contactRepository.save(contact);
	  session.setAttribute("message",new Helper("Your contact is updated","success"));
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return "redirect:/user/"+contact.getCid()+"/contact";
   }
   //Profile page 
   @GetMapping("/profile")
   public String profileView(Model m) {
	   m.addAttribute("title","profile");
	   return "normal/profile";
   }
   @GetMapping("/setting")
   public String setting() {
	   
	   return "normal/setting";
   }
   
  @GetMapping("/change-password")
   public String changePassword(@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword,Principal principal,HttpSession session) {

	  
	  System.out.println(oldpassword+"="+newpassword);
	  String name=principal.getName();
	  User user=this.userRepository.getUserByUserName(name);
	  System.out.println(user.getPassword());
	  if(this.bcryptpasswordencoder.matches(oldpassword, user.getPassword())) {
		 user.setPassword(this.bcryptpasswordencoder.encode(newpassword));
		 this.userRepository.save(user);
		 session.setAttribute("message", new Helper("Sucess password is updated","success"));
	  }
	  else {
		  session.setAttribute("message",new Helper("Error password is wrong !!","danger"));
	  }
	  return "redirect:/user/index"; 
   }
  
//  @PostMapping("/create_order")
//  @ResponseBody
//  public String create_order(@RequestBody Map<String,Object> data) {
//	  System.out.println("order is created");
//	  int amt=Integer.parseInt(data.get("amount").toString());
//	  try {
//		var client =new RazorpayClient("rzp_test_ezwaiZLv8pDC7f","0s7SIHvuW29tmnghNdzHYXkX");
//	    JSONObject ob=new JSONObject();
//	    ob.put("amount", amt*100);
//	    ob.put("currency", "INR");
//	    ob.put("receipt", "txn_235425");
//	  Order order=  client.orders.create(ob);
//	  System.out.println(order);
//	  
//	  } catch (RazorpayException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	  return "done";
//  }
  
}
