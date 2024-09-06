package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;


public class student extends home implements Initializable {

    @FXML
    private Button DeleteBtn,addEnrollButt;

    @FXML
    private TextField Fname_field;
    @FXML
    private Pane StuPane;


    @FXML
    private RadioButton Fradio;

    @FXML
    private TextField Lname_field;

    @FXML
    private TextField Mname_field;

    @FXML
    private AnchorPane OnUpdatePane,p2;
    @FXML
    private RadioButton Mradio;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button add_student_address;

    @FXML
    private DatePicker birth_choose;

    @FXML
    private TextField passwordUpdate,salaryUpdate,phoneUpdate,EmailUpdate,search;

    @FXML
    private Button FinishUpdatePane;


    @FXML
    private Button clearBtn;

    @FXML
    private TextField email_field;

    @FXML
    private VBox fill_inf_stu;

    @FXML
    private AnchorPane fname_pane;

    @FXML
    private AnchorPane fname_pane1;

    @FXML
    private AnchorPane fname_pane2;

    @FXML
    private AnchorPane fname_pane21;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane3;

    @FXML
    private AnchorPane fname_pane31;

    @FXML
    private AnchorPane fname_pane32;


    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private Label l4;

    @FXML
    private Label l5;

    @FXML
    private Label l6;

    @FXML
    private Label l7;

    @FXML
    private Label l8;

    @FXML
    private TextField phone_field;

    @FXML
    private TableColumn<studentTable, String> phone_student;

    @FXML
    private TableColumn<studentTable, Date> sbirthd;

    @FXML
    private TableColumn<studentTable, String> passwordc;


    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<studentTable, String> semail;

    @FXML
    private TableColumn<studentTable, String> sfname;

    @FXML
    private TableColumn<studentTable, String> sgender;

    @FXML
    private TableColumn<studentTable, String> slname;

    @FXML
    private TableColumn<studentTable, String> smname;

    @FXML
    private TextField ssn_field;

    @FXML
    private TableColumn<studentTable, String> sssn;


    @FXML
    private AnchorPane student_information;

    @FXML
    private TableView<studentTable> student_table;

    @FXML
    private AnchorPane table_pane;

    @FXML
    private Button view_student_address;

