/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Student;

/**
 *
 * @author Aung Nay
 */
public class FXMLDocumentController implements Initializable {
    // cmd for connecting to database java -jar derbyrun.jar server start
    //  disconnecting java -jar derbyrun.jar server shutdown
    // this is a Database manager
    EntityManager manager;
    
    @FXML
    private Label label;
    
    @FXML
    private Button createStnBtn;

    @FXML
    private Button updateStnBtn;

    @FXML
    private Button deleteStuBtn;

    @FXML
    private Button readAllStnBtn;

    @FXML
    private Button readIdStnBtn;

    @FXML
    private Button readNameStnBtn;

    @FXML
    private Button readStringStn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Student> studentTable;
    
    @FXML
    private TableColumn<Student, Integer> studentId;

    @FXML
    private TableColumn<Student, String> fName;

    @FXML
    private TableColumn<Student, String> lName;

    @FXML
    private TableColumn<Student, Float> gpa;
    
    // list that has the students that will be used to insert data into the table
    private ObservableList<Student> studentInfo;

    // adding the right data into the Student Table
    // referenced from the sample code provided by Prof. Billah
    public void setTableData(List<Student> stlist){
        studentInfo = FXCollections.observableArrayList();
        
        stlist.forEach(student -> {
            studentInfo.add(student);
        });
        //System.out.println(studentInfo.toString());
        studentTable.setItems(studentInfo);
        studentTable.refresh();
    }
    
   
    
    @FXML
    void searchByName(ActionEvent event) {
        //System.out.println("Search Button Clicked");
        
        String nameEntered = searchField.getText();
        
        List<Student> students = readByFname(nameEntered);
        //System.out.println(students.toString());
        if(students == null || students.isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error with Search Result");
            alert.setHeaderText("Name not found");
            alert.setContentText("No student found with such name");
            alert.showAndWait();
        } else{
            setTableData(students);
        }
    }
    
    @FXML
    void createStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Creating a new Student");
        // read user input from command line
        System.out.println("Enter Student ID: ");
        int id = input.nextInt();
        
        System.out.println("Enter Student's First Name: ");
        String fname = input.next();
        
        System.out.println("Enter Student's Last Name: ");
        String lname = input.next();
        
        System.out.println("Enter Student's GPA: ");
        double gpa = input.nextDouble();
        
        // creating a student object with the data inputted to be implemented into the database
        Student student = new Student();
        student.setStudentId(id);
        student.setFName(fname);
        student.setLName(lname);
        student.setGpa(gpa);
        
        // creates the student in the database using the delete method
        create(student);
        
        System.out.println(student.printString());
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Deleting a Student");
        // read user input from command line
        System.out.println("Enter Student ID: ");
        int id = input.nextInt();
        
        // deletes the student in the database using the delete method
        Student student = readById(id);
        delete(student);
    }

    @FXML
    void updateStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Updating a Student");
        // read user input from command line
        System.out.println("Enter Student ID: ");
        int id = input.nextInt();
        
        System.out.println("Enter Student's First Name: ");
        String fname = input.next();
        
        System.out.println("Enter Student's Last Name: ");
        String lname = input.next();
        
        System.out.println("Enter Student's GPA: ");
        double gpa = input.nextDouble();
        
        // creating a student object with the data inputted to be implemented into the database
        Student student = new Student();
        student.setStudentId(id);
        student.setFName(fname);
        student.setLName(lname);
        student.setGpa(gpa);
        
        // updates the student in the database using the update method
        update(student);
    }
    
    
    @FXML
    void readStn(ActionEvent event) {
        List<Student> students = readStn();
        for(Student s: students)
        {
            System.out.println(s.printString());
        }

    }

    @FXML
    void readStnById(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Student s = readById(id);
        System.out.println(s.toString());

    }

    @FXML
    void readStnByName(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Search by \n1)First Name \n2)Last Name \nType 1 or 2");
        int choice = input.nextInt();
        
        if(choice == 1){
            System.out.println("\nEnter First Name:");
            String fName = input.next();
        
            List<Student> s = readByFname(fName);
            System.out.println(s.toString());
        } 
        else{
            System.out.println("\nEnter Last Name:");
            String lName = input.next();
        
            List<Student> s = readByLname(lName);
            System.out.println(s.toString());    
        }
        
        
       
    }

    @FXML
    void readStnByString(ActionEvent event) {

    }
    
    // create student method
