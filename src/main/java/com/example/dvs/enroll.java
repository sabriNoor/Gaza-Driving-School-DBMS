package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import javafx.embed.swing.JFXPanel;


public class enroll extends home  implements Initializable {


    @FXML
    private TableColumn<enrollmentTable, String> VehicleID_colm;


    @FXML
    private Button add_but_enrol, FinishUpdatePane;

    @FXML
    private Button clear_but_enrol;

    @FXML
    private TableColumn<enrollmentTable, Date> completionDate_colm;

    @FXML
    private DatePicker completionDate_enrol_date, compDate;

    @FXML
    private TableColumn<enrollmentTable, Integer> courseID_colm;

    @FXML
    private ChoiceBox<Integer> courseID_enrol_choice;

    @FXML
    private Button delete_but_enrol;

    @FXML
    private AnchorPane enrol_fill_info;

    @FXML
    private TableView<enrollmentTable> enrol_table;

    @FXML
    private TableColumn<enrollmentTable, Date> enrollmentDate_colm;
    @FXML
    private Pane enrollPane;

    @FXML
    private DatePicker enrollmentDate_txt;

    @FXML
    private TableColumn<enrollmentTable, Integer> enrollmentId_colm;

    @FXML
    private TextField enrollmentId_enrol_txt, studentID_enrol;

    @FXML
    private TableColumn<enrollmentTable, String> instructorID_colm;

    @FXML
    private ChoiceBox<Integer> instructorID_enrol_choice;

    @FXML
    private Button search_but_enrol;

    @FXML
    private TableColumn<enrollmentTable, String> status_colm;

    //@FXML
    //private ChoiceBox<?> status_enrol_choice;

    @FXML
    private TableColumn<enrollmentTable, String> studentID_colm;

    @FXML
    private ChoiceBox<String> studChoice;
    @FXML
    private AnchorPane OnUpdatePane;


    @FXML
    private Button update_but_enrol,report;
    @FXML
    private TextField searchtxt;
    boolean courseSelected=false;

