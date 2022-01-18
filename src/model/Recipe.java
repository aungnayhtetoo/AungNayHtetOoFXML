/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aung Nay
 */
@Entity
@Table(name = "RECIPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r")
    , @NamedQuery(name = "Recipe.findByRecipeId", query = "SELECT r FROM Recipe r WHERE r.recipeId = :recipeId")
    , @NamedQuery(name = "Recipe.findByMealId", query = "SELECT r FROM Recipe r WHERE r.mealId = :mealId")
    , @NamedQuery(name = "Recipe.findByRecipeName", query = "SELECT r FROM Recipe r WHERE r.recipeName = :recipeName")
    , @NamedQuery(name = "Recipe.findByCalories", query = "SELECT r FROM Recipe r WHERE r.calories = :calories")
    , @NamedQuery(name = "Recipe.findByCookingDuration", query = "SELECT r FROM Recipe r WHERE r.cookingDuration = :cookingDuration")})
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RECIPE_ID")
    private Integer recipeId;
    @Basic(optional = false)
    @Column(name = "MEAL_ID")
    private int mealId;
    @Column(name = "RECIPE_NAME")
    private String recipeName;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "CALORIES")
    private Double calories;
    @Basic(optional = false)
    @Column(name = "COOKING_DURATION")
    private double cookingDuration;

    public Recipe() {
    }

//    public Recipe(Integer recipeId) {
//        this.recipeId = recipeId;
//    }
    
    //recipe ID is auto generated
    public Recipe(int mealId, Double calories, double cookingDuration) {
        this.mealId = mealId;
        this.calories = calories;
        this.cookingDuration = cookingDuration;
    }

    public Recipe(Integer recipeId, int mealId, Double calories, double cookingDuration) {
        this.recipeId = recipeId;
        this.mealId = mealId;
        this.calories = calories;
        this.cookingDuration = cookingDuration;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public double getCookingDuration() {
        return cookingDuration;
    }

    public void setCookingDuration(double cookingDuration) {
        this.cookingDuration = cookingDuration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recipeId != null ? recipeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.recipeId == null && other.recipeId != null) || (this.recipeId != null && !this.recipeId.equals(other.recipeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipe Id: " + getRecipeId() + " Meal Id: " + getMealId() + "\nName: " + getRecipeName();
    }
    
}