//    public void create(Student student)
//    {
//        try{
//          manager.getTransaction().begin();
//          
//          // checking if primary key of student is null. if null will throw exception.
//          if(student.getStudentId()!= null){
//              manager.persist(student);
//              // ending the transcation and writing it onto the database
//              manager.getTransaction().commit();
//              
//              System.out.println(student.toString() + " is created");
//          }
//        } catch(Exception ev){
//            System.out.println(ev.getMessage());
//        }
//        
//    }
    
    public void create(Student student) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (student.getStudentId() != null) {
                
                // create student
                manager.persist(student);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(student.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    // update student method
    public void update(Student student)
    {
        try{
            Student existingStudent = manager.find(Student.class, student.getStudentId());
            
            if(existingStudent != null)
            {   
                manager.getTransaction().begin();
                
                existingStudent.setFName(student.getFName());
                existingStudent.setLName(student.getLName());
                existingStudent.setGpa(student.getGpa());
                
                // ending the transcation and writing it onto the database
                manager.getTransaction().commit();
            }
            
        } catch(Exception ev){
            System.out.println(ev.getMessage());
        }
    }
    
    // delete student method
    public void delete(Student student)
    {
        try{
            Student existingStudent = manager.find(Student.class, student.getStudentId());
            
            if(existingStudent != null)
            {   
                manager.getTransaction().begin();
                
                // method from EntityMangaer to remove from the database
                manager.remove(existingStudent);
                
                // ending the transcation and writing it onto the database
                manager.getTransaction().commit();
            }
            
        } catch(Exception ev){
            System.out.println(ev.getMessage());
        }
    }
    
    public List<Student> readStn(){
        Query query = manager.createNamedQuery("Student.findAll");
        
        List<Student> students = query.getResultList();
        return students;
    }
    
    public Student readById(int id){
        Query query = manager.createNamedQuery("Student.findByStudentId");
        
        query.setParameter("studentId", id);
        
        Student s = (Student)query.getSingleResult();
        if(s != null){
            System.out.println(s.getStudentId() + " " + s.getFName() + " " + s.getLName() + " " + s.getGpa());
        }
        return s;
    }
    
    public List<Student> readByFname(String fName){
        Query query = manager.createNamedQuery("Student.findByFName");
        
        // setting query paramater
        query.setParameter("fName", fName);
        
        // perform the query
        List<Student> students =  query.getResultList();
        for (Student s: students){
            System.out.println(s.getStudentId() + " " + s.getFName() + " " + s.getLName() + " " + s.getGpa());
        }
        
        return students;
    }
    
    public List<Student> readByLname(String lName){
        Query query = manager.createNamedQuery("Student.findByLName");
        
        // setting query paramater
        query.setParameter("lName", lName);
        
        // perform the query
        List<Student> students =  query.getResultList();
        for (Student s: students){
            System.out.println(s.getStudentId() + " " + s.getFName() + " " + s.getLName() + " " + s.getGpa());
        }
        
        return students;
    }
    

    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        Query query = manager.createNamedQuery("Student.findAll");
        List<Student> data = query.getResultList();
        
        for (Student s : data)
        {
            System.out.println(s.getStudentId() + " " + s.getFName() + " " + s.getLName() + " " + s.getGpa());
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //load data from database
        //database String
        String database = "JavaFXApplication1PU";
        manager = (EntityManager)Persistence.createEntityManagerFactory(database).createEntityManager();
        
        // tell what value goes at each table column
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
        gpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    }    
    
}
