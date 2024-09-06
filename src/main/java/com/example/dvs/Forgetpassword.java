package com.example.dvs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Forgetpassword implements Initializable{

    @FXML
    private Button check;
    @FXML
    private Label l1,l2;

    @FXML
    private Button close;

    @FXML
    private TextField email;

    @FXML
    private Button finish;

    @FXML
    private AnchorPane firstpanel;

    @FXML
    private PasswordField newpass1;

    @FXML
    private PasswordField newpass2;

    @FXML
    private AnchorPane panel2;

    @FXML
    private TextField ssn;

    @FXML
    void check(ActionEvent event) {
        String emailb= email.getText();
        String ssnb =ssn.getText();
        if(isEmailAndSSNExist(emailb, ssnb)!=0 ){

            firstpanel.setVisible(false);
            panel2.setVisible(true);

        }

        else {
            l1.setText("There Is Wrong Make Sure of What You Entered");
        }


    }

    @FXML
    void closee(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage4.setScene(scene);
            stage4.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }

    @FXML
    void finish(ActionEvent event) {
        String emailb = email.getText();
        String ssnb = ssn.getText();
        String p1 = newpass1.getText();
        String p2 = newpass2.getText();

        if (p1.equals(p2)) {

            int userType = isEmailAndSSNExist(emailb, ssnb);
            switch (userType) {
                case 1:
                    updateInstructorPassword(ssnb, p1);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                        Parent root = loader.load();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    l2.setText("Update Successfully");
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    //stage.close();

                    break;
                case 2:
                    updateAdminPassword(ssnb, p1);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                        Parent root = loader.load();

                        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage1.setScene(scene);
                        stage1.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    l2.setText("Update Successfully");
                    break;
                case 3:
                    updateStudentPassword(ssnb, p1);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                        Parent root = loader.load();

                        Stage stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage3.setScene(scene);
                        stage3.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    l2.setText("Update Successfully");
                    break;
                default:
                    l2.setText("No matching record found");
            }

        } else {
            l2.setText("Mismatch");
        }
    }

    public int isEmailAndSSNExist(String email, String ssn) {
        String searchQueryInstructor = "SELECT * FROM driving_school.instructor WHERE email = ? AND issn = ?";
        String searchQueryAdmin = "SELECT * FROM driving_school.admin WHERE email = ? AND ssn = ?";
        String searchQueryStudent = "SELECT * FROM driving_school.students WHERE email = ? AND ssn = ?";

        try (PreparedStatement preparedStatementInstructor = DBconn.con.prepareStatement(searchQueryInstructor)) {
            preparedStatementInstructor.setString(1, email);
            preparedStatementInstructor.setString(2, ssn);

            try (ResultSet resultSet = preparedStatementInstructor.executeQuery()) {
                if (resultSet.next()) {
                    // Matching record found in instructor table
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        try (PreparedStatement preparedStatementAdmin = DBconn.con.prepareStatement(searchQueryAdmin)) {
            preparedStatementAdmin.setString(1, email);
            preparedStatementAdmin.setString(2, ssn);

            try (ResultSet resultSet = preparedStatementAdmin.executeQuery()) {
                if (resultSet.next()) {
                    // Matching record found in admin table
                    return 2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        try (PreparedStatement preparedStatementStudent = DBconn.con.prepareStatement(searchQueryStudent)) {
            preparedStatementStudent.setString(1, email);
            preparedStatementStudent.setString(2, ssn);

            try (ResultSet resultSet = preparedStatementStudent.executeQuery()) {
                if (resultSet.next()) {
                    // Matching record found in student table
                    return 3;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // No matching record found
        return 0;
    }

    public void updateInstructorPassword(String ssn, String newPassword) {
        String updateQuery = "UPDATE driving_school.instructor SET password = ? WHERE issn = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, ssn);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Instructor password updated successfully.");
            } else {
                System.out.println("No matching record found for the specified instructor SSN.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void updateAdminPassword(String ssn, String newPassword) {
        String updateQuery = "UPDATE driving_school.admin SET password = ? WHERE ssn = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, ssn);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Admin password updated successfully.");
            } else {
                System.out.println("No matching record found for the specified admin SSN.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void updateStudentPassword(String ssn, String newPassword) {
        String updateQuery = "UPDATE driving_school.students SET password = ? WHERE ssn = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, ssn);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student password updated successfully.");
            } else {
                System.out.println("No matching record found for the specified student SSN.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        panel2.setVisible(false);
        firstpanel.setVisible(true);
    }
}