    public enum Status {
        complete, active
    }


    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            String enrollDateStr = enrollmentDate_txt.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String compDateStr = completionDate_enrol_date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            // Check if both date and time are provided
            if (enrollDateStr == null || enrollmentId_enrol_txt.getText().isBlank() || studChoice.getValue()==null || status_enrol_choice.getValue() == null || courseID_enrol_choice.getValue() == null || VehicleID_enrol_choice.getValue() == null) {
                JOptionPane.showMessageDialog(null, "Please fill all", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            /*  if (lessonDateStr == null || lessonTimeStr == null || lessonDateStr.isEmpty() || lessonTimeStr.isEmpty()) {
                System.out.println("Please enter both date and time.");
                return;*/

            // Assuming lessonid is a SERIAL column
            else {
                String addenroll = "INSERT INTO driving_school.enrollment (enrollmentdate, completiondate, status, courseid, vehicleid, studentid, instructorid,enrollmentid) VALUES (?, ?, CAST(? AS driving_school.statusenum), ?, ?, ?, ?,?)";

                try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(addenroll)) {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(enrollmentDate_txt.getValue())); // Assuming enrollmentDate_txt is a string in 'YYYY-MM-DD' format
                    preparedStatement.setDate(2, java.sql.Date.valueOf(completionDate_enrol_date.getValue())); // Assuming completionDate_enrol_date is a string in 'YYYY-MM-DD' format
                    preparedStatement.setString(3, status_enrol_choice.getValue()); // Assuming status_enrol_choice is a String
                    preparedStatement.setInt(4, courseID_enrol_choice.getValue()); // Assuming courseID_enrol_choice is an integer
                    preparedStatement.setString(5, VehicleID_enrol_choice.getValue()); // Assuming VehicleID_enrol_choice is a String
                    preparedStatement.setString(6, studChoice.getValue()); // Assuming studentID_enrol is a String
                    preparedStatement.setString(7, instructorID_enrol_choice.getValue().toString()); // Assuming instructorID_enrol_choice is a String
                    preparedStatement.setInt(8, Integer.parseInt(enrollmentId_enrol_txt.getText())); // Assuming instructorID_enrol_choice is a String


                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error inserting student: " + ex.getMessage());
                    ex.printStackTrace();
                }
                showData();
                JOptionPane.showMessageDialog(null, "Added successfully", "success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    @FXML
    void clear(ActionEvent event) throws IOException {

        studChoice.setValue(null);
        completionDate_enrol_date.setValue(null);
        enrollmentDate_txt.setValue(null);
        status_enrol_choice.setValue(null);
        courseID_enrol_choice.setValue(null);
        VehicleID_enrol_choice.setValue(null);
        instructorID_enrol_choice.setValue(null);
    }

    ObservableList<enrollmentTable> op = FXCollections.observableArrayList();


    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = enrol_table.getSelectionModel().getSelectedIndex();
        enrollmentTable m = enrol_table.getItems().get(selectedRow);
        int s = m.getEnrollmentid();
        System.out.println("s=" + s);

        try {
            String deleteEnrollment = "delete from driving_school.enrollment where enrollmentid ='" + s + "'";
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deleteEnrollment);
            op.remove(m);
            showData();
            JOptionPane.showMessageDialog(null, "Delete successfully lesson with id  " + s, "delete information", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    String STATE[] = {"active", "complete"};
    @FXML
    private ChoiceBox<String> VehicleID_enrol_choice, status_box, status_enrol_choice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status_enrol_choice.getItems().addAll(STATE);
        st_lab.setText("Enrollment Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/enrollment (3).png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        // status_enrol_choice.getItems().add();
        //courseID_enrol_choice.getItems().add();
        //VehicleID_enrol_choice.getItems().add();
        //instructorID_enrol_choice.getItems().add();
        //studChoice
        try {
            studChoice.getItems().clear();
            String query = "SELECT ssn FROM driving_school.students ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                studChoice.getItems().add(resultSet.getString(1));
            }

            statement.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(student.fE) {
            studChoice.setValue(student.studEnroll);
            student.fE=false;
        }

        try {
            courseID_enrol_choice.getItems().clear();
            String query = "SELECT courseId FROM driving_school.course ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                courseID_enrol_choice.getItems().add(resultSet.getInt("courseId"));
            }

            statement.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        status_box.getItems().addAll(STATE);
        try {
            VehicleID_enrol_choice.getItems().clear();
            String query = "SELECT licenseplate FROM driving_school.vehicle ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                VehicleID_enrol_choice.getItems().add(resultSet.getString(1));
            }

            statement.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            instructorID_enrol_choice.getItems().clear();
            String query = "SELECT issn FROM driving_school.instructor ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                instructorID_enrol_choice.getItems().add(resultSet.getInt("issn"));
            }

            statement.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        VehicleID_enrol_choice.setDisable(true);
        OnUpdatePane.setVisible(false);
        // Add an event listener to courseID_enrol_choice
        courseID_enrol_choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                // Set the flag to indicate that a course has been selected
                courseSelected = true;

                // Enable VehicleID_enrol_choice and update its contents based on the selected course
                VehicleID_enrol_choice.setDisable(false);
                updateVehicleChoiceBox(newValue);
            } else {
                // Clear the flag and disable VehicleID_enrol_choice if no course is selected
                courseSelected = false;
                VehicleID_enrol_choice.setDisable(true);
            }
        });


        enrollmentId_colm.setCellValueFactory(new PropertyValueFactory<>("enrollmentid"));
        enrollmentDate_colm.setCellValueFactory(new PropertyValueFactory<>("enrollmentdate"));
        completionDate_colm.setCellValueFactory(new PropertyValueFactory<>("completiondate"));
        status_colm.setCellValueFactory(new PropertyValueFactory<>("status"));
        courseID_colm.setCellValueFactory(new PropertyValueFactory<>("courseid"));
        VehicleID_colm.setCellValueFactory(new PropertyValueFactory<>("vehicleid"));
        studentID_colm.setCellValueFactory(new PropertyValueFactory<>("studentid"));
        instructorID_colm.setCellValueFactory(new PropertyValueFactory<>("instructorid"));

