package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Food;
import model.Meal;
import model.Nutrient;
import controller.RecipeViewController;

/**
 * FXML Controller class
 *
 * @author Aung Nay
 */
public class MealFoodViewController implements Initializable {

    @FXML
    private Button backBtn;
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
    private Label descriptionMenu;
    @FXML
    private Label descriptionFood;
    @FXML
    private Button mealBtn1;
    @FXML
    private Button mealBtn2;
    @FXML
    private Button mealBtn3;
    @FXML
    private Button mealBtn4;
    @FXML
    private ImageView mealPic1;
    @FXML
    private ImageView mealPic2;
    @FXML
    private ImageView mealPic3;
    @FXML
    private ImageView mealPic4;
    @FXML
    private Button foodBtn1;
    @FXML
    private Button foodBtn2;
    @FXML
    private Button foodBtn3;
    @FXML
    private Button foodBtn4;
    @FXML
    private ImageView foodPic1;
    @FXML
    private ImageView foodPic2;
    @FXML
    private ImageView foodPic3;
    @FXML
    private ImageView foodPic4;
    
    @FXML    
    ArrayList<ButtonPicture> foodList = new ArrayList<>();
    @FXML    
    ArrayList<ButtonPicture> mealList = new ArrayList<>();
    
    Scene previousScene;
    Scene homeScene;
 
    Nutrient selectedNutrient;
    private EntityManager manager;

