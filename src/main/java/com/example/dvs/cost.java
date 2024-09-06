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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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

public class cost extends home implements Initializable {

    @FXML
    private Button add,finishUp;

    @FXML
    private TableColumn<costTable, Double> amount_colm;

    @FXML
    private TextField amount_txt, amountUp;

    @FXML
    private Button baxk_but;

    @FXML
    private Button clear;

    @FXML
    private TableColumn<costTable, Integer> costID_colm;

    @FXML
    private TextField costID_txt,search;

    @FXML
    private ChoiceBox<String> costType_choice;

    @FXML
    private TableColumn<costTable, String> cost_type_colm;

    @FXML
    private Button delete;

    @FXML
    private TextArea des_area,desUp;

    @FXML
    private TableColumn<costTable, String> desc_colm;

    @FXML
    private ChoiceBox<String> inst_choice;

    @FXML
    private TableColumn<costTable, String> inst_colm;



    @FXML
    private Button update;

    @FXML
    private ChoiceBox<String> vehicle_choice;

    @FXML
    private TableColumn<costTable, String> vehicle_colm;
    @FXML
    private TableColumn<costTable, Date> date;
    @FXML
    private StackPane stack;
    @FXML
    private Pane cost_pane;
    @FXML
    private TableView<costTable> cost_tble;
    @FXML
    private AnchorPane side_inf, update_pane,searchPane;
    @FXML
     private DatePicker costDate;
    ObservableList<costTable> op = FXCollections.observableArrayList();
    Integer idCostUpdate;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Costs Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/cutting.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        System.out.println("Initializing cost controller...");

        initializeContent();
        System.out.println("Initializing cost controller...");

        costID_colm.setCellValueFactory(new PropertyValueFactory<>("costid"));
        cost_type_colm.setCellValueFactory(new PropertyValueFactory<>("costtype"));
        desc_colm.setCellValueFactory(new PropertyValueFactory<>("description"));
        amount_colm.setCellValueFactory(new PropertyValueFactory<>("amount"));
        vehicle_colm.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        inst_colm.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


        try{
            vehicle_choice.getItems().clear();
            String query = "SELECT licenseplate FROM driving_school.vehicle ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                vehicle_choice.getItems().add(resultSet.getString(1));
            }

            statement.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try{
            inst_choice.getItems().clear();
            String query = "SELECT issn FROM driving_school.instructor ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                inst_choice.getItems().add(resultSet.getString(1));
            }

            statement.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try{
            costType_choice.getItems().clear();
            costType_choice.getItems().add("instructor");
            costType_choice.getItems().add("vehicle");
            costType_choice.getItems().add("taxes");
            costType_choice.getItems().add("rent");
            costType_choice.getItems().add("other");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        showData();


    }

    private void initializeContent() {
        try {
            cost_tble.setVisible(true);
            side_inf.setVisible(true);
            baxk_but.setVisible(true);
            update_pane.setVisible(false);
            searchPane.setVisible(true);
            System.out.println("heyy");

            // Add any additional initialization logic here
        } catch (Exception e) {
            e.printStackTrace(); // Add proper error handling
        }
    }

    public void performBackInitialization() {
        initializeContent();
    }


    @FXML
    void back(javafx.event.ActionEvent event) throws IOException {
        cost_tble.setVisible(false);
        side_inf.setVisible(false);
        baxk_but.setVisible(false);
        update_pane.setVisible(false);
        searchPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("financial.fxml"));
        Parent lool = loader.load();

        // Get the controller instance
        Financial financialController = loader.getController();

