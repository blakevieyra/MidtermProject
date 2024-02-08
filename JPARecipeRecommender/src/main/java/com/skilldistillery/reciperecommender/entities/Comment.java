package com.skilldistillery.reciperecommender.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_comment")
public class Comment {
	
	@Column(name = "comment")
	private String comment;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Comment() {
		super();
	}
	
	public Comment(User user, Recipe recipe) {
		super();
		this.user = user;
		this.recipe = recipe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(recipe, other.recipe);
	}

	@Override
	public String toString() {
		return "Comment [comment=" + comment + ", recipe=" + recipe + ", user=" + user + "]";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
