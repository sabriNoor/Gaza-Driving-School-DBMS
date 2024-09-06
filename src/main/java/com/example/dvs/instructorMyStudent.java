package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class instructorMyStudent implements Initializable {

    @FXML
    private Button addlesson;

    @FXML
    private TableColumn<myStudentTable, Integer> countlesson;

    @FXML
    private RadioButton courseIdRadio;

    @FXML
    private RadioButton courseNameRadio;

    @FXML
    private TableColumn<myStudentTable, Integer> courseidcol;

    @FXML
    private TableColumn<myStudentTable, String> coursenamecol;

    @FXML
    private TableColumn<myStudentTable, Integer> enrollcol;

    @FXML
    private TableColumn<myStudentTable, String> fnamecol;

    @FXML
    private TableColumn<myStudentTable, String> lnamecol;

    @FXML
    private TableView<myStudentTable> myStudentTable;

    @FXML
    private TableColumn<myStudentTable, String> phonecol;

    @FXML
    private TextField searchbox;

    @FXML
    private Button showlesson;

    @FXML
    private RadioButton ssnRadio;

    @FXML
    private TableColumn<myStudentTable, String> ssncol;

    @FXML
    private RadioButton studentRadio;
    @FXML
    private Label lsearch;

    @FXML
    private Button viewAddress;

    ObservableList<myStudentTable> op = FXCollections.observableArrayList();

    public static Integer selectedRowMystudentEnrollmentId;
    public static Integer selectedRowMystudentCoureId;
    public static String selectedRowMystudentSSN;
    @FXML
    void referesh(ActionEvent event) {
        showData();
    }

    @FXML
    protected void addlesson(ActionEvent e) throws IOException {
        // Load the FXML file
        if (myStudentTable.getSelectionModel().getSelectedIndex() >= 0){
            int selectedRow = myStudentTable.getSelectionModel().getSelectedIndex();
            myStudentTable m = myStudentTable.getItems().get(selectedRow);
            selectedRowMystudentEnrollmentId = m.enrollid;
            selectedRowMystudentSSN = m.getSsn();
            selectedRowMystudentCoureId=m.getCourseid();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addlesson.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("Add Lesson"); // Set the title for the new window

            // Set the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();
        }
        else {
            JOptionPane.showMessageDialog(null,"select row!");

        }
        showData();

    }





    @FXML
    protected void show_lesson(ActionEvent e) throws IOException {
        // Load the FXML file
        if (myStudentTable.getSelectionModel().getSelectedIndex() >= 0){
            int selectedRow = myStudentTable.getSelectionModel().getSelectedIndex();
            myStudentTable m = myStudentTable.getItems().get(selectedRow);
            selectedRowMystudentEnrollmentId = m.enrollid;
            selectedRowMystudentSSN = m.getSsn();
            selectedRowMystudentCoureId=m.getCourseid();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("instructorMyStudentShowLesson.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("show Lesson"); // Set the title for the new window

            // Set the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();
        }
        else {
            JOptionPane.showMessageDialog(null,"select row!");

        }
        showData();

    }


    @FXML
    protected void viewAddress(ActionEvent e) throws IOException {
        // Load the FXML file
        if (myStudentTable.getSelectionModel().getSelectedIndex() >= 0){
            int selectedRow = myStudentTable.getSelectionModel().getSelectedIndex();
            myStudentTable m = myStudentTable.getItems().get(selectedRow);
            selectedRowMystudentEnrollmentId = m.enrollid;
            selectedRowMystudentSSN = m.getSsn();
            selectedRowMystudentCoureId=m.getCourseid();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("insviewAdd.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("show Address"); // Set the title for the new window

            // Set the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();
        }
        else {
            JOptionPane.showMessageDialog(null,"select row!");

        }
        showData();

    }






    private ToggleGroup searchToggleGroup;
    String searchCriteria = null;

    public void handleRadioButtonAction(RadioButton selectedRadioButton) {
        // Get the custom text for the selected radio button
        //  RadioButton selectedRadioButton = (RadioButton) actionEvent.getSource();
        if (selectedRadioButton.equals(ssnRadio))
            searchCriteria = "ssn";
        else if (selectedRadioButton.equals(studentRadio))
            searchCriteria = "student";
        else if (selectedRadioButton.equals(courseIdRadio))
            searchCriteria = "courseId";
        else if (selectedRadioButton.equals(courseNameRadio))
            searchCriteria = "courseName";
        else
            searchCriteria = "";
        showData();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchToggleGroup = new ToggleGroup();
        ssnRadio.setToggleGroup(searchToggleGroup);
        studentRadio.setToggleGroup(searchToggleGroup);
        courseIdRadio.setToggleGroup(searchToggleGroup);
        courseNameRadio.setToggleGroup(searchToggleGroup);

        ssnRadio.setOnAction(event -> handleRadioButtonAction(ssnRadio));
        studentRadio.setOnAction(event -> handleRadioButtonAction(studentRadio));
        courseIdRadio.setOnAction(event -> handleRadioButtonAction(courseIdRadio));
        courseNameRadio.setOnAction(event -> handleRadioButtonAction(courseNameRadio));


        enrollcol.setCellValueFactory(new PropertyValueFactory<>("enrollid"));
        ssncol.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        fnamecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        courseidcol.setCellValueFactory(new PropertyValueFactory<>("courseid"));
        coursenamecol.setCellValueFactory(new PropertyValueFactory<>("coursename"));
        countlesson.setCellValueFactory(new PropertyValueFactory<>("countlesson"));

        showData();



    }

    public void showData() {


        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            String viewStudentLessons = "SELECT e.enrollmentid, e.courseid, e.studentid," +
                    "       s.fname, s.lname, s.phonenumber," +
                    "       c.coursename," +
                    "       COUNT(l.lessonid) AS lesson_count" +
                    " FROM driving_school.enrollment e" +
                    " JOIN driving_school.students s ON e.studentid = s.ssn" +
                    " JOIN driving_school.course c ON e.courseid = c.courseid" +
                    " LEFT JOIN driving_school.lesson l ON e.enrollmentid = l.enrollmentid" +
                    " WHERE e.instructorid = ?" +
                    " GROUP BY e.enrollmentid, e.courseid, e.studentid, s.fname, s.lname, s.phonenumber, c.coursename;";
            System.out.println("SQL Query: " + viewStudentLessons);

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(viewStudentLessons)) {
                preparedStatement.setString(1, userInfo.issn);
                System.out.println("SQL Query: " + 999666);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("SQL Query: " + 442);
                    while (resultSet.next()) {
                        // Retrieve data from the resultSet
                        Integer enrollmentId = resultSet.getInt("enrollmentid");
                        Integer courseId = resultSet.getInt("courseid");
                        String studentId = resultSet.getString("studentid");
                        String fname = resultSet.getString("fname");
                        String lname = resultSet.getString("lname");
                        String phoneNumber = resultSet.getString("phonenumber");
                        String courseName = resultSet.getString("coursename");
                        int lessonCount = resultSet.getInt("lesson_count");
                        op.add(new myStudentTable(enrollmentId,courseId,courseName,lessonCount,studentId,fname,lname,phoneNumber));
                        myStudentTable.setItems(op);
                        // Process the retrieved data as needed
                        // ...
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }






            FilteredList<myStudentTable> filteredList = new FilteredList<>(op, b -> true);
            searchbox.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(myStudentTable -> {
                    if (newValue == null || newValue.trim().isEmpty()) {
                        lsearch.setText("");

                        return true;
                    }

                    String searchWord = newValue.toLowerCase();
                    if (searchCriteria == "ssn"){
                        lsearch.setText("");

                        return myStudentTable.getSsn().toLowerCase().contains(searchWord);

                    }

                    else if ( searchCriteria =="student"){
                        lsearch.setText("");

                        return   myStudentTable.getFname().toLowerCase().contains(searchWord)
                                || myStudentTable.getLname().toLowerCase().contains(searchWord);
                    }

                    else if ( searchCriteria == "courseId"){
                        lsearch.setText("");

                        return String.valueOf(myStudentTable.getCourseid()).contains(searchWord);

                    }
                    else if ( searchCriteria == "courseName"){
                        lsearch.setText("");

                        return myStudentTable.getCoursename().toLowerCase().contains(searchWord);
                    }
                    else{
                        lsearch.setText("YOU MUST SELECT BY WITCH DO YOU SEARCH");
                    }
                    return true;
                /*    return myStudentTable.getCoursename().toLowerCase().contains(searchWord)
                            || myStudentTable.getFname().toLowerCase().contains(searchWord)
                            || myStudentTable.getLname().toLowerCase().contains(searchWord)
                            || myStudentTable.getSsn().toLowerCase().contains(searchWord)
                            || String.valueOf(myStudentTable.getCourseid()).contains(searchWord);*/


                });
            });

            SortedList<myStudentTable> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(myStudentTable.comparatorProperty());
            myStudentTable.setItems(sortedList);


        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }



}
