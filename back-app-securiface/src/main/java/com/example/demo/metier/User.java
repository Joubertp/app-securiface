package com.example.demo.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Getter @Setter @NoArgsConstructor @ToString(exclude = {"roles", "password"}) @AllArgsConstructor
public class User {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name="ID", updatable = false, nullable = false)
	  private long id;
	  @Column(name="FIRSTNAME", length= 30)
	  private String firstname;
	  @Column(name="LASTNAME", length= 30)
	  private String lastname;
	  @Column(name="USERNAME", length= 30)
	  private String username;
	  @Column(name="EMAIL", length= 30, nullable=false)
	  private String email;
	  @Column(name="PASSWORD", length= 120, nullable=false)
	  private String password;
	  @Column(columnDefinition="TEXT", nullable = false)
	  private String description;
	  private boolean enabled;
	  
	 //**************************************
	  @ManyToMany(cascade = CascadeType.DETACH)
		@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
		private Set<Role> roles= new HashSet<>();
	
	//**************************************
	public User(long id, String username, String password, String description) {
		this(id,"","",username,"",password,description,true,new HashSet<>());
	}
	
}