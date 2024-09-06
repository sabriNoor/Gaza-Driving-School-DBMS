package com.example.dvs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class stuEditProf implements Initializable {

    @FXML
    private PasswordField ConfPas;

    @FXML
    private Label StuSsn;

    @FXML
    private AnchorPane infoPane;

    @FXML
    private Label name;

    @FXML
    private PasswordField pas;

    @FXML
    private AnchorPane pasChange;

    @FXML
    private Button pasChangeBut;

    @FXML
    private Button save;

    @FXML
    private Label stuEmail;

    @FXML
    private ImageView studImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if ("female".equals(userInfo.gender)) {
            String imagePath = "studentGirl.png";
            Image instructorImage = new Image(getClass().getResourceAsStream(imagePath));
            studImage.setImage(instructorImage);
        }
        pasChange.setVisible(false);
        infoPane.setVisible(true);
        stuEmail.setText(userInfo.email);
        StuSsn.setText(userInfo.ssn);
        name.setText(userInfo.fname+" "+userInfo.lname);
    }
    @FXML
    void passToChange(ActionEvent event) throws IOException {
        pasChange.setVisible(true);
        infoPane.setVisible(false);
    }
    @FXML
    void save(ActionEvent event) throws IOException {
        if (pas.getText() != null && ConfPas != null) {
            if (pas.getText().equals(ConfPas.getText())) {
                try {
                    // Update password in the students table
                    String updatePasswordQuery = "UPDATE driving_school.students SET password = ? WHERE ssn = ?";
                    java.sql.PreparedStatement updatePasswordStmt = DBconn.con.prepareStatement(updatePasswordQuery);

                    // Set the new password and user SSN as parameters
                    updatePasswordStmt.setString(1, pas.getText());
                    updatePasswordStmt.setString(2, userInfo.ssn);

                    // Execute the update query
                    int rowsAffected = updatePasswordStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    pasChange.setVisible(false);
                    infoPane.setVisible(true);


                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Make sure that confirmed password field is  same as the password field", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You should fill both fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
