/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Recipe;

/**
 *
 * @author Aung Nay
 */
public class FXMLDocumentController implements Initializable {
    // cmd for connecting to database java -jar derbyrun.jar server start
    // disconnecting java -jar derbyrun.jar server shutdown
    // this is a Database manager
    
    /*THIS IS THE PREVIOUS ASSIGNMENT CONTROLLER NOT PART OF THE FINAL IMPLEMENTATION WAS GOING TO CHANGE THIS
    INTO AM ADMIN PAGE BUT WE DIDN"T HAVE TIME*/
    
    private EntityManager manager;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createRecipeBtn;

    @FXML
    private Button updateRecipeBtn;

    @FXML
    private Button deleteRecipeBtn;

    @FXML
    private Button readRecipeBtn;

    @FXML
    private Button readRecipeCustomBtn1;

    @FXML
    private Button readRecipeCustomBtn2;

    @FXML
    private Button button;

    @FXML
    private Label label;

    @FXML
    void createRecipe(ActionEvent event) {
        Scanner scn = new Scanner(System.in);
        Scanner scnDouble = new Scanner(System.in);
        
        //Later be Foreign Key Meal Id from Meal Table 
        System.out.println("Enter Recipe Name: ");
        String mealName = scn.nextLine();
        
        System.out.println("Enter Cooking Duration: ");
        double dura = scnDouble.nextDouble();

        //Big Dec length
        System.out.println("Enter Calories: ");
        double cal = scnDouble.nextDouble();
        
        System.out.println("Enter Meal ID: " );
        int mealId = 2;
        
        Recipe recipe = new Recipe();
        
        recipe.setRecipeId(null);        
        recipe.setMealId(mealId);
        recipe.setCalories(cal);
        recipe.setCookingDuration(dura);
        recipe.setRecipeName(mealName);
        recipe.toString();
        create(recipe);
        
        System.out.println("");
        recipe.toString();
        
    }

    @FXML
    void deleteRecipe(ActionEvent event) {
        Scanner scn = new Scanner(System.in);
        
        //Starts from 10
        System.out.println("Enter Recipe ID: " );
        int repId = scn.nextInt();
        
        Recipe recipe = readById(repId);
        delete(recipe);
        System.out.println("Recipe ID " + repId + "Found -- Deleted");
    }

    @FXML
    void findRecipeLessThanCalories(ActionEvent event) {
        Scanner scn = new Scanner(System.in);
        
        //Starts from 10
        System.out.println("Enter Calories: " );
        double lessCal = scn.nextDouble();
        
        List<Recipe> recipeLessCal = readByCal(lessCal);
        System.out.println("Found " + recipeLessCal.size() + " Results");
        for(Recipe r : recipeLessCal)
        {
            System.out.println(r.toString() + " Calories: " + r.getCalories());
        }
    }

    @FXML
    void findRecipeStringContain(ActionEvent event) {
        Scanner scn = new Scanner(System.in);
        
        //Find all Recipe that includes a String in their name
        System.out.println("Enter Recipe Name: " );
        String nameInc = scn.next();
        
        List<Recipe> recipeInc = readByString(nameInc);
        System.out.println("Found " + recipeInc.size() + " Results");
        for(Recipe r : recipeInc)
        {
            System.out.println(r.toString());
        }
    }

    @FXML
    void readAllRecipe(ActionEvent event) {
        List<Recipe> recipes = readRecipe();
        for(Recipe rp: recipes){
            System.out.println(rp.toString());
        }
    }

    @FXML
    void updateRecipe(ActionEvent event) {
        Scanner scn = new Scanner(System.in);
        Scanner scnDouble = new Scanner(System.in);
        
        //Starts from 10
        
        System.out.println("Updating a Recipe");
        // read user input from command line
        System.out.println("Enter Recipe ID: " );
        int repId = scn.nextInt();
        
        System.out.println("Enter Recipe Name: ");
        String repName = scnDouble.nextLine();
      
        System.out.println("Enter Meal Id: ");
        int mealId = scn.nextInt();
        
        
        
        System.out.println("Enter Recipe Calories: ");
        double calo = scnDouble.nextDouble();
        
        System.out.println("Enter Cooking Duration: ");
        double dura = scnDouble.nextDouble();
        
        // creating a student object with the data inputted to be implemented into the database
        Recipe recipe = readById(repId);
        recipe.setMealId(mealId);
        recipe.setRecipeName(repName);
        recipe.setCalories(calo);
        recipe.setCookingDuration(dura);
        
        
        
        System.out.println("Recipe ID " + repId + "Found -- Updating");
        update(recipe);
    }

    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    // got it from query documentation Oracle https://docs.oracle.com/javaee/6/tutorial/doc/bnbrg.html
    public List<Recipe> readByCal(Double calories){
       return manager.createQuery("SELECT r FROM Recipe r WHERE r.calories < :caloriesAdd")
                .setParameter("caloriesAdd", calories)
                .getResultList();
        //System.out.println(recipes.size());
       
    }
    
    // got it with the help of the Criteria Query Paramters from https://www.objectdb.com/java/jpa/query/parameter
    public List<Recipe> readByString(String namec){
       return manager.createQuery("SELECT r FROM Recipe r WHERE r.recipeName LIKE  '%" + namec + "%' ")
                //.setParameter("nameInc", namec)
                .getResultList();
        //System.out.println(recipes.size());
       
    }
    
    // extended from source code google doc and my Demo project
    public void create(Recipe recipe){
        
        try{
            
            manager.getTransaction().begin();
            
            if(recipe.getRecipeName() != null){
                manager.persist(recipe);
                
                manager.getTransaction().commit();
                
                System.out.println("Added to Recipe\n" + recipe.toString());
            }
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());            
        }
    }
    
    // extended from source code google doc
    public void update(Recipe recipe){
        try{
            
            Recipe existingRecipe = manager.find(Recipe.class, recipe.getRecipeId());
            
            if(existingRecipe != null){
            
                manager.getTransaction().begin();
            
                existingRecipe.setMealId(recipe.getMealId());
                existingRecipe.setRecipeName(recipe.getRecipeName());
                existingRecipe.setCalories(recipe.getCalories());
                existingRecipe.setCookingDuration(recipe.getCookingDuration());
                

                manager.getTransaction().commit();
            }
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());            
        }
    }
    
    // extended from source code google doc
    public void delete(Recipe recipe){
         Recipe existingRecipe = manager.find(Recipe.class, recipe.getRecipeId());
         
        try{
            if(existingRecipe != null){

                   manager.getTransaction().begin();

                   manager.remove(existingRecipe);


                   manager.getTransaction().commit();
           } 
        } catch(Exception ex){
            System.out.println(ex.getMessage());            
        }
    }
    
    // extended from source code from DERBY google doc
    public List<Recipe> readRecipe(){
        Query query = manager.createNamedQuery("Recipe.findAll");
        //System.out.println("Here Test");
        List<Recipe> recipes = query.getResultList();
        //System.out.println(recipes.size());
        return recipes;
    }
    
    public Recipe readById(int repId){
        Query query = manager.createNamedQuery("Recipe.findByRecipeId");
        
        query.setParameter("recipeId", repId);
        
        Recipe recipe = (Recipe)query.getSingleResult();
        if(recipe != null){
            System.out.println(recipe.toString());
        }
        
        return recipe;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String database = "AungNayHtetOoFXMLPU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
    }    
    
}
