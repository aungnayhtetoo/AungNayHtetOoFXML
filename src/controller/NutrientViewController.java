/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Nutrient;
import model.Recipe;

/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class NutrientViewController implements Initializable {

    private EntityManager manager;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button nutrBtn1;

    @FXML
    private Button nutrBtn2;

    @FXML
    private Button nutrBtn3;

    @FXML
    private Button nutrBtn6;

    @FXML
    private Button nutrBtn5;

    @FXML
    private Button nutrBtn4;

    @FXML
    private Button quitBtn;

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
    ArrayList<Button> btnList = new ArrayList<>();

//    @FXML
//    void getNutr(ActionEvent event) {
//        Button btn = (Button)event.getSource();
//        // testing if the button calls the correct nutrient from the database
//        //System.out.println(btn.getText().toLowerCase());
//        //System.out.println(readNutrientByName(btn.getText().toLowerCase()).toString());
//    }
    
    // Set and name the Buttons
    public void setNutrientLabel(){
        btnList.add(nutrBtn1);
        btnList.add(nutrBtn2);
        btnList.add(nutrBtn3);
        btnList.add(nutrBtn4);
        btnList.add(nutrBtn5);
        btnList.add(nutrBtn6);
        
        for(int i = 0; i < btnList.size(); i++){
            btnList.get(i).setText(readById(i+1).getNutrientName());
        }
    }
    
    
    // Queries
    public Nutrient readNutrientByName(String nutrient){
        Query query = manager.createNamedQuery("Nutrient.findByNutrientName");
        
        query.setParameter("nutrientName", nutrient);
        
       Nutrient nutr = (Nutrient)query.getSingleResult();
        //System.out.println(nutr.toString());
        return nutr;
    }
    
    public List<Nutrient> readNutrient(){
        Query query = manager.createNamedQuery("Nutrient.findAll");
        
        //query.setParameter("nutrientId", repId);
        
        List<Nutrient> nutList = query.getResultList();
        //System.out.println(recipes.size());
        return nutList;
    }
    
    public Nutrient readById(int id){
        Query query = manager.createNamedQuery("Nutrient.findByNutrientId");
        
        query.setParameter("nutrientId", id);
        Nutrient nut = (Nutrient)query.getSingleResult();
        if(nut != null){
            System.out.println(nut.toString());
        }
        return nut;
    }
   

    // setup and calls NutrientInfo
    @FXML
    void getNutr(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource();
        
        // testing if the button calls the correct nutrient from the database
//        //System.out.println(btn.getText().toLowerCase());
//        //System.out.println(readNutrientByName(btn.getText().toLowerCase()).toString());
        
        Nutrient nutrientSelected = readNutrientByName(btn.getText().toLowerCase());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NutrientInfo.fxml"));
        
        
        try{        
            System.out.println(nutrientSelected.toString());
            Parent detailModelView = loader.load();
            Scene tableViewScene = new Scene(detailModelView);

            NutrientInfoController controller = loader.getController();
            controller.initData(nutrientSelected);
            
            controller.setHeader(locationHeader1.getText());

            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setPreviousScene(currentScene);
            
            // adding css for scroll pane
            String css = MealFoodViewController.class.getResource("/view/ScrollPane.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(tableViewScene);
            stage.show();
        } catch(NullPointerException  e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Button Malfunctioning");
            alert.setHeaderText("Nutrient not found in system");
            alert.setContentText("Please check the database");
            alert.showAndWait();
        }
        
    }
    
    // quit the app
    @FXML
    void exitApplicaiton(ActionEvent event) {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        String database = "AungNayHtetOoFXMLPU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
        setNutrientLabel();
    }
}