        // Call the initialization method
        financialController.performBackInitialization();
        stack.getChildren().removeAll();
        stack.getChildren().setAll(lool);

    }

    public void showData() {
        try {
            op.clear();
            String viewCost = "SELECT costid, costtype, description, amount, vehicle_id, instructor_id, costdate FROM driving_school.cost";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewCost);

            while (re.next()) {
                op.add(new costTable(re.getInt(1), re.getString(2),
                        re.getString(3), re.getDouble(4),
                        re.getString(5), re.getString(6), re.getDate(7)));
            }

            FilteredList<costTable> filteredList = new FilteredList<>(op, b -> true);
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(costTable -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();
                    if (costTable.getCosttype().toLowerCase().contains(searchWord)) {
                        return true;
                    } else if (costTable.getDescription().toLowerCase().contains(searchWord)) {
                        return true;
                    } else if (costTable.getAmount().toString().contains(searchWord)) {
                        return true;
                    } else if (costTable.getVehicle_id() != null && costTable.getVehicle_id().toLowerCase().contains(searchWord)) {
                        return true;
                    } else if (costTable.getDate().toString().toLowerCase().contains(searchWord)) {
                        return true;
                    } else if (costTable.getInstructor_id() != null && costTable.getInstructor_id().contains(searchWord)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<costTable> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(cost_tble.comparatorProperty());
            cost_tble.setItems(sortedList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    void clear(ActionEvent event) throws IOException {

        amount_txt.setText("");
        des_area.setText("");
        costType_choice.setValue(null);
        vehicle_choice.setValue(null);
        inst_choice.setValue(null);
        costType_choice.setValue(null);
        costDate.setValue(null);
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = cost_tble.getSelectionModel().getSelectedIndex();
        costTable m =  cost_tble.getItems().get(selectedRow);
        Integer s=m.getCostid();
        System.out.println("s="+s);

        try {
            String deleteInstructor = "delete from driving_school.cost where costid ='"+s+"'" ;
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deleteInstructor);
            //Instructor_table.getItems().remove(selectedRow);
            op.remove(m);
            JOptionPane.showMessageDialog(null,"Delete Successfully the cost row with id = "+s+"   ","Information",JOptionPane.INFORMATION_MESSAGE);
            showData();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = cost_tble.getSelectionModel().getSelectedIndex();
        costTable m =  cost_tble.getItems().get(selectedRow);
        idCostUpdate=m.getCostid();
        Double amount=m.getAmount();
        String dis=m.getDescription();

        amountUp.setText(amount.toString());
        desUp.setText(dis);

        update_pane.setVisible(true);
        //  System.out.println("s="+s);
    }
    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        Double amount=Double.parseDouble(amountUp.getText());
        String dis=desUp.getText();

        String updateInstructor = "UPDATE driving_school.cost SET amount = ?, description =?  WHERE costid = ?";
        System.out.println("issn=" + idCostUpdate .toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateInstructor);

            preparedStatement.setDouble(1, amount);

            preparedStatement.setString(2,dis);
            preparedStatement.setInt(3, idCostUpdate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Update successful");

            JOptionPane.showMessageDialog(null,"Updated Successfully");
            showData();
            update_pane.setVisible(false);
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
            update_pane.setVisible(true);
        }

    }
    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            if (amount_txt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You should fill the Amount field", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (costType_choice.getValue() == null) {
                JOptionPane.showMessageDialog(null, "You should fill the Cost Type field", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (costDate.getValue() == null) {
                JOptionPane.showMessageDialog(null, "You should fill the Cost Date field", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    String insid=inst_choice.getValue()==null?null:"'"+inst_choice.getValue()+"'";
                    String vehid=vehicle_choice.getValue()==null  ?null:"'"+vehicle_choice.getValue()+"'";
                    String viewCost = "INSERT INTO driving_school.cost (costtype, description, amount, vehicle_id, instructor_id, costdate) VALUES ('"
                            + costType_choice.getValue() + "','" + des_area.getText() + "'," + amount_txt.getText() + ","
                            + vehid+ "," + insid
                            + ", TO_DATE('" + costDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "', 'DD-MM-YYYY'))";
                    Statement st = DBconn.con.createStatement();
                    st.executeUpdate(viewCost);
                } catch (Exception ex) {
                    System.out.println("Error inserting into cost table");
                    ex.printStackTrace();
                }
            }
            showData();
            System.out.println("Data added successfully");
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }
}




