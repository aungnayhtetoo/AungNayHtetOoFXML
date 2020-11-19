/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Student;

/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class DetailedModelViewController implements Initializable {

    @FXML
    private Label studentIdText;

    @FXML
    private Label nameText;

    @FXML
    private ImageView imageViewer;

    @FXML
    private Button backBtn;
    
    Student selectedModel;
    Scene previousScene;
    
    @FXML
    void goBackScene(ActionEvent event) {
        //current stage from backBtn
        Stage stage = (Stage)backBtn.getScene().getWindow();
        
        if(previousScene != null){
            stage.setScene(previousScene);
        }
    }
    
    public void setPreviousScene(Scene scene){
        previousScene = scene;
        backBtn.setDisable(false);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backBtn.setDisable(true);
    }    

    void initData(Student studentSelected) {
        selectedModel = studentSelected;
        nameText.setText(selectedModel.getFName() + " " + selectedModel.getLName());
        studentIdText.setText(selectedModel.getStudentId().toString());
        
        try{
            String imgName = "/resource/images/Crop/" + selectedModel.getStudentId().toString() + ".jpg";
            Image profilePic = new Image(getClass().getResourceAsStream(imgName));
            imageViewer.setImage(profilePic);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
