package com.SmartContact.user;

import java.util.ArrayList;
import java.util.List;

//import com.example.demo.User.User;
import com.SmartContact.user.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="CONTACT")
public class Contact {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
  private int cid;
  private String name;
  private String nickname;
  private String phoneno;
  @Column(unique=true)
  private String email;
  private String work;
  private String imageurl;
  @Column(length=5000)
  private String description;
  @ManyToOne
  @JsonIgnore
  private User user;

  public Contact() {
	super();
	// TODO Auto-generated constructor stub
      }
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getPhoneno() {
	return phoneno;
}
public void setPhoneno(String phoneno) {
	this.phoneno = phoneno;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getWork() {
	return work;
}
public void setWork(String work) {
	this.work = work;
}
public String getImageurl() {
	return imageurl;
}
public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
//@Override
/*public String toString() {
	return "Contact [cid=" + cid + ", name=" + name + ", nickname=" + nickname + ", phoneno=" + phoneno + ", email="
			+ email + ", work=" + work + ", imageurl=" + imageurl + ", description=" + description + ", user=" + user
			+ "]";
}*/
public boolean equals(Object obj) {
	return this.cid==((Contact)obj).getCid();
}

  
  
  
}
