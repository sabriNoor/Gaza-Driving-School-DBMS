package com.example.dvs;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class home implements Initializable {

    @FXML
    private FontAwesomeIcon CAR;

    @FXML
    private Button Enrollment_btn_SideBar,logOut;

    @FXML
    private FontAwesomeIcon HOME_BUT;

    @FXML
    private AnchorPane Top_base_pane;

    @FXML
    private Label USERname_changeLabel;

    @FXML
    private Button adm_user;

    @FXML
    private AnchorPane base_pane;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button course_but;

    @FXML
    private Button financial_mang;

    @FXML
    private Button home_but;

    @FXML
    private Button instructor_but;

    @FXML
    private Button lesson_but;

    @FXML
    private ImageView logochoiceChange_top,image,image2;

    @FXML
    private VBox side_pane;

    @FXML
    private Button stu_but;

    @FXML
    public  Label labInfo;

    @FXML
    private AnchorPane top_pane_in_side;

    @FXML
    private Button vehicle_but;
    public static Label st_lab;
    private static ImageView staticLogoImageView;

    public static ImageView getStaticLogoImageView() {
        return staticLogoImageView;
    }
    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if ("female".equals(userInfo.gender)) {
            String imagePath = "woman.png";
            Image instructorImage = new Image(getClass().getResourceAsStream(imagePath));
            image.setImage(instructorImage);

            image2.setImage(instructorImage);

        }

        labInfo.setText("Home DashBoard");
        st_lab=labInfo;
        staticLogoImageView = logochoiceChange_top;
        USERname_changeLabel.setText(userInfo.fname+" "+userInfo.lname);

        try {

            Parent lool = FXMLLoader.load(getClass().getResource("dash.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(lool);
        } catch (Exception e) {
        }

    }


    @FXML
    void enroll(javafx.event.ActionEvent event) throws IOException {

        Parent lool = FXMLLoader.load(getClass().getResource("enroll.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }
    @FXML
    void instructor(javafx.event.ActionEvent event) throws IOException {

        Parent lool = FXMLLoader.load(getClass().getResource("instructor.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }
    @FXML
    void admin(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("admin.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }

    @FXML
    void course(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("course.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }


    @FXML
    void dash(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("dash.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }


    @FXML
    void lesson(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("lesson.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }

    @FXML
    void student(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("student.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);
    }

    @FXML
    void vehicle(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("vehicle.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }
    @FXML
    void financial(ActionEvent event) throws IOException {
        Parent lool = FXMLLoader.load(getClass().getResource("financial.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(lool);

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        JOptionPane.showMessageDialog(null," Goodbye have a good day! ","GoodBye",JOptionPane.PLAIN_MESSAGE);
        exit(0);
    }
}


