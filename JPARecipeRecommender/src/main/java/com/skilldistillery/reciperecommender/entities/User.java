package com.skilldistillery.reciperecommender.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private boolean enabled;

	private String role;

	
	//transient means that is doesnt user persistence to access the databases, it is it own field
	
	@Transient
	private List<Ingredient> cart;

	public void addToCart(Ingredient ingredient) {
		cart.add(ingredient);
	}

	public void removeFromCart(Ingredient ingredient) {
		cart.remove(ingredient);
	}

	public User() {
	}

	@ManyToMany
	@JoinTable(name = "user_ingredient", joinColumns = @JoinColumn(name = "ingredient_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<Ingredient> ingredients;

	public void addIngredient(Ingredient ingredient) {
		if (ingredients == null) {
			ingredients = new ArrayList<>();
		}
		if (!ingredients.contains(ingredient)) {
			ingredients.add(ingredient);
			ingredient.addUser(this);
		}
	}

	public void removeIngredient(Ingredient ingredient) {
		if (ingredients != null && ingredients.contains(ingredient)) {
			ingredients.remove(ingredient);
			ingredient.removeUser(this);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, id, ingredients, password, role, username);
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
		return enabled == other.enabled && id == other.id && Objects.equals(ingredients, other.ingredients)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", role=" + role + ", ingredients=" + ingredients.size() + "]";
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Ingredient> getCart() {
		return cart;
	}

	public void setCart(List<Ingredient> cart) {
		this.cart = cart;
	}
}
