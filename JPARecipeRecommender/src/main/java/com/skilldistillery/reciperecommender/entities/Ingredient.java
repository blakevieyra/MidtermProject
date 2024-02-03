package com.skilldistillery.reciperecommender.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingredient {
	
	public Ingredient() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@ManyToMany(mappedBy = "ingredients")
	private List<User> users;
	
	public void addUser(User user) {
		if (user == null) {
			users = new ArrayList<>();
		}
		if (!users.contains(user)) {
			user.addIngredient(this);
		}
	}

	public void removeUser(User user) {
		if (users != null && users.contains(user)) {
			users.remove(user);
			user.removeIngredient(this);
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", users=" + users.size() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(users, other.users);
	}
}