    /**
     * Initializes the controller class and the entity manager
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String database = "AungNayHtetOoFXMLPU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
        setBtnList();
    }
    
    // making a hashmap containing the image and the button for food and meal
    class ButtonPicture{
        Button btn;
        ImageView pic;
        
        ButtonPicture(Button btn, ImageView pic){
            this.btn = btn;
            this.pic = pic;
        }
        
        public void setBtn(Button btn) {
            this.btn = btn;
        }
        
        public Button getBtn(){
            return btn;
        }

        public ImageView getPic() {
            return pic;
        }

        public void setPic(ImageView pic) {
            this.pic = pic;
        }
    }
    
    // add the button and image viewer into the hashmap
    public void setBtnList(){
        foodList.add(new ButtonPicture(foodBtn1, foodPic1));
        foodList.add(new ButtonPicture(foodBtn2, foodPic2));
        foodList.add(new ButtonPicture(foodBtn3, foodPic3));
        foodList.add(new ButtonPicture(foodBtn4, foodPic4));
        
        mealList.add(new ButtonPicture(mealBtn1, mealPic1));
        mealList.add(new ButtonPicture(mealBtn2, mealPic2));
        mealList.add(new ButtonPicture(mealBtn3, mealPic3));
        mealList.add(new ButtonPicture(mealBtn4, mealPic4));
        
    }
    
    // All set method which prepares the view before shown to the user
    // Set the different scenes for navigation panel
    public void setScenes(Scene scene, Scene scene2){
        previousScene = scene;
        homeScene = scene2;
        backBtn.setDisable(false);
    }
    
    // Set the dynamic header for the navigation panel
    public void setHeader(String header, String header2){
        locationHeader1.setText(header);
        locationHeader2.setText(header2);
        locationHeader3.setText("Meal & Food");
        icon2.setFill(Color.web("#979797"));
        icon3.setFill(Color.web("#979797"));
    }
    
    // Name the button
    public void setBtn(){
        int sizeM = readMealList().size();
        int sizeF = readFoodList().size();
       
        while(sizeM-1 >= 0){
            mealList.get(sizeM-1).btn.setText(readMealList().get(sizeM-1).getMealName());
            sizeM -= 1;
        }
        
        while(sizeF-1 >= 0){
            foodList.get(sizeF-1).btn.setText(readFoodList().get(sizeF-1).getFoodName());
            sizeF -= 1;
        }
    }
    
    // Add the picture
    public void setPic(){
        int sizeM = readMealList().size();
        int sizeF = readFoodList().size();
        
        while(sizeM-1 >= 0){
            try{
                String imgName = "/resource/meal/" + readMealList().get(sizeM-1).getMealName().toLowerCase() + ".jpg";
                //System.out.println(imgName);
                Image nutrImg = new Image(getClass().getResourceAsStream(imgName));
                mealList.get(sizeM-1).pic.setImage(nutrImg);
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            sizeM -= 1;
        }
        
        while(sizeF-1 >= 0){
            try{
                String imgName = "/resource/food/" + readFoodList().get(sizeF-1).getFoodName().toLowerCase() + ".jpg";
                //System.out.println(imgName);
                Image nutrImg = new Image(getClass().getResourceAsStream(imgName));
                foodList.get(sizeF-1).pic.setImage(nutrImg);
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            sizeF -= 1;
        }
        
        
    }

    public void initData(Nutrient nutrient){
        this.selectedNutrient = nutrient;
        setBtn();
        setPic();
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
    private void goHome(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(homeScene != null){
            stage.setScene(homeScene);
        }
    }
    
    // Queries for retreiving result(s) from the database
    public Meal readMealbyName(String name){
        Query query = manager.createNamedQuery("Meal.findByMealName");
        
        query.setParameter("mealName", name);
        
       Meal tempMeal = (Meal)query.getSingleResult();
        //System.out.println(list.toString());
        return tempMeal;
    }
    
    public Food readFoodbyName(String name){
        Query query = manager.createNamedQuery("Food.findByFoodName");
        
        query.setParameter("foodName", name);
        
        Food tempMeal = (Food)query.getSingleResult();
        //System.out.println(list.toString());
        return tempMeal;
    }
    
    public List<Meal> readMealList(){
        Query query = manager.createNamedQuery("Meal.findByNutrientId");
        
        query.setParameter("nutrientId", selectedNutrient.getNutrientId());
        
        List<Meal> list = query.getResultList();
        //System.out.println(list.toString());
        return list;
    }
    
    public List<Food> readFoodList(){
        Query query = manager.createNamedQuery("Food.findByNutrientId");
        
        query.setParameter("nutrientId", selectedNutrient.getNutrientId());
        
        List<Food> list = query.getResultList();
        //System.out.println(list.toString());
        return list;
    }

    // Method for clicking on the button in the Meal list
    @FXML
    void getMenu(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource();
        
        // testing if the button calls the correct nutrient from the database
//        System.out.println(btn.getText().toLowerCase());
        System.out.println(btn.getText().toLowerCase());
        
        Meal mealSelected = readMealbyName(btn.getText().toLowerCase());
        //nutrientSelected.getMealName();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
        
        
        try{        
            System.out.println(mealSelected.toString());
            Parent detailModelView = loader.load();
            Scene tableViewScene = new Scene(detailModelView);

            RecipeViewController controller = loader.getController();
            controller.initData(mealSelected);
            
            controller.setHeader(locationHeader1.getText(), locationHeader2.getText(), locationHeader3.getText());

            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setScenes(homeScene,previousScene,currentScene);

            String css = MealFoodViewController.class.getResource("/view/ScrollPane.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(tableViewScene);
            stage.show();
        } catch(NullPointerException  e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Button Malfunctioning");
            alert.setHeaderText("Meal not found in system");
            alert.setContentText("Please check the database");
            alert.showAndWait();
        }
    }

    // Method for clicking on the button in the Food list
    @FXML
    private void getFood(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource();
        
        // testing if the button calls the correct nutrient from the database
//        System.out.println(btn.getText().toLowerCase());
        System.out.println(btn.getText().toLowerCase());
        
        Food foodSelected = readFoodbyName(btn.getText().toLowerCase());
        //nutrientSelected.getMealName();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FoodView.fxml"));
        
        
        try{        
            System.out.println(foodSelected.toString());
            Parent detailModelView = loader.load();
            Scene tableViewScene = new Scene(detailModelView);

            FoodViewController controller = loader.getController();
            controller.initData(foodSelected);
            
            controller.setHeader(locationHeader1.getText(), locationHeader2.getText(), locationHeader3.getText());

            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setScenes(homeScene,previousScene,currentScene);
            
            String css = MealFoodViewController.class.getResource("/view/ScrollPane.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(tableViewScene);
            stage.show();
        } catch(NullPointerException  e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Button Malfunctioning");
            alert.setHeaderText("Food not found in system");
            alert.setContentText("Please check the database");
            alert.showAndWait();
        }
    }    
}
