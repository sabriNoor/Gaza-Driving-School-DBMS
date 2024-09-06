package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class course extends home  implements Initializable {
    @FXML
    private RadioButton male;

    @FXML
    private TextArea Course_description_area;

    @FXML
    private TableColumn<courseTable, String> Course_description_column;

    @FXML
    private Button DeleteBtn1_course;

    @FXML
    private Button UpdateBtn1_course;

    @FXML
    private Button addBtn1_course;

    @FXML
    private Button clearBtn1_course;

    @FXML
    private TableView<courseTable> couese_table;

    @FXML
    private TextField courseCost_field1;

    @FXML
    private TableColumn<courseTable, String> courseDuration_column;

    @FXML
    private TextField courseId_field1;

    @FXML
    private TableColumn<courseTable, Double> course_costPLesson_column;

    @FXML
    private AnchorPane course_information1;

    @FXML
    private AnchorPane course_table_pane;

    @FXML
    private TableColumn<courseTable, Integer> courseid_column;

    @FXML
    private TextField coursename_field1;

    @FXML
    private TableColumn<courseTable, String> cousename_column;

    @FXML
    private TextField duration_field1,search;

    @FXML
    private VBox fill_inf_stu2;

    @FXML
    private AnchorPane fname_pane1;

    @FXML
    private AnchorPane fname_pane12;

    @FXML
    private AnchorPane fname_pane21;
    @FXML
    private ChoiceBox<String> vehicleChoice;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane2113;

    @FXML
    private AnchorPane fname_pane322,OnUpdatePane;

    @FXML
    private Label l12;

    @FXML
    private Label l22;

    @FXML
    private Label l32;

    @FXML
    private Label l62;

    @FXML
    private Label l72;

    @FXML
    private Label l82;

    @FXML
    private Button searchBtn1_course;

    @FXML
    private TableColumn<courseTable, Integer> studentCountCourse_column;

    @FXML
    private TableColumn<courseTable, String> vehicle_type_course_column;

    @FXML
    private TextField vehicle_type_field1,durationf,costf;
    @FXML

    private TextArea desArea;


    ObservableList<courseTable> op= FXCollections.observableArrayList();

    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            System.out.println("44");


            if(courseId_field1.getText().equals("")||courseId_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill ISSN field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(coursename_field1.getText().equals("")||coursename_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill frist name","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(vehicleChoice.getValue().equals(null))
                JOptionPane.showMessageDialog(null,"you should fill mid name field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(duration_field1.getText().equals("")||duration_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill last name field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( courseCost_field1.getText().equals("")||courseCost_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill phone number field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Course_description_area.getText().equals("")||Course_description_area.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill salary field","ERROR",JOptionPane.ERROR_MESSAGE);

            else {
                String insertCourseQuery = "INSERT INTO driving_school.course (courseid, coursename, description, vehicletype, duration, costperlesson) VALUES (?, ?, ?, ?, ?::interval, ?)";

                try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(insertCourseQuery)) {
                    // Assuming you have variables for the course details
                    preparedStatement.setInt(1, Integer.parseInt(courseId_field1.getText()));
                    preparedStatement.setString(2, coursename_field1.getText());
                    preparedStatement.setString(4, vehicleChoice.getValue());
                    preparedStatement.setString(5, duration_field1.getText());
                    preparedStatement.setDouble(6, Double.parseDouble(courseCost_field1.getText())); // Assuming duration is a string representing the interval
                    preparedStatement.setString(3, Course_description_area.getText());

                    preparedStatement.executeUpdate();
                    System.out.println("Course added successfully");
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            //  op = FXCollections.observableArrayList();
            showData();

        }

        catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }

    }


    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = couese_table.getSelectionModel().getSelectedIndex();
        courseTable m =  couese_table.getItems().get(selectedRow);
        Integer s=m.getId();
        System.out.println("s="+s);

        try {
            String deleteInstructor = "delete from driving_school.course where courseid ='"+s+"'" ;
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deleteInstructor);
            //Instructor_table.getItems().remove(selectedRow);
            op.remove(m);
            showData();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    int IDUpdate;
    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = couese_table.getSelectionModel().getSelectedIndex();
        courseTable m =  couese_table.getItems().get(selectedRow);
        IDUpdate=m.getId();
        String duration=m.getDuration();
        Double cost=m.getCost_lesson();
        String discrip=m.getDecription();
        durationf.setText(duration);
        costf.setText(cost.toString());
        desArea.setText(discrip);

        OnUpdatePane.setVisible(true);
        //  System.out.println("s="+s);
    }

    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        Double cost=Double.parseDouble(costf.getText());
        String duration=durationf.getText();
        String description=desArea.getText();
        String updateCourse = "UPDATE driving_school.course SET costperlesson = ?, duration = ?::interval, description = ? WHERE courseid = ?";

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateCourse);

            preparedStatement.setDouble(1, cost);

            preparedStatement.setString(2,duration);
            preparedStatement.setString(3,description);
            preparedStatement.setInt(4,IDUpdate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Update successful");




            showData();
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }
        OnUpdatePane.setVisible(false);


    }
    @FXML
    void clear(ActionEvent event) throws IOException {
        courseId_field1.setText("");
        coursename_field1.setText("");
        vehicleChoice.setValue(null);
        duration_field1.setText("");
        courseCost_field1.setText("");
        Course_description_area.setText("");

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Courses Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/training (2).png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        courseid_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        cousename_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        vehicle_type_course_column.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        courseDuration_column.setCellValueFactory(new PropertyValueFactory<>("duration"));
        course_costPLesson_column.setCellValueFactory(new PropertyValueFactory<>("cost_lesson"));
        studentCountCourse_column.setCellValueFactory(new PropertyValueFactory<>("number"));
        Course_description_column.setCellValueFactory(new PropertyValueFactory<>("decription"));

        try{
            vehicleChoice.getItems().clear();
            String query = "SELECT type FROM driving_school.typevehicle ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                vehicleChoice.getItems().add(resultSet.getString(1));
            }

            statement.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        showData();



    }

    private void showData() {
        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            String viewInstructor = "SELECT c.courseid, c.coursename, c.description, c.vehicletype, c.duration, c.costperlesson, COUNT(e.studentid) AS studentCount " +
                    "FROM driving_school.course c " +
                    "LEFT JOIN driving_school.enrollment e ON c.courseid = e.courseid " +
                    "GROUP BY c.courseid, c.coursename, c.description, c.vehicletype, c.duration, c.costperlesson";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);

            while (re.next()) {
                op.add(new courseTable(
                        Integer.parseInt(re.getString(1)),
                        re.getString(2),
                        re.getString(4),
                        re.getString(5),
                        re.getString(3),
                        re.getDouble(6),
                        re.getInt(7)

                ));
                System.out.println("count"+ re.getInt(7));
                couese_table.setItems(op);
            }


            FilteredList<courseTable> filteredList=new FilteredList<>(op, b->true);
            search.textProperty().addListener((observabl,oldvalue,newvalue)-> {
                filteredList.setPredicate(courseTable -> {
                    if(newvalue.isEmpty()||newvalue.isBlank()||newvalue==null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord=newvalue.toLowerCase();
                    if(courseTable.getDecription().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getDuration().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getCost_lesson().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getName().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getId().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getCost_lesson().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getVehicleType().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(courseTable.getNumber().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;

                    else
                        return false;

                });
            });
            SortedList<courseTable> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(couese_table.comparatorProperty());
            couese_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
}
