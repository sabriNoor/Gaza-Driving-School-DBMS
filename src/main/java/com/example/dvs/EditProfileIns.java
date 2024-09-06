package com.example.dvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditProfileIns implements Initializable {

    @FXML
    private Label License;

    @FXML
    private Label birh_age;

    @FXML
    private Button changeAccountInfo;

    @FXML
    private Button checkpass;

    @FXML
    private AnchorPane checkpasspanel,updatepanel;

    @FXML
    private AnchorPane email;

    @FXML
    private Button exitxhange;

    @FXML
    private Button finishchangepassword;

    @FXML
    private ImageView image;

    @FXML
    private Label issn;

    @FXML
    private PasswordField newpass1;

    @FXML
    private PasswordField newpass2;

    @FXML
    private PasswordField password;

    @FXML
    private Label salary;

    @FXML
    private Label salary1,welcomeName,emaill;
    @FXML
    private TextField emailf;
    @FXML
    private Button show1;

    @FXML
    private Button show2;


    @FXML
    private Label wrongpass;
    String fname ;
    String mname ;
    String lname ;
    String email1 ;
    String phonenumber;
    String licensenumber,password12;
    double salary12 ;

    @FXML
    void changeAccountInfo() {
        checkpasspanel.setVisible(true);
        password.setText(null);
        wrongpass.setText("");

    }
    @FXML
    void testPass() {
        refresh();
        wrongpass.setText("");

        if(password12.equals(password.getText().toString())){
            checkpasspanel.setVisible(false);
            updatepanel.setVisible(true);
            emailf.setText(email1);
            newpass1.setText(null);newpass2.setText(null);
            emailf.setEditable(false);
            emailf.setStyle("-fx-control-inner-background: lightgray;");



        }
        else{
            wrongpass.setText("Wrong password");
        }

    }
    @FXML
    void finishup(){
        refresh();
        String s1=newpass1.getText().toString();
        String s2=newpass2.getText().toString();

        if(s1.equals(s2)){
            String updatePasswordQuery = "UPDATE driving_school.instructor SET password = ? WHERE issn = ?";
            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(updatePasswordQuery)) {
                System.out.println("new pass "+newpass1.getText());
                preparedStatement.setString(1, newpass1.getText());
                preparedStatement.setString(2, userInfo.issn);
                System.out.println(issn.toString());
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("No matching record found for the instructor SSN.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            JOptionPane.showMessageDialog(null, "change successful");

            updatepanel.setVisible(false);
            checkpasspanel.setVisible(false);

        }
        else {
            JOptionPane.showMessageDialog(null, "Password mismatch");

        }
    }
    @FXML
    void exitpan() {
        updatepanel.setVisible(false);
        checkpasspanel.setVisible(false);
    }

    @FXML
    void show222() {

    }
    @FXML
    void show111() {
        newpass1.setFont(Font.getDefault()); // Reset font to default
        newpass1.setStyle("-fx-text-fill: BLACK;");// lt
        newpass1.setDisable(false);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if ("female".equals(userInfo.gender)) {
            String imagePath = "businesswoman.png";
            Image instructorImage = new Image(getClass().getResourceAsStream(imagePath));
            image.setImage(instructorImage);
        }
        System.out.println("uer info+"+userInfo.issn);
        String Issn=userInfo.issn;
        String query = "SELECT fname, mname, lname, email, phonenumber, licensenumber, salary,password FROM driving_school.instructor WHERE issn = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setString(1, userInfo.issn);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    fname = resultSet.getString("fname");
                    mname = resultSet.getString("mname");
                    lname = resultSet.getString("lname");
                    email1 = resultSet.getString("email");
                    phonenumber = resultSet.getString("phonenumber");
                    licensenumber = resultSet.getString("licensenumber");
                    salary12 = resultSet.getDouble("salary");
                    password12 = resultSet.getString("password");

                    // Process the retrieved data as needed
                    System.out.println("First Name: " + fname);
                    System.out.println("Middle Name: " + mname);
                    System.out.println("Last Name: " + lname);
                    System.out.println("Email: " + email);
                    System.out.println("Phone Number: " + phonenumber);
                    System.out.println("License Number: " + licensenumber);
                    System.out.println("Salary: " + salary);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String NAME=fname+" "+mname+" "+lname;
        welcomeName.setText(NAME);
        issn.setText(userInfo.issn);
        License.setText(licensenumber);
        salary.setText(String.valueOf(salary12));
        emaill.setText(email1);

/*
        String dateOfBirthString =

        // Parse the date of birth string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);

        // Calculate age
        int age = calculateAge(dateOfBirth, LocalDate.now());

        birh_age.setText();
*/
    }
    private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }

    public void refresh() {
        System.out.println("refersh inss"+userInfo.issn+userInfo.fname);
        String Issn=userInfo.issn;
        String query = "SELECT fname, mname, lname, email, phonenumber, licensenumber, salary,password FROM driving_school.instructor WHERE issn = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setString(1, userInfo.issn);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    fname = resultSet.getString("fname");
                    mname = resultSet.getString("mname");
                    lname = resultSet.getString("lname");
                    email1 = resultSet.getString("email");
                    phonenumber = resultSet.getString("phonenumber");
                    licensenumber = resultSet.getString("licensenumber");
                    salary12 = resultSet.getDouble("salary");
                    password12 = resultSet.getString("password");

                    // Process the retrieved data as needed
                    System.out.println("First Name: " + fname);
                    System.out.println("Middle Name: " + mname);
                    System.out.println("Last Name: " + lname);
                    System.out.println("Email: " + email);
                    System.out.println("Phone Number: " + phonenumber);
                    System.out.println("License Number: " + licensenumber);
                    System.out.println("Salary: " + salary);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String NAME=fname+" "+mname+" "+lname;
        welcomeName.setText(NAME);
        issn.setText(Issn);
        License.setText(licensenumber);
        salary.setText(String.valueOf(salary12));
        emaill.setText(email1);


    }













}
