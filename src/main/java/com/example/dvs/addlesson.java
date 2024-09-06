package com.example.dvs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class addlesson {

    @FXML
    private Button addBtn1_lesson;

    @FXML
    private Button clearBtn1_lesson,close;

    @FXML
    private AnchorPane course_information11;

    @FXML
    private VBox fill_inf_stu21;

    @FXML
    private AnchorPane fname_pane12;

    @FXML
    private AnchorPane fname_pane121;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane211311;

    @FXML
    private Label l221;

    @FXML
    private Label l321;

    @FXML
    private DatePicker lessonDate;

    @FXML
    private TextField lessonTime_field11;

    @FXML
    String vehicleId;
    public void closep(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the window
        stage.close();
    }
    public void clear(ActionEvent event) {
        lessonDate.setValue(null);
        lessonTime_field11.setText(null);
    }

    public void add(ActionEvent event) {
        try {
            Integer enrollmentId = instructorMyStudent.selectedRowMystudentEnrollmentId; // Replace with the actual enrollment id

            LocalDate localDate = lessonDate.getValue();

            // Convert LocalDate to java.util.Date
            Date lessonDateUtil = java.sql.Date.valueOf(localDate.atStartOfDay().toLocalDate());

            // Check if the lesson already exists for the given date and time
            if (lessonExists(lessonDateUtil.toLocalDate(), lessonTime_field11.getText())) {
                JOptionPane.showMessageDialog(null, "Lesson already exists for the selected date and time.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Don't proceed with the insertion
            } else {

                String insertLessonQuery = "INSERT INTO driving_school.lesson (lessondate, starttime, enrollmentid) VALUES (?, ?, ?)";

                try (PreparedStatement lessonStatement = DBconn.con.prepareStatement(insertLessonQuery)) {
                    lessonStatement.setDate(1, lessonDateUtil);
                    lessonStatement.setTime(2, java.sql.Time.valueOf(LocalTime.parse(lessonTime_field11.getText())));
                    lessonStatement.setInt(3, enrollmentId);

                    int affectedRows = lessonStatement.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately, log, or show an error message
                }
            }
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean lessonExists(LocalDate lessonDate, String lessonTime) {
        String checkQuery = "SELECT COUNT(*) AS count FROM driving_school.lesson WHERE lessondate = ? AND starttime = CAST(? AS TIME)";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(checkQuery)) {
            // Convert LocalDate to java.util.Date
            Date lessonDateUtil = java.sql.Date.valueOf(lessonDate.atStartOfDay().toLocalDate());

            preparedStatement.setDate(1, lessonDateUtil);
            preparedStatement.setString(2, lessonTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    System.out.println("in count>0");
                    return count > 0; // If count is greater than 0, the lesson already exists

                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        System.out.println("in count==0");
        return false;
    }


}
