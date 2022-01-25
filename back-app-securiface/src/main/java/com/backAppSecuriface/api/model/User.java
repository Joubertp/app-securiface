package com.backAppSecuriface.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER_API")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", updatable = false, nullable = false)
    private Long userId;

	@Column(name = "FIRST_NAME", insertable=true, updatable=true, nullable=false)
    private String firstname;
	
	@Column(name = "LAST_NAME", insertable=true, updatable=true, nullable=false)
    private String lastname;
	
	@Column(name = "EMAIL", insertable=true, updatable=true, nullable=false)
    private String email;
	
	@Column(name = "PASSWORD", insertable=true, updatable=true, nullable=false)
    private String password;
	
	@Column(name = "CREATED_AT", insertable=false, updatable=false, nullable=false)
	private LocalDateTime createdAt;
	
	@Column(name = "IS_ACTIVE", insertable=true, updatable = true, nullable=false)
	private Integer isActive;
	
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles= new HashSet<>();

    public User() {
    	super();
    }

    public User(String firstname, String lastname, String email, String password, Integer active) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.isActive = active;
    }
    
	public User(Long id, String firstname, String lastname, String email, String password, Integer active) {
		this.userId = id;
		this.firstname = firstname;
		this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.isActive = active;
	}

	public User(String firstname, String lastname, String email, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.isActive = 0;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
	}

	public String getFirstname() {
		return this.firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getActive() {
		return this.isActive;
	}
	public void setActive(Integer active) {
		this.isActive = active;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email  + ", password=XXXXXXX, active=" + isActive + ", roles="
				+ roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
}