    public static String selectedRowSssn;
    @FXML
    private RadioButton male,female;
    @FXML
    private StackPane pane;
    @FXML
    private Label sLabel;
    static String  studEnroll="";
    static boolean fE=false;
    @FXML
    void addEnroll(ActionEvent event) throws IOException {
        int selectedRow = student_table.getSelectionModel().getSelectedIndex();

        if (selectedRow >= 0) {
            studentTable m = student_table.getItems().get(selectedRow);


            try {
                // Verify FXMLLoader location and FXML hierarchy

                student_information.setVisible(false);
                table_pane.setVisible(false);
                search.setVisible(false);
                sLabel.setVisible(false);
                fE=true;
                studEnroll = m.getSSN();


                Parent lool = FXMLLoader.load(getClass().getResource("enroll.fxml"));
                pane.getChildren().removeAll();
                pane.getChildren().setAll(lool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Please select the row of the student you want to enroll",
                    "Information message",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    @FXML
    protected void open_view_address(ActionEvent e) throws IOException {
        // Load the FXML file
        try {
            int selectedRow = student_table.getSelectionModel().getSelectedIndex();

            // Check if there is a selected item
            if (selectedRow >= 0) {
                studentTable m = student_table.getItems().get(selectedRow);
                selectedRowSssn = m.getSSN();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStudentAddress.fxml"));
                Parent root = loader.load();

                // Create a new stage for the new window
                Stage newStage = new Stage();
                newStage.setTitle("Student Address"); // Set the title for the new window

                // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

                // Show the new stage
                newStage.show();
            } else {
                JOptionPane.showMessageDialog(null,"Please select the row of student you want to show address for him/her","Information message",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @FXML

    protected void add_address(ActionEvent e) throws IOException {
        int selectedRow = student_table.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            studentTable m = student_table.getItems().get(selectedRow);
            selectedRowSssn = m.getSSN();
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentAddressAdd.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("Student Address"); // Set the title for the new window

            // Set the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();
        }
        else
            JOptionPane.showMessageDialog(null,"Please select the row of student you want to add address for him/her","Information message",JOptionPane.INFORMATION_MESSAGE);


    }


    ObservableList<studentTable> op = FXCollections.observableArrayList();


    private ToggleGroup genderToggleGroup;
    String genderT =null;
    private void handleRadioButtonAction(RadioButton selectedRadioButton) {
        // Get the custom text for the selected radio button

        if(selectedRadioButton.equals(male))
            genderT="male";
        else if (selectedRadioButton.equals(female))
            genderT="female";
        else
            genderT="";
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            System.out.println("44");


            if(ssn_field.getText().equals("")||ssn_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill ISSN field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Fname_field.getText().equals("")||Fname_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill frist name","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Mname_field.getText().equals("")||Mname_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill mid name field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Lname_field.getText().equals("")||Lname_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill last name field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( phone_field.getText().equals("")||phone_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill phone number field ","ERROR",JOptionPane.ERROR_MESSAGE);

            else if(genderT.equals(""))
                JOptionPane.showMessageDialog(null,"you should choose gender","ERROR",JOptionPane.ERROR_MESSAGE);
            LocalDate birthDate = birth_choose.getValue();
            LocalDate currentDate = LocalDate.now();

            int age = Period.between(birthDate, currentDate).getYears();

            // Check if the age is 18 or above
            if (age < 18) {
                JOptionPane.showMessageDialog(null, "The student must be 18 years or older.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if age is below 18
            }
          /*  else if(date.getPromptText().equals(null))
                JOptionPane.showMessageDialog(null,"you should fill BD field","ERROR",JOptionPane.ERROR_MESSAGE);*/
            else {
                try {

                    String viewInstructor = "INSERT INTO driving_school.students (ssn, fname, mname, lname,gender, dateofbirth, phonenumber) VALUES ('"
                            + ssn_field.getText() + "','" + Fname_field.getText() + "','" + Mname_field.getText() + "','"
                            + Lname_field.getText() + "','"+genderT +"', TO_DATE('" + birth_choose.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "', 'DD-MM-YYYY'),'"+phone_field.getText()+"')" ;
                    Statement st = DBconn.con.createStatement();
                    st.executeUpdate(viewInstructor);
                } catch (Exception ex) {
                    System.out.println("ERRoR");
                    ex.printStackTrace();
                }
            }
            //  op = FXCollections.observableArrayList();
            showData();
            genderT="";
            System.out.println("555");
        }

        catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }

    }


    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = student_table.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            studentTable m = student_table.getItems().get(selectedRow);
            String s = m.getSSN();
            System.out.println("s=" + s);

            try {
                String deleteInstructor = "delete from driving_school.students where ssn ='" + s + "'";
                Statement st = DBconn.con.createStatement();
                st.executeUpdate(deleteInstructor);
                //Instructor_table.getItems().remove(selectedRow);
                op.remove(m);
                showData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
            JOptionPane.showMessageDialog(null,"Please select the row of student you want to delete","Information message",JOptionPane.INFORMATION_MESSAGE);

    }
    String issnUpdate;


    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = student_table.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            OnUpdatePane.setVisible(true);
            studentTable m = student_table.getItems().get(selectedRow);
            issnUpdate = m.getSSN();
            String phone = m.getPhone();
            String email = m.getEmail();
            String password = m.getPassword();
            passwordUpdate.setText(password);
            phoneUpdate.setText(phone);
            EmailUpdate.setText(email);
            OnUpdatePane.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(null,"Please select the row of student you want to update","Information message",JOptionPane.INFORMATION_MESSAGE);
        //  System.out.println("s="+s);
    }


    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
      //Double salary=Double.parseDouble(salaryUpdate.getText());
        String phone=phoneUpdate.getText();
        String email=EmailUpdate.getText();
        String password=passwordUpdate.getText();
        String updateInstructor = "UPDATE driving_school.students SET password =? , email = ? , phonenumber = ? WHERE ssn = ?";
        System.out.println("ssn=" + issnUpdate.toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateInstructor);


            if(email!=null && password!=null){
                preparedStatement.setString(1,password);
                preparedStatement.setString(2,email);
                preparedStatement.setString(3,phone);
                preparedStatement.setString(4, issnUpdate.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");
            }
            else if(email==null && password!=null){

                JOptionPane.showMessageDialog(null,"cant Update password because there is no email");
            }
            else if(email==null && password==null)
            {
                preparedStatement.setString(1,null);
                preparedStatement.setString(2,null);
                preparedStatement.setString(3,phone);
                preparedStatement.setString(4, issnUpdate.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");

            }

            JOptionPane.showMessageDialog(null,"Updated Successfully");
            showData();
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }
        OnUpdatePane.setVisible(false);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Students Information's");

        Image newImage = new Image(getClass().getResource("/com/example/dvs/student.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        genderToggleGroup = new ToggleGroup();
        male.setToggleGroup(genderToggleGroup);
        female.setToggleGroup(genderToggleGroup);
        male.setOnAction(event -> handleRadioButtonAction(male));
        female.setOnAction(event -> handleRadioButtonAction(female));
        student_information.setVisible(true);
        table_pane.setVisible(true);
        search.setVisible(true);
        sLabel.setVisible(true);
        fE=false;



        sssn.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        sfname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        smname.setCellValueFactory(new PropertyValueFactory<>("Mname"));
        slname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        sgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        sbirthd.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        phone_student.setCellValueFactory(new PropertyValueFactory<>("phone"));
        semail.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordc.setCellValueFactory(new PropertyValueFactory<>("password"));
        OnUpdatePane.setVisible(false);
        showData();

    }




    private void showData() {
        try {

            op.clear();
            String viewInstructor = "select ssn,fname,mname,lname,gender,dateofbirth ,phonenumber,email,password from driving_school.students ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);


            while (re.next()) {
                op.add(new studentTable(re.getString(1),re.getString(2),
                        re.getString(3),re.getString(4)
                        ,re.getString(5),re.getDate(6),re.getString(7),
                        re.getString(8),re.getString(9)));

                //student_table.setItems(op);


            }
            student_table.setItems(op);
            FilteredList<studentTable> filteredList = new FilteredList<>(op, b -> true);
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(studentTable -> {
                    if (newValue == null || newValue.trim().isEmpty()) {
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();

                    return studentTable.getSSN().toLowerCase().contains(searchWord) ||
                            studentTable.getGender().toLowerCase().contains(searchWord) ||
                            studentTable.getFname().toLowerCase().contains(searchWord) ||
                            //studentTable.getEmail().toLowerCase().contains(searchWord) ||
                            studentTable.getPhone().toLowerCase().contains(searchWord) ||

                            // studentTable.getPassword().toLowerCase().contains(searchWord) ||
                            studentTable.getMname().toLowerCase().contains(searchWord) ||
                            studentTable.getLname().toLowerCase().contains(searchWord) ||
                            studentTable.getBirthdate().toString().contains(searchWord);
                });
            });

            SortedList<studentTable> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(student_table.comparatorProperty());
            student_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
}