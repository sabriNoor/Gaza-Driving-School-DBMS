package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.util.ResourceBundle;

public class InstructorCourse implements Initializable{

    @FXML
    private TableColumn<courseTable, Integer> costcol;

    @FXML
    private TableColumn<courseTable, Integer> courseidcol;

    @FXML
    private TableColumn<courseTable, String> coursenamecol,vehicletypecol;

    @FXML
    private TableColumn<courseTable, String> descriptioncol;

    @FXML
    private TableColumn<courseTable, Period> durationcol;

    @FXML
    private TableView<courseTable> coursetable;
    ObservableList<courseTable> op= FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        coursenamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vehicletypecol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        durationcol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        costcol.setCellValueFactory(new PropertyValueFactory<>("cost_lesson"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("decription"));
        showData();

    }


    private void showData() {
        try {
            op.clear();

            String viewInstructor = "SELECT DISTINCT c.courseid, c.coursename, c.description, c.vehicletype, c.duration, c.costperlesson\n" +
                    "FROM driving_school.course c\n" +
                    "JOIN driving_school.enrollment e ON c.courseid = e.courseid\n" +
                    "WHERE e.instructorid = ?";

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(viewInstructor)) {
                preparedStatement.setString(1, userInfo.issn); // Replace with the actual instructor ID

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        op.add(new courseTable(
                                resultSet.getInt("courseid"),
                                resultSet.getString("coursename"),
                                resultSet.getString("vehicletype"),
                                resultSet.getString("duration"),
                                resultSet.getString("description"),
                                resultSet.getDouble("costperlesson")
                        ));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            coursetable.setItems(op);
        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }



}
