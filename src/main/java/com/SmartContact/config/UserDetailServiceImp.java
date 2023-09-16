package com.SmartContact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//import com.example.demo.User.User;
//import com.example.demo.dao.UserRepository;
import com.SmartContact.user.User;
import com.SmartContact.dao.UserRepository;

public class UserDetailServiceImp implements UserDetailsService {
     @Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// fetching user from userDatabase
		User user=userRepository.getUserByUserName(username);
		if(user==null) {
throw new UsernameNotFoundException("Could not found!!");
		}
	CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
