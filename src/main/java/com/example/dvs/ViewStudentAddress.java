package com.example.dvs;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewStudentAddress  implements Initializable {

    @FXML
    private AnchorPane Veiw_strudent_page;

    @FXML
    private Button addBtn1_add;

    @FXML
    private TableColumn<AddressTable, String> city_col;

    @FXML
    private FontAwesomeIcon close_viewAddress_page;

    @FXML
    private Button close_viewAddress_page_BTN;

    @FXML
    private Button deleteBtn_address;

    @FXML
    private TableColumn<AddressTable, String> house_col;

    @FXML
    private ImageView img1;

    @FXML
    private TableColumn<AddressTable, String> street_col;

    @FXML
    private Label title;

    @FXML
    private TableView<AddressTable> view_student_Address_table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        street_col.setCellValueFactory(new PropertyValueFactory<>("street"));
        house_col.setCellValueFactory(new PropertyValueFactory<>("housebuilding"));

        showData();

    }
    ObservableList<AddressTable> op= FXCollections.observableArrayList();

    private void showData() {
        try {
            // Clear existing items from the table
            view_student_Address_table.getItems().clear();

            String viewInstructor = "select stu_ssn,city,street,housenumber from driving_school.stu_address ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);

            while (re.next()) {
                if(student.selectedRowSssn.equals(re.getString(1))){
                    op.add(new AddressTable(re.getString(2), re.getString(3),
                            re.getString(4)));

                    view_student_Address_table.setItems(op);
                }
            }


        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }

    @FXML
    void deleteAddress(ActionEvent event) throws IOException {
        int selectedRow = view_student_Address_table.getSelectionModel().getSelectedIndex();
        AddressTable m =  view_student_Address_table.getItems().get(selectedRow);
        String s=m.getHousebuilding();
        System.out.println("s="+s);

        try {
            String deleteInstructor = "delete from driving_school.stu_address where housenumber ='"+s+"'"+" and stu_ssn ='"+student.selectedRowSssn+"'" ;
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deleteInstructor);
            view_student_Address_table.getItems().remove(selectedRow);
            // showData();
        }
        catch (Exception ex){

        }


    }



}