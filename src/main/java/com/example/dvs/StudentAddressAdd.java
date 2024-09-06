package com.example.dvs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentAddressAdd {

    @FXML
    private Button addAddress_Btn;

    @FXML
    private AnchorPane add_Address_page;

    @FXML
    private AnchorPane an1;

    @FXML
    private AnchorPane an2;

    @FXML
    private TextField city_field;

    @FXML
    private ImageView city_img;

    @FXML
    private Label cityl1;

    @FXML
    private ImageView house_img;

    @FXML
    private TextField house_num_field;

    @FXML
    private Label housel;

    @FXML
    private AnchorPane inc3;

    @FXML
    private AnchorPane inc_title;

    @FXML
    private TextField street_field;

    @FXML
    private ImageView street_img;

    @FXML
    private Label strl1;

    @FXML
    private Label titlr_label;

    @FXML
    private ImageView titlt_img;

    @FXML
    private VBox vbox1;

    @FXML


    public void Addaddress(javafx.event.ActionEvent actionEvent) {
        String s=student.selectedRowSssn;
        System.out.println(s);
        try {
            String addAddressQuery = "INSERT INTO driving_school.stu_address (city, street, housenumber, stu_ssn) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(addAddressQuery)) {
                preparedStatement.setString(1, city_field.getText());
                preparedStatement.setString(2, street_field.getText());
                preparedStatement.setString(3, house_num_field.getText());
                preparedStatement.setString(4, s);

                preparedStatement.executeUpdate();
                System.out.println("Address added successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        city_field.setText("");
        street_field.setText("");
        house_num_field.setText("");
    }
}
