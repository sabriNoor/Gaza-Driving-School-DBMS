package com.example.dvs;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class signUp implements Initializable{

    @FXML
    private PasswordField confirm_pass,password_txt;

    @FXML
    private TextField email_txt;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton male;

    @FXML
    private Button save,CLOSE;

    @FXML
    private AnchorPane sign_up_panel;

    @FXML
    private Label signuu_lab,invalidemail;

    @FXML
    private TextField ssn_txt;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    String statemail,statepassword;
    String StudentIdToSearch ;
    int temp=0,temp2=0;
    public void closee(javafx.event.ActionEvent actionEvent) throws SQLException {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void save(javafx.event.ActionEvent actionEvent) throws SQLException {

        String ssn=ssn_txt.getText();
        String email=email_txt.getText();
        String password=password_txt.getText();
        String passwordcon=confirm_pass.getText();
        if(validateEmail(email)){
            invalidemail.setText("");
            System.out.println("valid");
            if(passwordcon.equals(password)){
                if(genderT!=null){

                    String instructorIdToSearch = ssn;
                    String searchQuery = "SELECT * FROM driving_school.instructor WHERE issn = ?";
                    //ResultSet resultSet, resultSet1;

                    try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(searchQuery)) {
                        preparedStatement.setString(1, instructorIdToSearch);
                        System.out.println("SQL Query: " + 999666);
                        //ResultSet resultSet = preparedStatement.executeQuery();
                        try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                            System.out.println("SQL Query: " + 442);
                            while (resultSet.next()) {
                                System.out.println("inside");
                                temp = 1;
                                userInfo.issn = resultSet.getString("issn");
                                userInfo.fname = resultSet.getString("fname");
                                userInfo.mname = resultSet.getString("mname");
                                userInfo.lname = resultSet.getString("lname");
                                userInfo.gender=resultSet.getString("gender");

                                statemail = resultSet.getString("email");
                                statepassword = resultSet.getString("password");
                                break;

                            }
                        }
                        if ((temp == 1) && (statepassword != null) && (statemail != null)) {
                            JOptionPane.showMessageDialog(null, "you have account");
                        } else if (temp == 1) {

                            String updateQuery = "UPDATE driving_school.instructor SET email = ?, password = ? WHERE issn = ?";
                            try (PreparedStatement ps = DBconn.con.prepareStatement(updateQuery)) {
                                if (compareAndInsertEmails(email) != null) {

                                    ps.setString(1, email);
                                    ps.setString(2, password);
                                    ps.setString(3, instructorIdToSearch);

                                    int rowsAffected = ps.executeUpdate();

                                    if (rowsAffected > 0) {
                                        System.out.println("Instructor credentials updated successfully.");
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructorUser.fxml"));
                                        Parent root = loader.load();

                                        // Create a new stage for the new window
                                        Stage newStage = new Stage();
                                        newStage.setTitle("Student Address"); // Set the title for the new window

                                        // Set the scene for the new stage
                                        Scene scene = new Scene(root);
                                        newStage.setScene(scene);

                                        // Show the new stage
                                        newStage.show();


                                    } else {
                                        System.out.println("No matching record found for the specified instructor ID.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "This Email is exits!");

                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        } else {
                            System.out.println("not instructor");

                        }
                    }


                    StudentIdToSearch = ssn;
                    String searchQuerys = "SELECT * FROM driving_school.students WHERE ssn = ?";

                    try (PreparedStatement pss = DBconn.con.prepareStatement(searchQuerys)) {
                        pss.setString(1, StudentIdToSearch);
                        System.out.println("SQL Query: " + 999666);

                        try (ResultSet resultSet = pss.executeQuery()) {
                            System.out.println("SQL Query: " + 442);
                            while (resultSet.next()) {
                                temp2 = 1;
                                userInfo.ssn = resultSet.getString("ssn");
                                userInfo.fname = resultSet.getString("fname");
                                userInfo.mname = resultSet.getString("mname");
                                userInfo.lname = resultSet.getString("lname");
                                userInfo.gender=resultSet.getString("gender");
                                statemail = resultSet.getString("email");
                                statepassword = resultSet.getString("password");

                            }
                        }
                        if ((temp2 == 1) && (statemail != null)) {
                            JOptionPane.showMessageDialog(null, "you have account");
                        } else if (temp2 == 1) {
                            String updateQuery = "UPDATE driving_school.students SET email = ?, password = ? WHERE ssn = ?";
                            try (PreparedStatement ps = DBconn.con.prepareStatement(updateQuery)) {
                                if(compareAndInsertEmails(email)!=null) {
                                    ps.setString(1, email);
                                    ps.setString(2, password);
                                    ps.setString(3, StudentIdToSearch);


                                    int rowsAffected = ps.executeUpdate();

                                    if (rowsAffected > 0) {
                                        System.out.println("student credentials updated successfully.");
                                    } else {
                                        System.out.println("No matching record found for the specified student ID.");
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"This Email is exits!");
                                }
                            }


                        }
                    }








                    System.out.println( userInfo.fname);
                }
                else{
                    JOptionPane.showMessageDialog(null,"select Gender plz");

                }




            }
            else{
                JOptionPane.showMessageDialog(null,"Mismatch Password");
            }
        }
        else {
            invalidemail.setText("Invalid Email!");
            System.out.println("not");
        }




    }
    private ToggleGroup genderToggleGroup;
    String genderT =null;
    private void handleRadioButtonAction(RadioButton selectedRadioButton) {
        // Get the custom text for the selected radio button
        if(selectedRadioButton.equals(male))
            genderT="male";
        else if (selectedRadioButton.equals(female))
            genderT="female";
        else
            genderT="";
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderToggleGroup = new ToggleGroup();
        male.setToggleGroup(genderToggleGroup);
        female.setToggleGroup(genderToggleGroup);
        male.setOnAction(event -> handleRadioButtonAction(male));
        female.setOnAction(event -> handleRadioButtonAction(female));

    }
    public String compareAndInsertEmails(String newEmail) {
        try {
            String query = "SELECT email, 'instructor' AS user_type FROM driving_school.instructor " +
                    "UNION " +
                    "SELECT email, 'student' AS user_type FROM driving_school.students " +
                    "UNION " +
                    "SELECT email, 'admin' AS user_type FROM driving_school.admin";

            try (Statement statement = DBconn.con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    boolean emailExists = false;

                    while (resultSet.next()) {
                        String existingEmail = resultSet.getString("email");

                        // Compare the new email with existing emails
                        if (newEmail.equalsIgnoreCase(existingEmail)) {
                            emailExists = true;
                            System.out.println("Email already exists: " + existingEmail);
                            break;
                        }
                    }

                    // If the email does not exist, insert it
                    if (!emailExists) {
                        // Your insert logic goes here
                        System.out.println("Inserting new email: " + newEmail);
                        return newEmail;
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return null;

    }

}
