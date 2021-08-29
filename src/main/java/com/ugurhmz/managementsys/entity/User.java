package com.ugurhmz.managementsys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;




@Entity
@Table(name="users")
public class User {

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name", nullable=false, length=80)
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=120)
	private String lastName;
	
	@Column(nullable=false, length=200, unique=true)
	private String email;
	
	@Column(nullable=false, length=80)
	private String password;
	
	@Column(length=100)
	private String photos;
	
	
	private boolean enabled;
	
	
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
				name = "users_roles",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();


	
	
	// CONSTRUCTORS 
	public User() {
		
	}
	
	
	public User(String firstName, String lastName, String email, String password) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}


	// GETTER & SETTER
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
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


	public String getPhotos() {
		return photos;
	}


	public void setPhotos(String photos) {
		this.photos = photos;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	// toString
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", photos=" + photos + ", enabled=" + enabled + ", roles=" + roles + "]";
	}

	
	// add Role
	public void addRole(Role role) {
		this.roles.add(role);
		
	}
	
	
	
	// User photos path 
	@Transient
	public String getPhotosImagePath() {
		
		if ( this.id == null || this.photos == null) {
			return "/images/default-user.png";
		}
		
		return "/user-profile-photos/" + this.id + "/" + this.photos;
	}
	
	/* Not 
	 * 
	 * *Veritabanında belirlediğimiz kolonun oluşturulmasını istemiyorsak 
	 * @Transient anotasyonunu kullanıyoruz.
	 * Tablo içerisine kolon olarak eklenmesini istiyorsak Basic anotasyonu ile bunu belirtebiliriz*/
	
	  
	
	
	
	
}
