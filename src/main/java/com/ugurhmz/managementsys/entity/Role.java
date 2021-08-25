package com.ugurhmz.managementsys.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="roles")
public class Role {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = true, length= 50, unique= true  )
	private String name;
	
	@Column(length= 200, nullable=true)
	private String description;
	
	
	// CONSTRUCTORS
	
	public Role() {
		
	}
	
	public Role(Integer id) {
		this.id = id;
	}
	
	
	public Role(String name) {
		this.name = name;
		
	}
	
	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	// GETTER & SETTER
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	// toString
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	
	//hashCode & equals
	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	
	
	
	
	
}
