package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class vehicleType implements Initializable {

    @FXML
    private TableColumn<vehicleTypeTable, Integer> NumVeh;

    @FXML
    private TableColumn<vehicleTypeTable, String> Vtypecolm;

    @FXML
    private Button add;

    @FXML
    private TableColumn<vehicleTypeTable, Integer> bumCou;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private TableColumn<vehicleTypeTable, String> idColm;
    @FXML
    private TableView<vehicleTypeTable> type_table;


    @FXML
    private TextField idTxt;

    @FXML
    private TextField typeTxt;
    private ObservableList<vehicleTypeTable> ob= FXCollections.observableArrayList();


    @FXML
    void add(ActionEvent event) {
        if(idTxt.getText().isBlank()||(idTxt.getText().equals(null))||typeTxt.getText().isBlank()||typeTxt.getText().isBlank())
            JOptionPane.showMessageDialog(null, " please make sure to fill both fields!   " , "Error", JOptionPane.ERROR_MESSAGE);
        else
        {
            String addType = "INSERT INTO driving_school.typevehicle (type,thetype) VALUES (?, ?)";
            try {

                try (PreparedStatement preparedStatement = DBconn.con.prepareStatement(addType)) {
                    preparedStatement.setString(1, idTxt.getText());
                    preparedStatement.setString(2, typeTxt.getText());


                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error inserting student: " + ex.getMessage());
                    ex.printStackTrace();
                }
                showData();
                JOptionPane.showMessageDialog(null, "Added successfully", "success", JOptionPane.INFORMATION_MESSAGE);

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }

    @FXML
    void clear(ActionEvent event) {
        idTxt.setText("");
        typeTxt.setText("");

    }

    @FXML
    void delete(ActionEvent event) {
        int selectedRow = type_table.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            vehicleTypeTable m = type_table.getItems().get(selectedRow);
            String s = m.getType();
            System.out.println("s=" + s);

            try {
                String deletelesson = "delete from driving_school.typevehicle where type ='" + s + "'";
                Statement st = DBconn.con.createStatement();
                st.executeUpdate(deletelesson);
                ob.remove(m);
                showData();
                JOptionPane.showMessageDialog(null, "Delete successfully vehicle type with id  " + s, "delete information", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
            JOptionPane.showMessageDialog(null, "please select a row to delete  " , "Error", JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColm.setCellValueFactory(new PropertyValueFactory<>("type"));
        Vtypecolm.setCellValueFactory(new PropertyValueFactory<>("thetype"));
        NumVeh.setCellValueFactory(new PropertyValueFactory<>("numvehicle"));
        bumCou.setCellValueFactory(new PropertyValueFactory<>("numCourse"));
        showData();
        createBarChart();
    }
    void showData() {
        try {
            // Clear existing items from the table
            type_table.getItems().clear();

            // Retrieve data from the database
            String query = "SELECT vt.type, vt.thetype, COUNT(DISTINCT c.courseid) AS numcourse, COUNT(DISTINCT v.licenseplate) AS numvehicle " +
                    "FROM driving_school.typevehicle vt " +
                    "LEFT JOIN driving_school.course c ON vt.type = c.vehicletype " +
                    "LEFT JOIN driving_school.vehicle v ON vt.type = v.vtype " +
                    "GROUP BY vt.type";


            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the TableView with data
            while (resultSet.next()) {
                type_table.getItems().add(new vehicleTypeTable(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
                ));
            }

            statement.close();
        } catch (Exception ex) {
            System.out.println("Error fetching vehicle types: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void createBarChart() {
        barChart.getData().clear();

        // Create series for numVehicle and numCourse
        XYChart.Series<String, Number> vehicleSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> courseSeries = new XYChart.Series<>();

        for (vehicleTypeTable item : type_table.getItems()) {
            vehicleSeries.getData().add(new XYChart.Data<>(item.getType(), item.getNumvehicle()));
            courseSeries.getData().add(new XYChart.Data<>(item.getType(), item.getNumCourse()));
        }

        // Set series names
        vehicleSeries.setName("Number of Vehicles");
        courseSeries.setName("Number of Courses");

        // Add series to the chart
        barChart.getData().addAll(vehicleSeries, courseSeries);
    }


}
