package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.dvs.instructorMyStudent.selectedRowMystudentEnrollmentId;

public class instructorMyStudentShowLesson implements Initializable {

    @FXML
    private TableColumn<lessonTable, Date> Date_lesson_column;

    @FXML
    private TableColumn<lessonTable, Integer> LessonId_coiumn;

    @FXML
    private TableColumn<lessonTable, Time> Time_lesson_column;

    @FXML
    private TableColumn<lessonTable, Integer> courseID_lesson_column;

    @FXML
    private TableColumn<lessonTable, Integer>enrollmentID_lesson_column;

    @FXML
    private TableView<lessonTable> lesson_table;

    @FXML
    private TableColumn<lessonTable, String> student_ssn;

    @FXML
    private TableColumn<lessonTable, String> vehicleID_lesson_column;
    @FXML
    private Button delete,update,finishup,close;

    @FXML
    private Label l221;

    @FXML
    private Label l2211;

    @FXML
    private DatePicker lessonDate;

    @FXML
    private TextField lessonTime_field11;


    @FXML
    private AnchorPane updatepanel;


    @FXML
    void closea(ActionEvent event) {

    }

    public void deletea(javafx.event.ActionEvent actionEvent) {

        if (lesson_table.getSelectionModel().getSelectedIndex() >= 0){
            int selectedRow = lesson_table.getSelectionModel().getSelectedIndex();
            lessonTable m = lesson_table.getItems().get(selectedRow);
            int selectedRowlessonid = m.getLessonId();

            String deleteQuery = "DELETE FROM driving_school.lesson WHERE lessonid = ?";

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, selectedRowlessonid);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(null,"Done succussfully!");

                    System.out.println("Record deleted successfully");
                } else {

                    System.out.println("No matching record found for deletion");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"select row!");

        }
        showData();
    }

    @FXML
    public void finishupdate(javafx.event.ActionEvent event) {
        lessonTable selectedLesson = lesson_table.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            LocalDate localDate = lessonDate.getValue();

            // Convert LocalDate to java.util.Date
            java.sql.Date lessonDateUtil = java.sql.Date.valueOf(localDate.atStartOfDay().toLocalDate());

            // Check if the lesson already exists for the given date and time
            if (lessonExists(lessonDateUtil.toLocalDate(), lessonTime_field11.getText())) {
                JOptionPane.showMessageDialog(null, "Lesson already exists for the selected date and time.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Don't proceed with the insertion
            } else {
                LocalDate updatedDate = lessonDate.getValue();
                String updatedTime = lessonTime_field11.getText();

                // Update the lesson in the database
                String updateQuery = "UPDATE driving_school.lesson SET lessondate = ?, starttime = ? WHERE lessonid = ?";

                try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateQuery)) {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(updatedDate));
                    preparedStatement.setTime(2, java.sql.Time.valueOf(LocalTime.parse(updatedTime)));
                    preparedStatement.setInt(3, selectedLesson.getLessonId());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Lesson updated successfully");

                        System.out.println("Lesson updated successfully");
                    } else {
                        System.out.println("No matching record found for update");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        //showData();


    }
    private boolean lessonExists(LocalDate lessonDate, String lessonTime) {
        String checkQuery = "SELECT COUNT(*) AS count FROM driving_school.lesson WHERE lessondate = ? AND starttime = CAST(? AS TIME)";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(checkQuery)) {
            // Convert LocalDate to java.util.Date
            java.sql.Date lessonDateUtil = java.sql.Date.valueOf(lessonDate.atStartOfDay().toLocalDate());

            preparedStatement.setDate(1, lessonDateUtil);
            preparedStatement.setString(2, lessonTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // If count is greater than 0, the lesson already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return false;
    }

    @FXML
    public void updatea(javafx.event.ActionEvent event) {
        if (lesson_table.getSelectionModel().getSelectedIndex() >= 0){
            int selectedRow = lesson_table.getSelectionModel().getSelectedIndex();
            lessonTable m = lesson_table.getItems().get(selectedRow);

            String time=m.getLessonTime().toString();

            lessonTime_field11.setText(time);

            java.util.Date utilDate = new java.util.Date(m.getLessonDate().getTime());

            Instant instant = utilDate.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            lessonDate.setValue(localDate);


        }
        else {
            JOptionPane.showMessageDialog(null,"select row!");

        }
        showData();
    }

    public void closea(javafx.event.ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }





    private ObservableList<lessonTable> ob= FXCollections.observableArrayList();

    @Override
    //public lessonTable(int lessonId, int courseId, String vehicleId, Date lessonDate, Time lessonTime) {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        LessonId_coiumn.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        Date_lesson_column.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        Time_lesson_column.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
        courseID_lesson_column.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        vehicleID_lesson_column.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));

        showData();


    }

    private void showData() {
        System.out.println("show data in");
        try {
            ob.clear();

            // Select lessons along with their enrollment details, courseid, and vehicleid
            String viewDataQuery = "SELECT lesson.lessonid, lesson.lessondate,lesson.starttime, enrollment.courseid, enrollment.vehicleid\n" +
                    "FROM driving_school.lesson\n" +
                    "JOIN driving_school.enrollment ON lesson.enrollmentid = enrollment.enrollmentid\n" +
                    "WHERE enrollment.instructorid = ? AND enrollment.enrollmentid = ?;\n";

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(viewDataQuery)) {
                preparedStatement.setString(1, userInfo.issn);
                preparedStatement.setInt(2, selectedRowMystudentEnrollmentId);
                System.out.println(567);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("loop out");
                    while (resultSet.next()) {
                        System.out.println("loop in");
                        // Retrieve data from the resultSet
                        Integer lessonId = resultSet.getInt("lessonid");
                        Date lessonDate = resultSet.getDate("lessondate");
                        Integer courseId = resultSet.getInt("courseid");
                        Time startTime = resultSet.getTime("starttime");

                        String vehicleId = resultSet.getString("vehicleid");
                        System.out.println(234);
                        // Process the retrieved data as needed
                        // ...

                        // Assuming you have a method to add data to ob
                        ob.add(new lessonTable(lessonId, courseId, vehicleId, lessonDate, startTime));

                    }
                    lesson_table.setItems(ob);

                }

                // Assuming you have a method to set the items in your table
                // myStudentTable.setItems(ob);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }



}
