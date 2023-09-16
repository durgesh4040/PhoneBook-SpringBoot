package com.SmartContact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.example.demo.User.Contact;
//import com.example.demo.User.User;
import com.SmartContact.user.*;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
/*@Query("From  Contact as c where c.user.id=:userid") //print all list
public List<Contact>findConatactsByUser(@Param("userid") int userid);*/
	//print page by page
	@Query("From Contact as c where c.user.id=:userid")
	public Page<Contact>findConatactByUser(@Param("userid")int userid,Pageable pageable);

//public List<Contact>findByNameContainingandUser(String name,User user);
//search
public List<Contact> findByNameContainingAndUser(String query, User user);

}
