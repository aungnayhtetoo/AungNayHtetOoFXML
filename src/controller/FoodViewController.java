/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import model.Food;


/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class FoodViewController implements Initializable {

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
    private ImageView foodImage;
    @FXML
    private Button backBtn;
    @FXML
    private Label foodTitle;
    @FXML
    private Label info;
    @FXML
    private TextFlow textFlow;
    
    Scene previousScene;
    Scene homeScene;
    Scene mealScene;
    Food selectedFood;
    EntityManager manager;
    /**
     * Initializes the controller class and the entity manager
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String database = "AungNayHtetOoFXMLPU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
    }  
    
    // Back button method
    @FXML
    private void goBackScene(ActionEvent event) {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        
        if(previousScene != null){
            stage.setScene(previousScene);
        }
    }
    
    // Navigation panel, nutrient selection button
    @FXML
    private void goNutrientSelection(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(mealScene != null){
            stage.setScene(mealScene);
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
        locationHeader4.setText("Food Info");
        icon2.setFill(Color.web("#979797"));
        icon3.setFill(Color.web("#979797"));
        icon4.setFill(Color.web("#979797"));
    }
        
    // Text to be displayed. Uses queries to get the needed text from the database
    // referenced from stackoverflow
    public void setTextFlow(){
        //Recipe selectedRecipe
        Font sanSerif = Font.font("SanSerif");
        Text mealName = new Text(selectedFood.getFoodName()); 

        //Setting font to the text 
        mealName.setFont(new Font(20)); 

        //Setting color to the text  
        mealName.setFill(Color.DARKSLATEBLUE); 

        //Text text2 = new Text(System.lineSeparator()); 

        //Setting font to the text 
        Text benefit = new Text("\nCalories: " + selectedFood.getCalories()); 

        //Setting font to the text 
        benefit.setFont(new Font(15)); 
        
        benefit.setFont(sanSerif);

        //Setting color to the text 
        benefit.setFill(Color.BLACK);

        Text headerText = new Text("\nDetails " );
        //Setting font to the text 
        headerText.setFont(new Font(20)); 
        //headerText.setFont(sanSerif);
        headerText.setFill(Color.DARKSLATEBLUE);

        Text detail = new Text("\n" + selectedFood.getDescription()); 

        //Setting font to the text 
        detail.setFont(new Font(15)); 
        
        detail.setFont(sanSerif);

        //Setting color to the text 
        detail.setFill(Color.BLACK);

        //Setting the line spacing between the text objects 
        textFlow.setTextAlignment(TextAlignment.LEFT); 

        //Setting the line spacing  
        textFlow.setLineSpacing(5.0);
        //Retrieving the observable list of the TextFlow Pane 
        ObservableList list = textFlow.getChildren(); 

        //Adding text into TextFlow
        list.addAll(mealName, benefit, headerText, detail); 
    }
    
    public void initData(Food food){
        this.selectedFood = food;
        //this.foodInfo = readFoodInfoById(selectedFood.getFoodId());
        System.out.println(selectedFood.getFoodId());
        foodTitle.setText(selectedFood.getFoodName());
        info.setText("Details on " + selectedFood.getFoodName());
        setTextFlow();
        try{
            String imgName = "/resource/food/" + selectedFood.getFoodName().toLowerCase() + ".jpg";
            Image img = new Image(getClass().getResourceAsStream(imgName));
            foodImage.setImage(img);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    




}
