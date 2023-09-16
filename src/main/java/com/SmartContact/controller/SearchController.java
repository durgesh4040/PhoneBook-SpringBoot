package com.SmartContact.controller;

import java.security.Principal;
import java.util.List;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//import com.example.demo.User.Contact;
//import com.example.demo.User.User;
//import com.example.demo.dao.ContactRepository;
//import com.example.demo.dao.UserRepository;
import com.SmartContact.user.Contact;
import com.SmartContact.user.User;
import com.SmartContact.dao.ContactRepository;
import com.SmartContact.dao.UserRepository;

@RestController
public class SearchController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?>search(@PathVariable("query")String query,Principal principal ){
		System.out.println("query");
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
	List<Contact> contacts=this.contactRepository.findByNameContainingAndUser(query, user);
	return ResponseEntity.ok(contacts);
	}
}
