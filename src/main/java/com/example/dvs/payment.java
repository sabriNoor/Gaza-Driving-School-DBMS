package com.example.dvs;

import com.example.dvs.Financial;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import javax.swing.text.html.ImageView;
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

public class payment extends home implements Initializable {

    @FXML
    private ChoiceBox<Integer>payment_enrollmentID_field;
    @FXML
    private TextField Amount_field,search,enrollSearch;


    @FXML
    private Button DeleteBtn1_payment;

    @FXML
    private VBox PAY_fill2;

    @FXML
    private AnchorPane Payment_information1,OnUpdatePane;

    @FXML
    private Button UpdateBtn1_payment;

    @FXML
    private Button addBtn1_payment;

    @FXML
    private Label amountl;

    @FXML
    private Button clearBtn1_payment,finishSearch;

    @FXML
    private DatePicker date,dateSearch;

    @FXML
    private Label datel;

    @FXML
    private Label enrolll;

    @FXML
    private AnchorPane fname_pane1,searchPane;

    @FXML
    private AnchorPane fname_pane12;

    @FXML
    private AnchorPane fname_pane21;

    @FXML
    private AnchorPane fname_pane211;

    @FXML
    private AnchorPane fname_pane2113;

    @FXML
    private AnchorPane fname_pane322;

    @FXML
    private Label methodl;

    @FXML
    private TextField paymentID_field;

    @FXML
    private TextField paymentMethod_field;

    @FXML
    private TextField paymentTransationID_field,paymeth,Amount;


    @FXML
    private TableView<paymentTable> payment_tbl;

    @FXML
    private TableColumn<paymentTable, Integer> paymentIDcol;
    @FXML
    private TableColumn<paymentTable, Double> amountcol;
    @FXML
    private TableColumn<paymentTable, Date> datecol;
    @FXML
    private TableColumn<paymentTable, String> methodcol;
    @FXML
    private TableColumn<paymentTable, String> transactioncol,stu_name;
    @FXML
    private TableColumn<paymentTable, Integer> enrollmentidcol;


    @FXML
    private Label paymentl;

    @FXML
    private Button searchBtn1_payment;
    @FXML
    private Button back_btn,searchBut;

    @FXML
    private StackPane stack;

    @FXML
    private Label transactionl;




    private void initializeContent() {
        try {
            payment_tbl.setVisible(true);
            Payment_information1.setVisible(true);
            back_btn.setVisible(true);
            search.setVisible(true);
            OnUpdatePane.setVisible(false);
            searchPane.setVisible(false);
            // Add any additional initialization logic here
        } catch (Exception e) {
            e.printStackTrace(); // Add proper error handling
        }
    }
    ObservableList<paymentTable> op= FXCollections.observableArrayList();
    public void performBackInitialization() {
        initializeContent();
    }
    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            System.out.println("44");


