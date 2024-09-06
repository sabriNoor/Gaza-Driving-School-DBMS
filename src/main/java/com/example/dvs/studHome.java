package com.example.dvs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class studHome implements Initializable {

    @FXML
    private Button editProf;

    @FXML
    private Button financial;
    @FXML
    private Label name;

    @FXML
    private Button hme;

    @FXML
    private Button lesson;

    @FXML
    private Button logout;

    @FXML
    private StackPane pane;

    @FXML
    private ImageView studImg;

    @FXML
    void logout(ActionEvent event) throws IOException {
        JOptionPane.showMessageDialog(null," Goodbye have a good day! ","GoodBye",JOptionPane.PLAIN_MESSAGE);
        exit(0);
    }
    @FXML
    void lesson(ActionEvent event) throws IOException {

            Parent lool = FXMLLoader.load(getClass().getResource("studLesson.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(lool);
    }
    @FXML
    void home(ActionEvent event) throws IOException {

        Parent lool = FXMLLoader.load(getClass().getResource("studDash.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(lool);
    }
//editProf
@FXML
void editProf(ActionEvent event) throws IOException {

    Parent lool = FXMLLoader.load(getClass().getResource("editProfileStud.fxml"));
    pane.getChildren().removeAll();
    pane.getChildren().setAll(lool);
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Parent lool = null;
        try {
            lool = FXMLLoader.load(getClass().getResource("studDash.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(lool);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String LogInSQL = "SELECT email, password, ssn, fname, mname, lname, gender FROM driving_school.students WHERE ssn=?";
        try {
            PreparedStatement st = DBconn.con.prepareStatement(LogInSQL);
            st.setString(1, userInfo.ssn);

            ResultSet re = st.executeQuery();

            while (re.next()) {
                userInfo.email = re.getString("email");
                userInfo.password = re.getString("password");
                userInfo.ssn = re.getString("ssn");
                userInfo.fname = re.getString("fname");
                userInfo.mname = re.getString("mname");
                userInfo.lname = re.getString("lname");
                userInfo.gender = re.getString("gender");
            }

            // Close the ResultSet and PreparedStatement in a finally block
            re.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        name.setText(userInfo.fname+" "+userInfo.lname);
        if ("female".equals(userInfo.gender)) {
            String imagePath = "studentGirl.png";
            Image instructorImage = new Image(getClass().getResourceAsStream(imagePath));
            studImg.setImage(instructorImage);


        }


    }
}
