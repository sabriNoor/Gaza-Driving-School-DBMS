package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class lesson extends home implements Initializable {

    @FXML
    private TableColumn<lessonTable, Date> Date_lesson_column;

    @FXML
    private Button DeleteBtn1_lesson;

    @FXML
    private TableColumn<lessonTable, Integer> LessonId_coiumn;

    @FXML
    private TableColumn<lessonTable, Time> Time_lesson_column;

    @FXML
    private Button UpdateBtn1_lesson;

    @FXML
    private Button addBtn1_lesson;

    @FXML
    private Button clearBtn1_lesson,finish_but;

    @FXML
    private ChoiceBox<Integer> enrol_choice;

    @FXML
    private TableColumn<lessonTable, Integer> courseID_lesson_column;

    @FXML
    private AnchorPane course_information11;

    @FXML
    private TextField enrollmentIDS_lesson,searchtxt,UpdateLesson;

    @FXML
    private TableColumn<lessonTable, Integer> enrollmentID_lesson_column;

    @FXML
    private VBox fill_inf_stu21;

    @FXML
    private AnchorPane fname_pane12;

    @FXML
    private AnchorPane fname_pane121;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane2113;

    @FXML
    private AnchorPane fname_pane21131;

    @FXML
    private AnchorPane fname_pane211311;

    @FXML
    private AnchorPane fname_pane3221;

    @FXML
    private TableColumn<lessonTable, Integer> intructotId_lesson_column;

    @FXML
    private Label l121;

    @FXML
    private Label l221;

    @FXML
    private Label l321;

    @FXML
    private Label l621;

    @FXML
    private Label l721;

    @FXML
    private Label l821;

    @FXML
    private Label l8211;

    @FXML
    private DatePicker lessonDate,updaetDate;

    @FXML
    private TextField lessonId_field11;

    @FXML
    private TextField lessonTime_field11;

    @FXML
    private TableView<lessonTable> lesson_table;

    @FXML
    private AnchorPane lesson_table_pane;

    @FXML
    private Button searchBtn1_lesson;
    @FXML
    private AnchorPane updatePane;
private int lessonidUP;



    @FXML
    private TableColumn<lessonTable, String> vehicleID_lesson_column,student_ssn;
    private ObservableList<lessonTable> ob= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Lessons Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/lesson (1).png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        LessonId_coiumn.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        Date_lesson_column.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        Time_lesson_column.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
        intructotId_lesson_column.setCellValueFactory(new PropertyValueFactory<>("issn"));
        courseID_lesson_column.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        vehicleID_lesson_column.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        enrollmentID_lesson_column.setCellValueFactory(new PropertyValueFactory<>("enrolId"));
        student_ssn.setCellValueFactory(new PropertyValueFactory<>("stud_ssn"));
        updatePane.setVisible(false);
        showData();


        try{
            enrol_choice.getItems().clear();
            String query = "SELECT enrollmentid FROM driving_school.enrollment where status='active' ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                enrol_choice.getItems().add(resultSet.getInt("enrollmentid"));
            }

            statement.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void showData() {
        try {
            ob.clear();

            // Select all lessons along with their enrollment details
            String viewLessons = "SELECT\n" +
                    "    l.lessonid AS lesson_ids,\n" +
                    "    l.lessondate AS lesson_dates,\n" +
                    "    l.starttime AS start_times,\n" +
                    "    CONCAT(i.fname, ' ', i.lname) AS instructor_names,\n" +
                    "    e.courseid,\n" +
                    "    e.vehicleid,\n" +
                    "    e.enrollmentid,\n" +
                    "    CONCAT(s.fname, ' ', s.lname) AS student_names\n" +
                    "FROM\n" +
                    "    driving_school.lesson l\n" +
                    "INNER JOIN\n" +
                    "    driving_school.enrollment as e ON l.enrollmentid = e.enrollmentid\n" +
                    "LEFT JOIN\n" +
                    "    driving_school.students as s ON e.studentid = s.ssn\n" +
                    "LEFT JOIN\n" +
                    "    driving_school.instructor as i ON e.instructorid = i.issn\n" +
                    "ORDER BY\n" +
                    "    e.enrollmentid;";
            Statement st = DBconn.con.createStatement();
            ResultSet lessonResultSet = st.executeQuery(viewLessons);

            while (lessonResultSet.next()) {
                ob.add(new lessonTable(
                        lessonResultSet.getInt(1),
                        lessonResultSet.getDate(2),
                        lessonResultSet.getTime(3),
                        lessonResultSet.getString(4),
                        lessonResultSet.getInt(5),
                        lessonResultSet.getString(6),
                        lessonResultSet.getInt(7),
                        lessonResultSet.getString(8)
                ));
            }

            lesson_table.setItems(ob);

            FilteredList<lessonTable> filteredList=new FilteredList<>(ob,b->true);
            searchtxt.textProperty().addListener((observabl,oldvalue,newvalue)-> {
                filteredList.setPredicate(lessonTable -> {
                    if(newvalue.isEmpty()||newvalue.isBlank()||newvalue==null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord=newvalue.toLowerCase();
                    if(String.valueOf(lessonTable.getLessonId()).indexOf(searchWord) > -1)
                        return true;
                    else if(lessonTable.getIssn().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(String.valueOf(lessonTable.getEnrolId()).indexOf(searchWord) > -1)
                        return true;
                    else if(String.valueOf(lessonTable.getCourseId()).indexOf(searchWord) > -1)
                        return true;
                    else if(lessonTable.getVehicleId().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(lessonTable.getStud_ssn().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(lessonTable.getLessonTime().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(lessonTable.getLessonDate().toString().indexOf(searchWord)>-1)
                        return true;
                    else
                        return false;

                });
            });
            SortedList<lessonTable> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(lesson_table.comparatorProperty());
            lesson_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            String lessonDateStr = lessonDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String lessonTimeStr = lessonTime_field11.getText();

            if (lessonDateStr == null || lessonTimeStr == null || lessonTimeStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill both date and time fields.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (enrol_choice.getValue() == null) {
                JOptionPane.showMessageDialog(null, "Please fill the enrollment id field.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int enrollmentId = Integer.valueOf(enrol_choice.getValue());

                if (!isVechicleAvailable(Date.valueOf(lessonDate.getValue()).toLocalDate(), lessonTimeStr, enrollmentId)) {
                    JOptionPane.showMessageDialog(null, "The same vehicle is used in another lesson at the same time.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isInstructorAvailable(Date.valueOf(lessonDate.getValue()).toLocalDate(), lessonTimeStr, enrollmentId)) {
                    JOptionPane.showMessageDialog(null, "The instructor has another lesson at the same time.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Both vehicle and instructor are available, proceed with adding the lesson
                    String viewlesson = "INSERT INTO driving_school.lesson (lessondate, starttime, enrollmentid) VALUES ("
                            + "TO_DATE('" + lessonDateStr + "', 'DD-MM-YYYY'), '" + lessonTimeStr + "'," + enrollmentId + ")";

                    Statement st = DBconn.con.createStatement();
                    st.executeUpdate(viewlesson);
                    showData();
                    JOptionPane.showMessageDialog(null, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    private boolean isInstructorAvailable(LocalDate lessonDate, String lessonTime, int enrollId) throws SQLException {
        String instructorId = getInstructorId(enrollId);

        if (instructorId == null) {
            return false;
        }

        String query = "SELECT 1 FROM driving_school.lesson WHERE lessondate = ? AND starttime = ? AND enrollmentid IN (SELECT enrollmentid FROM driving_school.enrollment WHERE instructorid = ?)";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(lessonDate));
            preparedStatement.setTime(2, Time.valueOf(LocalTime.parse(lessonTime)));
            preparedStatement.setString(3, instructorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.next(); // Return true if no rows are returned (instructor is available)
            }
        }

    }

    private String getInstructorId(int enrollId) throws SQLException {
        String query = "SELECT instructorid FROM driving_school.enrollment WHERE enrollmentid = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setInt(1, enrollId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString( 1);
                }
            }
        }

        return null; // Return null if the instructor ID is not found
    }



    private boolean isVechicleAvailable(LocalDate lessonDate, String lessonTime, int enrollId) throws SQLException {
        String vehicleId = getVehicleId(enrollId);

        if (vehicleId == null) {
            // Handle the case where the vehicle ID is not found for the given enrollment ID
            return false;
        }

        String query = "SELECT 1 FROM driving_school.lesson WHERE lessondate = ? AND starttime = ? AND enrollmentid IN (SELECT enrollmentid FROM driving_school.enrollment WHERE vehicleid = ?)";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(lessonDate));
            preparedStatement.setTime(2, Time.valueOf(LocalTime.parse(lessonTime)));
            preparedStatement.setString(3, vehicleId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.next(); // Return true if no rows are returned (vehicle is available)
            }
        }
    }

    private String getVehicleId(int enrollId) throws SQLException {
        String query = "SELECT vehicleid FROM driving_school.enrollment WHERE enrollmentid = ?";

        try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
            preparedStatement.setInt(1, enrollId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        }

        return null; // Return null if the vehicle ID is not found
    }


    @FXML
    void clear(ActionEvent event) throws IOException {

        lessonTime_field11.setText("");
        lessonDate.setValue(null);
        enrol_choice.setValue(null);
    }
    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = lesson_table.getSelectionModel().getSelectedIndex();
        lessonTable m =  lesson_table.getItems().get(selectedRow);
        int s=m.getLessonId();
        System.out.println("s="+s);

        try {
            String deletelesson = "delete from driving_school.lesson where lessonid ='"+s+"'" ;
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deletelesson);
            ob.remove(m);
            showData();
            JOptionPane.showMessageDialog(null,"Delete successfully lesson with id  "+ s,"delete information",JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        updatePane.setVisible(true);

        int selectedRow = lesson_table.getSelectionModel().getSelectedIndex();

        if (selectedRow >= 0) {
            lessonTable selectedLesson = lesson_table.getItems().get(selectedRow);
            lessonidUP = selectedLesson.getLessonId();

            // Convert java.sql.Date to java.util.Date
            java.util.Date utilDate = new java.util.Date(selectedLesson.getLessonDate().getTime());

            Instant instant = utilDate.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            updaetDate.setValue(localDate);
            UpdateLesson.setText(selectedLesson.getLessonTime().toString());
        } else {
            System.out.println("No row selected for update.");
        }
    }

    @FXML
    void finish(ActionEvent event) throws IOException {
        LocalDate date = updaetDate.getValue();
        String time = UpdateLesson.getText(); // Assuming UpdateLesson is a TextField

        // Check if date and time are not null
        if (date != null && time != null && !time.isEmpty()) {
            String updateLessonQuery = "UPDATE driving_school.lesson SET lessondate = ?, starttime = ? WHERE lessonid = ?";
            System.out.println("lessonidUP=" + lessonidUP);

            try {
                PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateLessonQuery);

                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setTime(2, Time.valueOf(time));
                preparedStatement.setInt(3, lessonidUP);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");
                JOptionPane.showMessageDialog(null, "Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                showData();
                updatePane.setVisible(false);

            } catch (SQLException e) {
                System.out.println("Error updating lesson: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Updated Failed. Please enter valid date and time.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Please enter valid date and time.");
        }
    }



}