        showData();



    }
    private void updateVehicleChoiceBox(Integer courseId) {
        try {
            // Clear existing items from the choice box
            VehicleID_enrol_choice.getItems().clear();

            // Retrieve vehicles of the same type as the selected course
            String query = "SELECT licenseplate FROM driving_school.vehicle WHERE vtype = (SELECT vehicletype FROM driving_school.course WHERE courseId = ?)";

            try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    // Add each vehicle license plate to the choice box
                    VehicleID_enrol_choice.getItems().add(resultSet.getString(1));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    int enrollidUP;

    @FXML
    void update(ActionEvent event) throws IOException {
        OnUpdatePane.setVisible(true);

        int selectedRow = enrol_table.getSelectionModel().getSelectedIndex();

        if (selectedRow >= 0) {
            enrollmentTable selectedEnrollment = enrol_table.getItems().get(selectedRow);
            enrollidUP = selectedEnrollment.getEnrollmentid();
            // Print enrollment information for debugging
            System.out.println(selectedEnrollment.getEnrollmentid());
            System.out.println(selectedEnrollment.getCompletiondate());
            if (selectedEnrollment.getCompletiondate() != null) {

                // Set the value of compDate (DatePicker)
                java.util.Date utilDate = new java.util.Date(selectedEnrollment.getCompletiondate().getTime());

                Instant instant = utilDate.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                compDate.setValue(localDate);
            } else {
                compDate.setValue(null);
            }

            // Set the value of status_box (ChoiceBox)
            status_box.setValue(selectedEnrollment.getStatus());

        } else {
            System.out.println("No row selected for update.");
        }
    }

    @FXML
    void finish(ActionEvent event) throws IOException {

        // Check if date and time are not null
        String updateLessonQuery = "UPDATE driving_school.enrollment SET completiondate = ?, status = CAST(? AS driving_school.statusenum) WHERE enrollmentid = ?";

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateLessonQuery);
            if (compDate.getValue() != null) {

                preparedStatement.setDate(1, java.sql.Date.valueOf(java.sql.Date.valueOf(compDate.getValue()).toLocalDate()));
            } else {
                preparedStatement.setDate(1, null);
            }
            preparedStatement.setString(2, status_box.getValue());
            preparedStatement.setInt(3, enrollidUP);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Update successful");
            JOptionPane.showMessageDialog(null, "Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            showData();

            OnUpdatePane.setVisible(false);

        } catch (SQLException e) {
            System.out.println("Error updating enrol: " + e.getMessage());
            e.printStackTrace();
        }

    }


    private void showData() {
        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            String viewInstructor = "select enrollmentid,enrollmentdate,completiondate,status,courseid,vehicleid,studentid,instructorid from driving_school.enrollment ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);


            while (re.next()) {
                op.add(new enrollmentTable(re.getInt(1), re.getString(4),
                        re.getString(6), re.getString(7), re.getString(8)
                        , re.getDate(2), re.getDate(3),
                        re.getInt(5)));
            }
            enrol_table.setItems(op);

            FilteredList<enrollmentTable> filteredList = new FilteredList<>(op, b -> true);
            searchtxt.textProperty().addListener((observabl, oldvalue, newvalue) -> {
                filteredList.setPredicate(enrollmentTable -> {
                    if (newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord = newvalue.toLowerCase();

                    if (enrollmentTable.getStudentid()!=null && enrollmentTable.getStudentid().toLowerCase().indexOf(searchWord) > -1)
                        return true;
                    else if (String.valueOf(enrollmentTable.getCourseid()).indexOf(searchWord) > -1)
                        return true;
                    else if (String.valueOf(enrollmentTable.getEnrollmentdate()).indexOf(searchWord) > -1)
                        return true;
                    else if (enrollmentTable.getVehicleid()!=null && enrollmentTable.getVehicleid().toString().indexOf(searchWord) > -1)
                        return true;
                    else if (enrollmentTable.getStatus().toLowerCase().indexOf(searchWord) > -1)
                        return true;
                    else if (enrollmentTable.getInstructorid()!=null && enrollmentTable.getInstructorid().toString().indexOf(searchWord) > -1)
                        return true;
                    else
                        return false;

                });
            });
            SortedList<enrollmentTable> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(enrol_table.comparatorProperty());
            enrol_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
  /*  @FXML
    void report(ActionEvent event) throws IOException
    {
        InputStream inputStream;
        JasperReport jr;
        JasperDesign jd;
        JasperPrint jp;
        OutputStream outputStream;
        try {
            inputStream=new FileInputStream(new File("Blank_A4_2.jrxml"));
            jd= JRXmlLoader.load(inputStream);
            jr= JasperCompileManager.compileReport(jd);
            jp=JasperFillManager.fillReport(jr,null,DBconn.con);
            outputStream=new FileOutputStream(new File("enrollmentReport.pdf"));
            JasperExportManager.exportReportToPdfStream(jp,outputStream);
            outputStream.close();
            inputStream.close();
           // DBconn.con.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }*/
  @FXML
  void report(ActionEvent event) throws IOException {
      InputStream inputStream;
      JasperReport jr;
      JasperDesign jd;
      JasperPrint jp;
      try {
          inputStream = new FileInputStream(new File("Blank_A4_2.jrxml"));
          jd = JRXmlLoader.load(inputStream);
          jr = JasperCompileManager.compileReport(jd);
          jp = JasperFillManager.fillReport(jr, null, DBconn.con);

          // Show the JasperViewer
          JasperViewer viewer = new JasperViewer(jp, false);
          viewer.setTitle("Enrollment Report");
          viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          viewer.setVisible(true);

          inputStream.close();
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }
}