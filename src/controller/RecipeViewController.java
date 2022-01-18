/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Meal;
import model.Recipe;
import model.Recipeinformation;

/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class RecipeViewController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Label recipeInfo;

    @FXML
    private TextFlow textFlow;
    
    @FXML
    private ImageView recipeImage;

    @FXML
    private Circle icon2;

    @FXML
    private Circle icon3;

    @FXML
    private Circle icon1;

    @FXML
    private Circle icon4;

    @FXML
    private Button locationHeader3;

    @FXML
    private Button locationHeader2;

    @FXML
    private Button locationHeader1;

    @FXML
    private Button locationHeader4;

    @FXML
    private Label MealTitle;

    @FXML
    private Label description;

    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private SplitPane splitPane;
    
    Scene previousScene;
    Scene homeScene;
    Scene mealScene;
    Meal selectedMeal;
    EntityManager manager;

    /**
     * Initializes the controller class and the entity manager
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String database = "AungNayHtetOoFXMLPU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
    }
    
    // All set method which prepares the view before shown to the user
    // Set the different scenes for navigation panel
    public void setScenes(Scene homeScene, Scene mealScene, Scene previousScene){
        this.homeScene = homeScene;
        this.mealScene = mealScene;
        this.previousScene = previousScene;
        backBtn.setDisable(false);
    }
    // Set the dynamic header for the navigation panel    
    public void setHeader(String header, String header2, String header3){
        locationHeader1.setText(header);
        locationHeader2.setText(header2);
        locationHeader3.setText(header3);
        locationHeader4.setText("Meal Info");
        icon2.setFill(Color.web("#979797"));
        icon3.setFill(Color.web("#979797"));
        icon4.setFill(Color.web("#979797"));
    }

    // Combo box recipe options
    public void setComboBox(){  
        //System.out.println(readNutrient()[0]);
        ObservableList<String> options = 
    FXCollections.observableArrayList(
        readMealById());
        comboBox.setItems(options);
        //comboBox.getSelectionModel().selectFirst();
        
        //comboBox = new ComboBox(FXCollections.observableArrayList(readNutrient())); 
    }

    // Text to be displayed. Uses queries to get the needed text from the database
    // referenced from stackoverflow
    public void setTextFlow(Recipe selectedRecipe){
        //Recipe selectedRecipe
        Font sanSerif = Font.font("SanSerif");
        Text mealName = new Text(selectedMeal.getMealName()); 

        //Setting font to the text 
        mealName.setFont(new Font(20)); 

        //Setting color to the text  
        mealName.setFill(Color.DARKSLATEBLUE); 

        //Text text2 = new Text(System.lineSeparator()); 

        //Setting font to the text 
        Text benefit = new Text("\nCalories: " + selectedRecipe.getCalories()); 

        //Setting font to the text 
        benefit.setFont(new Font(15)); 
        
        benefit.setFont(sanSerif);

        //Setting color to the text 
        benefit.setFill(Color.BLACK);
        
        Text duration = new Text("\nDuration: " + selectedRecipe.getCookingDuration() + " minutes"); 

        duration.setFont(new Font(15)); 
        
        duration.setFont(sanSerif);

        duration.setFill(Color.BLACK);
        
        Text headerText = new Text("\nStep for Making " + selectedRecipe.getRecipeName());
        //Setting font to the text 
        headerText.setFont(new Font(20)); 
        //headerText.setFont(sanSerif);
        headerText.setFill(Color.DARKSLATEBLUE);

        Text steps = new Text("\n" + readRecipeInfoById(selectedRecipe.getRecipeId()).getRecipeInformation()); 

        //Setting font to the text 
        steps.setFont(new Font(15)); 
        
        steps.setFont(sanSerif);

        //Setting color to the text 
        steps.setFill(Color.BLACK);

        //Setting the line spacing between the text objects 
        textFlow.setTextAlignment(TextAlignment.LEFT); 

        //Setting the line spacing  
        textFlow.setLineSpacing(5.0);
        //Retrieving the observable list of the TextFlow Pane 
        ObservableList list = textFlow.getChildren(); 

        //Adding text into TextFlow
        list.addAll(mealName, benefit, duration, headerText, steps); 
    }
    
    // Back button method
    @FXML
    private void goBackScene(ActionEvent event) {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        
        if(previousScene != null){
            stage.setScene(previousScene);
        }
    }
    
    // Navigation panel, home button
    @FXML
    void goHome(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(homeScene != null){
            stage.setScene(homeScene);
        }
    }

    // Navigation panel, nutrient selection button
    @FXML
    void goNutrientSelection(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(mealScene != null){
            stage.setScene(mealScene);
        }
    }

    // Method to update TextFlow when a new recipe is selected
    @FXML
    void updateScene(ActionEvent event) {
        splitPane.setDividerPositions(.0);
        textFlow.getChildren().clear();
        setTextFlow(readRecipeByName());
        System.out.println(comboBox.getSelectionModel().getSelectedItem());
    }
    
    public void initData(Meal meal){
        this.selectedMeal = meal;
        MealTitle.setText(selectedMeal.getMealName());
        recipeInfo.setText(selectedMeal.getDescription());
        
        splitPane.setDividerPositions(.99);
        try{
            String imgName = "/resource/meal/" + selectedMeal.getMealName().toLowerCase() + ".jpg";
            Image img = new Image(getClass().getResourceAsStream(imgName));
            recipeImage.setImage(img);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
        setComboBox();
    }
    
    // Queries    
    public String[] readMealById(){
        Query query = manager.createNamedQuery("Recipe.findByMealId");
        
        query.setParameter("mealId", selectedMeal.getMealId());
        List<Recipe> repList = query.getResultList();
        while(repList != null){
            String listRep[] = new String[repList.size()];
            for(int i = 0; i < repList.size(); i++){
                listRep[i] = repList.get(i).getRecipeName();
            }
            return listRep;
        }
        return null;
        //System.out.println(repList.size());
        //System.out.println(listRep[0]);
        
    }
    
    public Recipe readRecipeByName(){
        Query query = manager.createNamedQuery("Recipe.findByRecipeName");
        System.out.println(comboBox.getValue());
        query.setParameter("recipeName", comboBox.getValue());
        Recipe repList = (Recipe)query.getSingleResult();
        while(repList != null){
            return repList;
        }
        return null;
        //System.out.println(repList.size());
        //System.out.println(listRep[0]);
        
    }
    
    public Recipeinformation readRecipeInfoById(int id){
        Query query = manager.createNamedQuery("Recipeinformation.findByRecipeId");
        //System.out.println(comboBox.getValue());
        query.setParameter("recipeId", id);
        Recipeinformation repList = (Recipeinformation)query.getSingleResult();
        while(repList != null){
            return repList;
        }
        return null;
        //System.out.println(repList.size());
        //System.out.println(listRep[0]);
        
    }


}
