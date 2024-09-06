package com.example.dvs;

import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static javafx.scene.image.Image.*;

public class instructorUser implements Initializable {

    @FXML
    private Label USERname_changeLabel;

    @FXML
    private Button course;

    @FXML
    private Button editprofile;

    @FXML
    private Button home;

    @FXML
    private ImageView instructorphoto;

    @FXML
    private Button logout;
    @FXML

    private StackPane contentArea;
    @FXML
    private Button myStudent;
    @FXML
    void mystudentbutton(javafx.event.ActionEvent event) throws IOException {

        Parent lool = FXMLLoader.load(getClass().getResource("instructorMyStudent.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }

    public void EditProfilebtn(ActionEvent actionEvent) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("EditProfileIns.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }

    public void Home(ActionEvent actionEvent) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("instructorHome.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }

    public void Course(ActionEvent actionEvent) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("instructorCourse.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }
    public void logoutb(ActionEvent actionEvent) throws IOException {
        Platform.exit();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        USERname_changeLabel.setText(userInfo.fname + " " + userInfo.lname);

        if ("female".equals(userInfo.gender)) {
            String imagePath = "businesswoman.png";
            Image instructorImage = new Image(getClass().getResourceAsStream(imagePath));
            instructorphoto.setImage(instructorImage);
        }

        String LogInSQL = "SELECT email, password, issn, fname, mname, lname, gender FROM driving_school.instructor WHERE issn=?";
        try {
            PreparedStatement st = DBconn.con.prepareStatement(LogInSQL);
            st.setString(1, userInfo.issn);

            ResultSet re = st.executeQuery();

            while (re.next()) {
                userInfo.email = re.getString("email");
                userInfo.password = re.getString("password");
                userInfo.issn = re.getString("issn");
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

        Parent lool = null;
        try {
            lool = FXMLLoader.load(getClass().getResource("instructorHome.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }

}