            if(paymentID_field.getText().equals("")||paymentID_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill paymentID_field  ","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(Amount_field.getText().equals("")||Amount_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill date","ERROR",JOptionPane.ERROR_MESSAGE);
            else if(date.getValue().equals(null))
                JOptionPane.showMessageDialog(null,"you should fill paymentMethod_field","ERROR",JOptionPane.ERROR_MESSAGE);
            else if( paymentMethod_field.getText().equals("")||paymentMethod_field.getText().isBlank())
                JOptionPane.showMessageDialog(null,"you should fill paymentMethod_field ","ERROR",JOptionPane.ERROR_MESSAGE);
            if(paymentTransationID_field.getText().equals("")||paymentTransationID_field.getText().isBlank()){
                paymentTransationID_field.setText(null);
            }
            else {
                try {

                    String viewInstructor = "INSERT INTO driving_school.payment ( paymentid, enrollment_id, amount, paymentmethod, transactionid,paymentdate ) VALUES ('"
                            + paymentID_field.getText() + "','" + payment_enrollmentID_field.getValue() + "','" + Amount_field.getText() + "','"
                            + paymentMethod_field.getText() + "','" + paymentTransationID_field.getText()
                            +"', TO_DATE('" + date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "', 'DD-MM-YYYY'))";
                    Statement st = DBconn.con.createStatement();
                    st.executeUpdate(viewInstructor);
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
    void delete(ActionEvent event) throws IOException {
        int selectedRow = payment_tbl.getSelectionModel().getSelectedIndex();
        paymentTable m =  payment_tbl.getItems().get(selectedRow);
        Integer s=m.getPaymentid();
        System.out.println("s="+s);

        try {
            String deleteInstructor = "delete from driving_school.payment where paymentid ='"+s+"'" ;
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
    Integer issnUpdate;
    @FXML
    void update(ActionEvent event) throws IOException {

        int selectedRow = payment_tbl.getSelectionModel().getSelectedIndex();
        paymentTable m =  payment_tbl.getItems().get(selectedRow);
        issnUpdate=m.getPaymentid();
        Double amount=m.getAmount();
        String method=m.getPaymentmethod();

        Amount.setText(amount.toString());
        paymeth.setText(method);

        OnUpdatePane.setVisible(true);
        //  System.out.println("s="+s);
    }

    @FXML
    void FinishUpdate(ActionEvent event) throws IOException {
        Double amount=Double.parseDouble(Amount.getText());
        String meth=paymeth.getText();

        String updateInstructor = "UPDATE driving_school.payment SET amount = ?, paymentmethod =?  WHERE paymentid = ?";
        System.out.println("issn=" + issnUpdate.toString());

        try {
            PreparedStatement preparedStatement = DBconn.con.prepareStatement(updateInstructor);

            preparedStatement.setDouble(1, amount);

            preparedStatement.setString(2,meth);
            preparedStatement.setInt(3, issnUpdate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Update successful");


            JOptionPane.showMessageDialog(null,"Updated Successfully");
            showData();
        } catch (SQLException e) {
            System.out.println("Error updating instructor: " + e.getMessage());
            e.printStackTrace();
        }
        OnUpdatePane.setVisible(false);


    }
    @FXML
    void clear(ActionEvent event) throws IOException {
        paymentID_field.setText("");
        Amount_field.setText("");
        paymentMethod_field.setText("");
        paymentTransationID_field.setText("");
        payment_enrollmentID_field.setValue(null);


        date.setValue(null);
    }


    @FXML
    void back(javafx.event.ActionEvent event) throws IOException {
        payment_tbl.setVisible(false);
        Payment_information1.setVisible(false);
        back_btn.setVisible(false);
        search.setVisible(false);
        OnUpdatePane.setVisible(false);
        System.out.println("Loading financial.fxml...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("financial.fxml"));
        Parent lool = loader.load();

        // Get the controller instance
        Financial financialController = loader.getController();

        // Call the initialization method
        financialController.performBackInitialization();
        stack.getChildren().removeAll();
        stack.getChildren().setAll(lool);
        System.out.println("Back navigation completed.");



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeContent();
        st_lab.setText("Payments Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/payment-method.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);

        try{
            payment_enrollmentID_field.getItems().clear();
            String query = "SELECT enrollmentid FROM driving_school.enrollment ";

            Statement statement = DBconn.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Add each vehicleID value to the ChoiceBox
                payment_enrollmentID_field.getItems().add(resultSet.getInt("enrollmentid"));
            }

            statement.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        paymentIDcol.setCellValueFactory(new PropertyValueFactory<>("paymentid"));
        amountcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("paymentdate"));
        methodcol.setCellValueFactory(new PropertyValueFactory<>("paymentmethod"));
        transactioncol.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
        enrollmentidcol.setCellValueFactory(new PropertyValueFactory<>("enrollment_id"));
        stu_name.setCellValueFactory(new PropertyValueFactory<>("stu_name"));

        showData();



    }

    private void showData() {
        try {
            // Clear existing items from the table
            // Instructor_table.getItems().clear();
            op.clear();
            String viewpay = "SELECT paymentid, enrollment_id, amount, paymentdate, paymentmethod, transactionid,fname,mname,lname FROM driving_school.payment as p,driving_school.students as s,driving_school.enrollment as e where s.ssn=e.studentid and e.enrollmentid=p.enrollment_id ";
            Statement st = DBconn.con.createStatement();
            System.out.println(24);
            ResultSet re = st.executeQuery(viewpay);

            while (re.next()) {
                String stuName=re.getString(7)+" "+re.getString(8)+" "+re.getString(9);
                System.out.println(stuName);
                op.add(new paymentTable(re.getInt(1), re.getInt(2),
                        re.getDouble(3), re.getDate(4),
                        re.getString(5), re.getString(6),stuName));
            }

            FilteredList<paymentTable> filteredList=new FilteredList<>(op, b->true);
            search.textProperty().addListener((observabl,oldvalue,newvalue)-> {
                filteredList.setPredicate(paymentTable -> {
                    if(newvalue.isEmpty()||newvalue.isBlank()||newvalue==null) {
                        System.out.println("55");
                        return true;
                    }
                    String searchWord=newvalue.toLowerCase();
                    if(paymentTable.getPaymentmethod().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(paymentTable.getTransactionid().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(paymentTable.getAmount().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(paymentTable.getEnrollment_id().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(paymentTable.getPaymentdate().toString().toLowerCase().indexOf(searchWord)>-1)
                        return true;
                    else if(paymentTable.getPaymentid().toString().indexOf(searchWord)>-1)
                        return true;
                    else
                        return false;

                });
            });
            SortedList<paymentTable> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(payment_tbl.comparatorProperty());
            payment_tbl.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
    @FXML
    void search(ActionEvent event) throws IOException {
        searchPane.setVisible(true);
        enrollSearch.setText("");
        dateSearch.setValue(null);


    }
    @FXML
    void show(ActionEvent event) throws IOException {
        showData();
        searchPane.setVisible(false);
    }
    @FXML
    void finishSearch(ActionEvent event) throws IOException {
        try {
            String q1 = "SELECT paymentid, enrollment_id, amount, paymentdate, paymentmethod, transactionid, fname, mname, lname FROM driving_school.payment as p, driving_school.students as s, driving_school.enrollment as e " +
                    "WHERE s.ssn = e.studentid AND e.enrollmentid = p.enrollment_id AND e.enrollmentid = ? AND p.paymentdate = ?";
            String q2 = "SELECT paymentid, enrollment_id, amount, paymentdate, paymentmethod, transactionid, fname, mname, lname FROM driving_school.payment as p, driving_school.students as s, driving_school.enrollment as e " +
                    "WHERE s.ssn = e.studentid AND e.enrollmentid = p.enrollment_id AND e.enrollmentid = ?";
            String q3 = "SELECT paymentid, enrollment_id, amount, paymentdate, paymentmethod, transactionid, fname, mname, lname FROM driving_school.payment as p, driving_school.students as s, driving_school.enrollment as e " +
                    "WHERE s.ssn = e.studentid AND e.enrollmentid = p.enrollment_id AND e.enrollmentid = ? AND p.paymentdate = ?";

            LocalDate dateS = dateSearch.getValue();
            String enrolS = enrollSearch.getText();

            if (dateS != null && enrolS != null) {
                PreparedStatement preparedStatement = DBconn.con.prepareStatement(q1);
                preparedStatement.setInt(1, Integer.parseInt(enrollSearch.getText()));
                System.out.println(Integer.parseInt(enrollSearch.getText()));
                preparedStatement.setDate(2, java.sql.Date.valueOf(dateS));
                ResultSet resultSet = preparedStatement.executeQuery();
                updateTable(resultSet);
                preparedStatement.close();
                System.out.println("Search successful");
            } else if (dateS == null && enrolS != null) {
                PreparedStatement preparedStatement = DBconn.con.prepareStatement(q2);
                preparedStatement.setInt(1, Integer.parseInt(enrollSearch.getText()));
                ResultSet resultSet = preparedStatement.executeQuery();
                updateTable(resultSet);
                preparedStatement.close();
                System.out.println("Search successful");
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, you should fill enrollment id to successfully perform the operation", "Sorry", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateTable(ResultSet resultSet) {
        try {
            // Clear existing items from the table
            op.clear();

            while (resultSet.next()) {
                String stuName = resultSet.getString(7) + " " + resultSet.getString(8) + " " + resultSet.getString(9);
                System.out.println(stuName);
                op.add(new paymentTable(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getDouble(3), resultSet.getDate(4),
                        resultSet.getString(5), resultSet.getString(6), stuName));
            }

            // Refresh the table view
            payment_tbl.setItems(op);
        } catch (SQLException ex) {
            System.out.println("Error updating table: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



}
