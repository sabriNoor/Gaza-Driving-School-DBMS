package com.example.dvs;
import com.almasb.fxgl.notification.Notification;
import com.example.dvs.DBconn;
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
import org.controlsfx.control.Notifications;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static org.controlsfx.control.Notifications.*;

public class instructor extends home implements Initializable {

    @FXML
    private TextField IFname_field1;

    @FXML
    private TextField ILicense_field;

    @FXML
    private TextField IMname_field1;

    @FXML
    private AnchorPane Ifname_pane31;

    @FXML
    private Button Instructor_DeleteBtn1;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private Button Instructor_UpdateBtn1;

    @FXML
    private Button Instructor_addBtn1;

    @FXML
    private Button Instructor_clearBtn1;

    @FXML
    private Button Instructor_searchBtn1;

    @FXML
    private AnchorPane Instructor_table_pane;

    @FXML
    private TextField Iphone_field1;

    @FXML
    private TextField Issn_field1;
    @FXML
    private TextField search;

    @FXML
    private TextField Lname_field1;

    @FXML
    private DatePicker date;

    @FXML
    private TableColumn<instructorTable, Date> date_colm;

    @FXML
    private VBox fill_inf_stu1;

    @FXML
    private TableColumn<instructorTable, String> fname_col;

    @FXML
    private AnchorPane fname_pane1;

    @FXML
    private AnchorPane OnUpdatePane;
    @FXML
    private AnchorPane fname_pane11;

    @FXML
    private AnchorPane fname_pane21;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane2111;

    @FXML
    private AnchorPane fname_pane2112;

    @FXML
    private AnchorPane fname_pane311;

    @FXML
    private AnchorPane fname_pane321;

    @FXML
    private TableColumn<instructorTable,String> gender_col;

    @FXML
    private AnchorPane instructor_information;

    @FXML
    private Label l11;

    @FXML
    private Label l21;

    @FXML
    private Label l31;

    @FXML
    private Label l41;

    @FXML
    private Label l51;

    @FXML
    private Label l61;

    @FXML
    private Label l71;

    @FXML
    private Label l711;

    @FXML
    private Label l81;

    @FXML
    private TableColumn<instructorTable,String> lic_colm;

    @FXML
    private TableColumn<instructorTable, String> lname;

    @FXML
    private TableColumn<instructorTable, String> mname_col;

    @FXML
    private TableColumn<instructorTable, String> Email_colm;

    @FXML
    private TableColumn<instructorTable, String>pass_col;

    @FXML
    private TableColumn<instructorTable, String>phine_colm;

    @FXML
    private TableColumn<instructorTable, Double> salary_colm;

    @FXML
    private TextField salary_txt;

    @FXML
    private TableColumn<instructorTable, String> ssn_colm;
    @FXML
    private TableView<instructorTable> Instructor_table;




    @FXML
    private Label phoneUpdatel,salarylbl,eamillbl,passwordlbl;

    @FXML
    private TextField passwordUpdate,salaryUpdate,phoneUpdate,EmailUpdate;

    @FXML
    private Button FinishUpdatePane;



   ObservableList<instructorTable> op=FXCollections.observableArrayList();
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


