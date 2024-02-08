package com.skilldistillery.reciperecommender.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skilldistillery.reciperecommender.data.IngredientDAO;
import com.skilldistillery.reciperecommender.data.RecipeDAO;
import com.skilldistillery.reciperecommender.data.UserDAO;
import com.skilldistillery.reciperecommender.entities.Comment;
import com.skilldistillery.reciperecommender.entities.Ingredient;
import com.skilldistillery.reciperecommender.entities.Recipe;
import com.skilldistillery.reciperecommender.entities.RecipeImpression;
import com.skilldistillery.reciperecommender.entities.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeController {

	@Autowired
	private RecipeDAO recipeDAO;
	@Autowired
	private IngredientDAO ingredientDAO;
	
	@Autowired
	private UserDAO userDAO;
    private List<Ingredient> temporaryList = new ArrayList<>(); // Temporary list to hold added ingredients

	
	@GetMapping(path = "findall.do")
	public String findAll(Model model) {
		List<Recipe> recipes = recipeDAO.allRecipe();
		model.addAttribute("recipes", recipes);
		return "list";
	}

	@RequestMapping(path = "showRecipe.do", params = ("recipeId"))
	public String showRecipe(@RequestParam("recipeId") int recipeId, Model model, HttpSession session) {
		Recipe recipe = recipeDAO.findById(recipeId); // Adjust this method according to your DAO implementation
		model.addAttribute("recipe", recipe);
		return "recipeInfo";
	}

	@RequestMapping(path = "generateRecipes.do")
	public String generateRecipes(HttpSession session, Model model) {
		try {
			User user = (User) session.getAttribute("user");
			List<Recipe> topRecipes = recipeDAO.generateRecipes(user);
			model.addAttribute("topRecipes", topRecipes);
			return "displayRecipe";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	@RequestMapping(path = "favoriteRecipe.do", params = ("recipeId"))
	public String favoriteRecipe(@RequestParam("recipeId") int recipeId, @ModelAttribute User user, HttpSession session,
			Model model) {
		try {
			user = (User) session.getAttribute("user");
			Recipe recipe = recipeDAO.findById(recipeId);
			recipe.setCreateDate(LocalDateTime.now());
			RecipeImpression recipeImpression = recipeDAO.mapThisRecipeToUser(user, recipe);
			Recipe displayFavoriteRecipe = recipeDAO.favoriteThisRecipe(user, recipe);
			model.addAttribute("recipeImpression", recipeImpression);
			model.addAttribute("favoritedRecipe", displayFavoriteRecipe);
			return "redirect:showRecipe.do?recipeId=" + recipeId;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(path = "unfavoriteRecipe.do", params = ("recipeId"))
	public String unfavoriteRecipe(@RequestParam("recipeId") int recipeId, User user, HttpSession session,
			Model model) {
		try {
			user = (User) session.getAttribute("user");
			Recipe recipe = recipeDAO.findById(recipeId);
			recipe.setCreateDate(null);
			recipeDAO.unmapThisRecipeToUser(user, recipe);
			recipeDAO.unfavoriteThisRecipe(user, recipe);
			return "redirect:showRecipe.do?recipeId=" + recipeId;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(path = "addRecipeRedirect.do")
	public String addRecipeRedirect() {
		return "addRecipe";
	}
	
	@RequestMapping(path = "addIngredientToRecipe.do")
	public String addIngredientToRecipe(@RequestParam("id") int ingredientId, Model model) {
		Ingredient ingredient = ingredientDAO.findById(ingredientId);
		temporaryList.add(ingredient);
		model.addAttribute("tempIngredientList", temporaryList);
		return "addRecipe";
	}
	
	@RequestMapping(path = "removeIngredientFromRecipe.do")
	public String removeIngredientFromoRecipe(@RequestParam("id") int ingredientId, Model model) {
		Ingredient ingredient = ingredientDAO.findById(ingredientId);
		temporaryList.remove(ingredient);
		model.addAttribute("tempIngredientList", temporaryList);
		return "addRecipe";
	}

	@RequestMapping(path = "getAllFavorites.do")
	public String findFavorites() {
		return "favorite";
	}

	@RequestMapping(path = "addComment.do")
	public String addCommentToRecipe(@RequestParam("recipeId") int recipeId, @RequestParam("content") String content,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment(user, recipeDAO.findById(recipeId));
		comment.setComment(content);
		recipeDAO.addCommentToRecipe(recipeId, comment);
		session.setAttribute("user", userDAO.findById(user.getId()));
		return "showRecipe";
	}



	@RequestMapping(path = "rerouteToPantry.do")
	public String takeMeToPantry() {
		return "userIngredient";
	}

}
