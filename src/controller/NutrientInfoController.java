/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javax.persistence.Query;
import model.Nutrient;

/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class NutrientInfoController implements Initializable {
    
  
    @FXML
    private Button backBtn;

    @FXML
    private Button toEatBtn;

    @FXML
    private ImageView nurtImage;

    @FXML
    private TextFlow nutrientInfo;

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
    private Label nutrientTitle;

    @FXML
    private Label description;
    
    Scene previousScene;
    Nutrient selectedNutrient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // unused method and list
    public ArrayList<String> nutrientDetail(String description, String benefit, String calories){
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add(description);
        tempList.add(benefit);
        tempList.add(calories);
        
        return tempList;
    }

    // All set method which prepares the view before shown to the user
    // Text to be displayed. Uses queries to get the needed text from the database
    // referenced from stackoverflow
    public void setTextFlow(){
        //Creating text objects  
      Text nutrientName = new Text(selectedNutrient.getNutrientName()); 
      
      //Setting font to the text 
      nutrientName.setFont(new Font(20)); 
      
      //Setting color to the text  
      nutrientName.setFill(Color.DARKSLATEBLUE); 
       
      //Text text2 = new Text(System.lineSeparator()); 
      
      //Setting font to the text 
      Text benefit = new Text("\n" + selectedNutrient.getBenefit()); 
      
      //Setting font to the text 
      benefit.setFont(new Font(15)); 
      
      //Setting color to the text 
      benefit.setFill(Color.BLACK);
       
      Text dailyIntake = new Text("\nRecommend Daily Intake: " + selectedNutrient.getDailyIntake() + " grams"); 
      
      //Setting font to the text 
      dailyIntake.setFont(new Font(15)); 
      dailyIntake.setFill(Color.MEDIUMVIOLETRED); 

      //Setting the line spacing between the text objects 
      nutrientInfo.setTextAlignment(TextAlignment.LEFT); 

      //Setting the line spacing  
      nutrientInfo.setLineSpacing(5.0);
      //Retrieving the observable list of the TextFlow Pane 
      ObservableList list = nutrientInfo.getChildren(); 
      
      //Adding text into TextFlow
      list.addAll(nutrientName, benefit, dailyIntake);   
      
      //list.forEach(i -> System.out.println(i.toString()));
    }
    
    // Set the previous scenes for navigation panel
    public void setPreviousScene(Scene scene){
        previousScene = scene;
        backBtn.setDisable(false);
    }
    
    // Set the dynamic header for the navigation panel        
    public void setHeader(String header){
        locationHeader1.setText(header);
    }
    
    void initData(Nutrient selectedNutrient) {
        this.selectedNutrient = selectedNutrient;
        nutrientTitle.setText(selectedNutrient.getNutrientName());
        description.setText(selectedNutrient.getDescription());
        locationHeader2.setText(selectedNutrient.getNutrientName());
        icon2.setFill(Color.web("#979797"));
        setTextFlow();
        try{
            String imgName = "/resource/nutrient/" + selectedNutrient.getNutrientName().toLowerCase() + ".jpg";
            Image img = new Image(getClass().getResourceAsStream(imgName));
            nurtImage.setImage(img);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    // Back button method    
    @FXML
    private void goBackScene(ActionEvent event) {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        
        if(previousScene != null){
            stage.setScene(previousScene);
        }
    }

    // Method to setup and call MealFoodView. 
    @FXML
    private void openWhatToEat(ActionEvent event) throws IOException {
        Nutrient nutrientSelected = selectedNutrient;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MealFoodView.fxml"));
        
        try{        
            System.out.println(nutrientSelected.toString());
            Parent detailModelView = loader.load();
            Scene tableViewScene = new Scene(detailModelView);

            MealFoodViewController controller = loader.getController();
            controller.initData(nutrientSelected);
            
            controller.setHeader(locationHeader1.getText(), locationHeader2.getText());

            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setScenes(currentScene,previousScene);
            String css = MealFoodViewController.class.getResource("/view/ScrollPane.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(tableViewScene);
            stage.show();
        } catch(NullPointerException  e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Button Malfunctioning");
            alert.setHeaderText("Meal and Recipe data not found in system");
            alert.setContentText("Please check the database");
            alert.showAndWait();
        }
    }
    
}