            if(Issn_field1.getText().equals("")||Issn_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill ISSN field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(IFname_field1.getText().equals("")||IFname_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill frist name","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(IMname_field1.getText().equals("")||IMname_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill mid name field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Lname_field1.getText().equals("")||Lname_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill last name field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( Iphone_field1.getText().equals("")||Iphone_field1.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill phone number field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(salary_txt.getText().equals("")||salary_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill salary field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(ILicense_field.getText().equals("")||ILicense_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill license num field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(genderT.equals(""))
                JOptionPane.showMessageDialog(null,"you should choose gender","ERROR",JOptionPane.ERROR_MESSAGE);
          /*  else if(date.getPromptText().equals(null))
                JOptionPane.showMessageDialog(null,"you should fill BD field","ERROR",JOptionPane.ERROR_MESSAGE);*/
            else {
                try {

                    String viewInstructor = "INSERT INTO driving_school.instructor (issn, fname, mname, lname, phonenumber, salary, licensenumber, gender, birth_date) VALUES ('"
                            + Issn_field1.getText() + "','" + IFname_field1.getText() + "','" + IMname_field1.getText() + "','"
                            + Lname_field1.getText() + "','" + Iphone_field1.getText() + "'," + salary_txt.getText() + ",'"
                            + ILicense_field.getText() + "','"+genderT+"', TO_DATE('" + date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "', 'DD-MM-YYYY'))";
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
        int selectedRow = Instructor_table.getSelectionModel().getSelectedIndex();
        instructorTable m =  Instructor_table.getItems().get(selectedRow);
        String s=m.getISSN();
        System.out.println("s="+s);

        try {
            String deleteInstructor = "delete from driving_school.instructor where issn ='"+s+"'" ;
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
    String issnUpdate;
    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = Instructor_table.getSelectionModel().getSelectedIndex();
        instructorTable m =  Instructor_table.getItems().get(selectedRow);
        issnUpdate=m.getISSN();
        Double salary=m.getSalary();
        String phone=m.getPhone();
        String email=m.getEmail();
        String password=m.getPassword();
        passwordUpdate.setText(password);
        salaryUpdate.setText(salary.toString());
        phoneUpdate.setText(phone);
        EmailUpdate.setText(email);
        OnUpdatePane.setVisible(true);
        //  System.out.println("s="+s);
    }

    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        Double salary=Double.parseDouble(salaryUpdate.getText());
        String phone=phoneUpdate.getText();
        String email=EmailUpdate.getText();
        String password=passwordUpdate.getText();
        String updateInstructor = "UPDATE driving_school.instructor SET salary = ?, password =? , email = ? , phonenumber = ? WHERE issn = ?";
        System.out.println("issn=" + issnUpdate.toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateInstructor);

            preparedStatement.setDouble(1, salary);

            if(email!=null && password!=null){
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,email);
                preparedStatement.setString(4,phone);
                preparedStatement.setString(5, issnUpdate.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");
                JOptionPane.showMessageDialog(null,"Updated Successfully");

            }
            else if(email==null && password!=null){

                JOptionPane.showMessageDialog(null,"cant Update password because there is no email");
            }
            else if(email==null && password==null)
            {
                preparedStatement.setString(2,null);
                preparedStatement.setString(3,null);
                preparedStatement.setString(4,phone);
                preparedStatement.setString(5, issnUpdate.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");
                JOptionPane.showMessageDialog(null,"Updated Successfully");
                OnUpdatePane.setVisible(false);

            }
            OnUpdatePane.setVisible(false);
            showData();
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }



    }
    @FXML
    void clear(ActionEvent event) throws IOException {
        Issn_field1.setText("");
        IFname_field1.setText("");
        IMname_field1.setText("");
        Lname_field1.setText("");
        Iphone_field1.setText("");
        ILicense_field.setText("");
        salary_txt.setText("");

        date.setValue(null);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Instructors Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/teacher.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);

        genderToggleGroup = new ToggleGroup();
        male.setToggleGroup(genderToggleGroup);
        female.setToggleGroup(genderToggleGroup);
        male.setOnAction(event -> handleRadioButtonAction(male));
        female.setOnAction(event -> handleRadioButtonAction(female));


        ssn_colm.setCellValueFactory(new PropertyValueFactory<>("ISSN"));
        fname_col.setCellValueFactory(new PropertyValueFactory<>("fname"));
        mname_col.setCellValueFactory(new PropertyValueFactory<>("mname"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lic_colm.setCellValueFactory(new PropertyValueFactory<>("licensenumber"));
        phine_colm.setCellValueFactory(new PropertyValueFactory<>("phone"));
        salary_colm.setCellValueFactory(new PropertyValueFactory<>("salary"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        date_colm.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        Email_colm.setCellValueFactory(new PropertyValueFactory<>("email"));
        pass_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        showData();



    }

        private void showData() {
        try {
            // Clear existing items from the table
           // Instructor_table.getItems().clear();
            op.clear();
            String viewInstructor = "select issn,fname,mname,lname,phonenumber,salary,licensenumber,gender,birth_date ,email,password from driving_school.instructor order by salary";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);


            while (re.next()) {
                op.add(new instructorTable(re.getString(1),re.getString(2),
                        re.getString(3),re.getString(4)
                        ,re.getString(5),re.getDouble(6),re.getString(7),
                        re.getString(8),re.getDate(9),re.getString(10),re.getString(11)));

                Instructor_table.setItems(op);

            }
            FilteredList<instructorTable> filteredList=new FilteredList<>(op,b->true);
            search.textProperty().addListener((observabl,oldvalue,newvalue)-> {
                filteredList.setPredicate(instructorTable -> {
                    if(newvalue.isEmpty()||newvalue.isBlank()||newvalue==null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord=newvalue.toLowerCase();
                    if(instructorTable.getISSN().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getGender().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getFname().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getLicensenumber().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getPhone().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getSalary().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getMname().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getLname().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(instructorTable.getBirthdate().toString().indexOf(searchWord)>-1)
                        return true;
                    else
                        return false;

                });
            });
            SortedList<instructorTable> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(Instructor_table.comparatorProperty());
            Instructor_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }

}