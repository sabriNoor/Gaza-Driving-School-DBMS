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
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class vehicle extends home implements Initializable {

    @FXML
    private TableColumn<vehicleTable, Double> CoverageAmount_colm_vehicle;

    @FXML
    private TableColumn<vehicleTable, String> CoverageType_colm_vehicle;

    @FXML
    private TableColumn<vehicleTable, Date> EffectiveDate_colm_vehicle;

    @FXML
    private TableColumn<vehicleTable, String> License_Plate_colm_vehicle;

    @FXML
    private TextField ModeVeh_txt,plolicyUP,covTypeUp,covAmountUp;

    @FXML
    private TableColumn<vehicleTable, String> Policy_Number_colm_vehicle;

    @FXML
    private Button add_vehicle,back,vtypebut;

    @FXML
    private TextField civarage_amount_txt;

    @FXML
    private Button clear_vehicle;

    @FXML
    private TextField cvrageTyoe_txt;

    @FXML
    private Button delete_veh;

    @FXML
    private DatePicker effective_date_veh_txt,DateUp;

    @FXML
    private TableColumn<vehicleTable, String> fule_colm_vehicle;

    @FXML
    private ChoiceBox<String > fule_type_choice_box;

    @FXML
    private TableColumn<vehicleTable, String> insyarnce_colm_vehicle;

    @FXML
    private TextField lice_txt_veh;

    @FXML
    private TableColumn<vehicleTable, String> make_colm_vehicle;

    @FXML
    private TextField make_txt;

    @FXML
    private TableColumn<vehicleTable, String> model_colm_vehicle;

    @FXML
    private TextField policyTxt;

    @FXML
    private Button search_vehicle;

    @FXML
    private ChoiceBox<String> transm_tyoe_choice_box,choiceVeh;

    @FXML
    private Button update_vehicle,finishUp;

    @FXML
    private AnchorPane veh_fillInform_pane,updatePane;

    @FXML
    private TableView<vehicleTable> vehicle_tab;

    @FXML
    private TableColumn<vehicleTable, Integer> year_colm_vehicle;
    @FXML
    private TableColumn<vehicleTable, String>trnsmisinType,vtype;

    @FXML
    private TextField year_veh_txt,search;
    ObservableList<vehicleTable> op= FXCollections.observableArrayList();
    String myUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        st_lab.setText("Vehicles Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/car.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        License_Plate_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("licenseplate"));
        make_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("make"));
        model_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("model"));
        year_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("year"));
        fule_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("fule_type"));
        trnsmisinType.setCellValueFactory(new PropertyValueFactory<>("transmission_type"));
        Policy_Number_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("insurancepolicynumber"));
        CoverageType_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("covarage_type"));
        CoverageAmount_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("coverageamount"));
        EffectiveDate_colm_vehicle.setCellValueFactory(new PropertyValueFactory<>("effectivedate"));
        vtype.setCellValueFactory(new PropertyValueFactory<>("vtype"));
        showData();
        veh_fillInform_pane.setVisible(true);
        updatePane.setVisible(false);
        try{
            transm_tyoe_choice_box.getItems().clear();

            transm_tyoe_choice_box.getItems().add("Automatic");
            transm_tyoe_choice_box.getItems().add("Manual");
            transm_tyoe_choice_box.getItems().add("other");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try{
            fule_type_choice_box.getItems().clear();
            fule_type_choice_box.getItems().add("Petrol");
            fule_type_choice_box.getItems().add("Diesel");
            fule_type_choice_box.getItems().add("Electric");
            fule_type_choice_box.getItems().add("Hybrid");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try{
        choiceVeh.getItems().clear();
        String query = "SELECT type FROM driving_school.typevehicle ";

        Statement statement = DBconn.con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            // Add each vehicleID value to the ChoiceBox
            choiceVeh.getItems().add(resultSet.getString(1));
        }

        statement.close();

    }
        catch (Exception ex)
    {
        ex.printStackTrace();
    }

    }

    private void showData()
    {
        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            String viewVehicle = "select licenseplate,make,model,year,fule_type,transmission_type,insurancepolicynumber,covarage_type,coverageamount ,effectivedate,vtype from driving_school.vehicle order by year";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(viewVehicle);


            while (re.next()) {
                op.add(new vehicleTable(re.getString(1),re.getString(2),
                        re.getString(3),re.getInt(4)
                        ,re.getString(5),re.getString(6),re.getString(7),
                        re.getString(8),re.getDouble(9),re.getDate(10),re.getString(11)));

               vehicle_tab.setItems(op);

            }
            FilteredList<vehicleTable> filteredList=new FilteredList<>(op, b->true);
            search.textProperty().addListener((observabl,oldvalue,newvalue)-> {
                filteredList.setPredicate(vehicleTable -> {
                    if(newvalue.isEmpty()||newvalue.isBlank()||newvalue==null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord=newvalue.toLowerCase();
                    if(vehicleTable.getLicenseplate().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getCovarage_type().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(String.valueOf(vehicleTable.getYear()).indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getFule_type().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getInsurancepolicynumber().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(String.valueOf(vehicleTable.getCoverageamount()).indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getMake().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getModel().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getEffectivedate().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getTransmission_type().toString().indexOf(searchWord)>-1)
                        return true;
                    else if(vehicleTable.getVtype().toString().indexOf(searchWord)>-1)
                        return true;
                    else
                        return false;

                });
            });
            SortedList<vehicleTable> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(vehicle_tab.comparatorProperty());
           vehicle_tab.setItems(sortedList);
        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedRow = vehicle_tab.getSelectionModel().getSelectedIndex();
        vehicleTable m =  vehicle_tab.getItems().get(selectedRow);
        String s=m.getLicenseplate();
        System.out.println("s="+s);


        try {
            String deleteVehicle = "delete from driving_school.vehicle where licenseplate='"+s+"'" ;
            Statement st = DBconn.con.createStatement();
            st.executeUpdate(deleteVehicle);
            //Instructor_table.getItems().remove(selectedRow);
            op.remove(m);
            showData();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
        void clear(ActionEvent event) throws IOException {
            lice_txt_veh.setText("");
            make_txt.setText("");
            ModeVeh_txt.setText("");
            year_veh_txt.setText("");
            policyTxt.setText("");
            cvrageTyoe_txt.setText("");
            civarage_amount_txt.setText("");

            fule_type_choice_box.setValue(null);
            transm_tyoe_choice_box.setValue(null);
            effective_date_veh_txt.setValue(null);
        }
    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            System.out.println("44");
            if(lice_txt_veh.getText().equals("")||lice_txt_veh.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill License Plate field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(make_txt.getText().equals("")||make_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill Make field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(ModeVeh_txt.getText().equals("")||ModeVeh_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill model_colm_vehicle field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(year_veh_txt.getText().equals("")||year_veh_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill Year field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( policyTxt.getText().equals("")||policyTxt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill Policy Number field ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(cvrageTyoe_txt.getText().equals("")||cvrageTyoe_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill Coverage Type field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(civarage_amount_txt.getText().equals("")||civarage_amount_txt.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill Coverage Amount field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if (fule_type_choice_box.getValue() == null ) {
                JOptionPane.showMessageDialog(null, "Please fill the Fule Type field.", "ERROR", JOptionPane.ERROR_MESSAGE); }
            else if (transm_tyoe_choice_box.getValue().equals(null) ) {
                JOptionPane.showMessageDialog(null, "Please fill the Transmission Type field.", "ERROR", JOptionPane.ERROR_MESSAGE); }
            else if (transm_tyoe_choice_box.getValue().equals(null) ) {
                JOptionPane.showMessageDialog(null, "Please fill the Vehicle Type field.", "ERROR", JOptionPane.ERROR_MESSAGE); }

            else {
                String viewVehicle = "INSERT INTO driving_school.vehicle (licenseplate, make, model, year, fule_type, transmission_type, insurancepolicynumber, covarage_type, coverageamount, effectivedate, vtype) VALUES (?, ?, ?, ?, driving_school.fueltypeenum(?),driving_school.transmissiontypeenum(?), ?, ?, ?, TO_DATE(?, 'DD-MM-YYYY'), ?)";

                try (PreparedStatement pstmt = DBconn.con.prepareStatement(viewVehicle)) {
                    pstmt.setString(1, lice_txt_veh.getText());
                    pstmt.setString(2, make_txt.getText());
                    pstmt.setString(3, ModeVeh_txt.getText());
                    pstmt.setInt(4, Integer.parseInt(year_veh_txt.getText()));
                    pstmt.setString(5, fule_type_choice_box.getValue());
                    pstmt.setString(6, transm_tyoe_choice_box.getValue());
                    pstmt.setString(7, policyTxt.getText());
                    pstmt.setString(8, cvrageTyoe_txt.getText());
                    pstmt.setDouble(9, Double.parseDouble(civarage_amount_txt.getText()));
                    pstmt.setString(10, effective_date_veh_txt.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    pstmt.setString(11, choiceVeh.getValue());


                    // Execute the query
                    pstmt.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ERRoR");
                    ex.printStackTrace();
                }
            }
            //  op = FXCollections.observableArrayList();
            showData();
            System.out.println("555");
        }

        catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }

    }
    @FXML
    void update(ActionEvent event) throws IOException {


        int selectedRow = vehicle_tab.getSelectionModel().getSelectedIndex();
        if(selectedRow>0) {
            vehicleTable m = vehicle_tab.getItems().get(selectedRow);
            myUpdate = m.getLicenseplate();
            String policy = m.getInsurancepolicynumber();
            String type = m.getCovarage_type();
            String amount= String.valueOf(m.getCoverageamount());
          //  Date date=m.getEffectivedate();
            plolicyUP.setText(policy);
            covTypeUp.setText(type);
            covAmountUp.setText(amount);
            java.util.Date utilDate = new java.util.Date(m.getEffectivedate().getTime());

            Instant instant = utilDate.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            DateUp.setValue(localDate);
            veh_fillInform_pane.setVisible(false);
            updatePane.setVisible(true);

        }
        else
        {
            JOptionPane.showMessageDialog(null,"Plerase select the row to update","Sorry",JOptionPane.INFORMATION_MESSAGE);
        }
        //  System.out.println("s="+s);
    }

    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        Double amount=Double.parseDouble(covAmountUp.getText());
        String type=covTypeUp.getText();
        String policy=plolicyUP.getText();
        LocalDate date=DateUp.getValue();
        String updateVeh = "UPDATE driving_school.vehicle SET insurancepolicynumber = ?, coverageamount =? , covarage_type = ? , effectivedate = ? WHERE licenseplate = ?";
        System.out.println("issn=" + myUpdate.toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateVeh);

            preparedStatement.setString(1, policy);

            if(amount!=null && type!=null&&policy!=null && date!=null){
                preparedStatement.setString(1, policy);
                preparedStatement.setDouble(2,amount);
                preparedStatement.setString(3,type);
                preparedStatement.setDate(4, java.sql.Date.valueOf(date));
                preparedStatement.setString(5, myUpdate.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                JOptionPane.showMessageDialog(null, "Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                showData();
                veh_fillInform_pane.setVisible(true);
                updatePane.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "Updated Failed. Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);

            }

            //JOptionPane.showMessageDialog(null,"Updated Successfully");

        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }
        veh_fillInform_pane.setVisible(true);
        updatePane.setVisible(false);


    }
    @FXML
    void back(ActionEvent event) throws IOException {
        veh_fillInform_pane.setVisible(true);
        updatePane.setVisible(false);
    }

    @FXML
    void vtabeltype(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicletype.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("Vehicle type"); // Set the title for the new window

            // Set the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


}
