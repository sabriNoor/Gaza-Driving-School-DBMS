package com.example.dvs;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InsviewAdd implements Initializable {

    @FXML
    private TableColumn<AddressTable, String> city_col;

    @FXML
    private FontAwesomeIcon close;

    @FXML
    private TableColumn<AddressTable, String> house_col;

    @FXML
    private TableColumn<AddressTable, String> street_col;

    @FXML
    private Label title;

    @FXML
    private TableView<AddressTable> view_student_Address_table;
    ObservableList<AddressTable> op = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(11111);
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        street_col.setCellValueFactory(new PropertyValueFactory<>("street"));
        house_col.setCellValueFactory(new PropertyValueFactory<>("housebuilding"));

        showData();
        System.out.println(6363);

    }

    private void showData() {
        try {
            System.out.println(888);
            // Clear existing items from the table
            view_student_Address_table.getItems().clear();

            String viewInstructor = "select stu_ssn,city,street,housenumber from driving_school.stu_address ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewInstructor);
            System.out.println("bbb");
            while (re.next()) {
                System.out.println("sss");
                System.out.println(instructorMyStudent.selectedRowMystudentSSN);
                if (instructorMyStudent.selectedRowMystudentSSN.equals(re.getString(1))) {
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



}
