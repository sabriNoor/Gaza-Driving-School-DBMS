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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class admin extends home implements Initializable {

    @FXML
    private TableColumn<adminTable, Date> DB_colm;

    @FXML
    private Button add;

    @FXML
    private Button clear;

    @FXML
    private PasswordField conf_pass_txt;

    @FXML
    private Button delete;

    @FXML
    private DatePicker dofb_txt;

    @FXML
    private TableColumn<adminTable, String> email_colm;

    @FXML
    private TextField email_txt;


    @FXML
    private TableColumn<adminTable, String> fname_colm;

    @FXML
    private TextField fname_txt;

    @FXML
    private TableColumn<adminTable, String> gender_colm;

    @FXML
    private TextField lanme_txt,emailUp,passwordUp,ConfpasswordUp;

    @FXML
    private TableColumn<adminTable, String> lname_colm;

    @FXML
    private RadioButton male;
    @FXML
    private Button finishUp;

    @FXML
    private PasswordField pass_txt;
    @FXML
    private AnchorPane update_pane;

    @FXML
    private TableColumn<adminTable, String> phone_num_colm;

    @FXML
    private TextField phone_txt;

    @FXML
    private Button search;

    @FXML
    private TextField ssnTxt,phoneUp;

    @FXML
    private TableColumn<adminTable, String> ssn_colm,password;

    @FXML
    private Button update;

    @FXML
    private RadioButton female;
    @FXML
    private TableView<adminTable> admin_tab;

    private ToggleGroup genderToggleGroup;
    String genderT =null;
    String ssnUp;
    ObservableList<adminTable> op= FXCollections.observableArrayList();
    private void handleRadioButtonAction(RadioButton selectedRadioButton) {
        // Get the custom text for the selected radio button
        if(selectedRadioButton.equals(male))
            genderT="male";
        else if (selectedRadioButton.equals(female))
            genderT="female";
        else
            genderT="";
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home.st_lab.setText("Admin Information");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/admin.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        genderToggleGroup = new ToggleGroup();
        male.setToggleGroup(genderToggleGroup);
        female.setToggleGroup(genderToggleGroup);
        male.setOnAction(event -> handleRadioButtonAction(male));
        female.setOnAction(event -> handleRadioButtonAction(female));
        update_pane.setVisible(false);


        ssn_colm.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        fname_colm.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lname_colm.setCellValueFactory(new PropertyValueFactory<>("lname"));
        phone_num_colm.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        gender_colm.setCellValueFactory(new PropertyValueFactory<>("gender"));
        DB_colm.setCellValueFactory(new PropertyValueFactory<>("dateofbirth"));
        email_colm.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        showData();

    }

    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            System.out.println("44");


            if(ssnTxt.getText().equals("")||ssnTxt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill SSN field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(fname_txt.getText().equals("")||fname_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill frist name","ERROR",JOptionPane.ERROR_MESSAGE);
             else if(lanme_txt.getText().equals("")||lanme_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill last name field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( phone_txt.getText().equals("")||phone_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill phone number field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(email_txt.getText().equals("")||email_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill email field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(genderT.equals(""))
                JOptionPane.showMessageDialog(null,"you should choose gender","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(dofb_txt.getValue().equals(null))
                JOptionPane.showMessageDialog(null,"you should fill BD field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(pass_txt.getText().equals("")||pass_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill password field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(conf_pass_txt.getText().equals("")||conf_pass_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill confirmed password field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(!conf_pass_txt.getText().equals(pass_txt.getText()))
                JOptionPane.showMessageDialog(null,"Sorry password not match!! ","ERROR",JOptionPane.ERROR_MESSAGE);

            else {
                try {

                    String viewAdmin = "INSERT INTO driving_school.admin (ssn,fname,lname,gender,dateofbirth,phonenumber,email,password) VALUES ('"
                            + ssnTxt.getText() + "','" + fname_txt.getText() + "','" + lanme_txt.getText() + "','"
                            + genderT + "', TO_DATE('" + dofb_txt.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "', 'DD-MM-YYYY')" + ",'" +phone_txt.getText() + "','"
                            + email_txt.getText() + "','"+pass_txt.getText()+"')";
                            Statement st = DBconn.con.createStatement();
                    st.executeUpdate(viewAdmin);
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

    public void showData()
    {
        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            //String ssn, String fname, String lname, String gender, Date dateofbirth, String phonenumber, String email, String password
            String viewInstructor = "select ssn,fname,lname,gender,dateofbirth,phonenumber,email,password from driving_school.admin order by ssn";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);


            while (re.next()) {
                op.add(new adminTable(re.getString(1),re.getString(2),
                        re.getString(3),re.getString(4)
                        ,re.getDate(5),re.getString(6),re.getString(7),
                        re.getString(8)));

                admin_tab.setItems(op);

            }

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }

    @FXML
    void clear(ActionEvent event) throws IOException {
        ssnTxt.setText("");
        fname_txt.setText("");
        lanme_txt.setText("");
        email_txt.setText("");
        phone_txt.setText("");
        pass_txt.setText("");
        conf_pass_txt.setText("");
        dofb_txt.setValue(null);

    }
    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = admin_tab.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            adminTable m = admin_tab.getItems().get(selectedRow);
            ssnUp = m.getSsn();
            String email = m.getEmail();
            String password = m.getPassword();
            passwordUp.setText(password);
            ConfpasswordUp.setText(password);
            emailUp.setText(email);
            phoneUp.setText(m.getPhonenumber());
            update_pane.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(null,"  Please select a row to update  "+"Error"+JOptionPane.ERROR_MESSAGE);

        //  System.out.println("s="+s);
    }

    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        String confPass=ConfpasswordUp.getText();
        String email=emailUp.getText();
        String password=passwordUp.getText();
        String phone=phoneUp.getText();
        String updateAdm = "UPDATE driving_school.admin SET  password =? , email = ?, phonenumber=?  WHERE ssn = ?";
        System.out.println("issn=" + ssnUp.toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateAdm);

           // preparedStatement.setDouble(1, salary);

            if(email!=null && password!=null && confPass!=null && phone!=null&& password.equals(confPass)){
                preparedStatement.setString(1,password);
                preparedStatement.setString(2,email);
                preparedStatement.setString(3,phone);
                preparedStatement.setString(4, ssnUp.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Update successful");
                showData();
                JOptionPane.showMessageDialog(null,"Updated Successfully");
            }

            else if(email==null || password==null||confPass==null|| phone==null){

                JOptionPane.showMessageDialog(null,"Please fill all the filed "+"Error"+JOptionPane.ERROR_MESSAGE);
            }
            else if(!password.equals(confPass))
            {
                JOptionPane.showMessageDialog(null,"Please make sure that  password = confirmed password"+"Error"+JOptionPane.ERROR_MESSAGE);

            }
            showData();
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }
        update_pane.setVisible(false);


    }
    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = admin_tab.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            adminTable m = admin_tab.getItems().get(selectedRow);
            String s = m.getSsn();
            System.out.println("s=" + s);

            try {
                String deleteInstructor = "delete from driving_school.admin where ssn ='" + s + "'";
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
            JOptionPane.showMessageDialog(null," Please select a row to delete "+"Error"+JOptionPane.ERROR_MESSAGE);


    }
}
