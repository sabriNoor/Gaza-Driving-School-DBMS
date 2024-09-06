package com.example.dvs;

import com.almasb.fxgl.app.MainWindow;
import javafx.application.Platform;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class Financial extends home implements Initializable {

    @FXML
    private TableColumn<viewFin, Double> amount_to_pay_colm;

    @FXML
    private PieChart chart;

    @FXML
    private Button cost_but,payandcost;

    @FXML
    private TableColumn<viewFin, Double> cost_per_lesson;

    @FXML
    private TableColumn<viewFin, Integer> enroll_id;

    @FXML
    private TableColumn<viewFin, Integer> lesson_count;

    @FXML
    private TableColumn<viewFin, Double> payment_amount_colm;
    @FXML
    private TableColumn<viewFin, String>stud_name;

    @FXML
    private Button payment_but,compRep;

    @FXML
    private StackPane stackO;

    @FXML
    private TableView<viewFin> view_table;
    @FXML
    private Pane financial_pane;
    @FXML
    private TextField search;
    private ObservableList<viewFin> ob= FXCollections.observableArrayList();
  //  private home homeInstance;

    // @Override
   /* public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            view_table.setVisible(true);
            payment_but.setVisible(true);
            cost_but.setVisible(true);
            chart.setVisible(true);
        } catch (Exception e) {
        }

    }*/


    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeContent();
        st_lab.setText("Financial Information's");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/report.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);

        enroll_id.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        lesson_count.setCellValueFactory(new PropertyValueFactory<>("lessonCount"));
        cost_per_lesson.setCellValueFactory(new PropertyValueFactory<>("costPerLesson"));
        payment_amount_colm.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        amount_to_pay_colm.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
        stud_name.setCellValueFactory(new PropertyValueFactory<>("stdu_name"));
        showData();
        updateChart();


    }


    private void initializeContent() {
        try {
           // financial_pane.setVisible(true);
            view_table.setVisible(true);
            payment_but.setVisible(true);
            cost_but.setVisible(true);
            chart.setVisible(true);
            // Add any additional initialization logic here
        } catch (Exception e) {
            e.printStackTrace(); // Add proper error handling
        }
    }

    public void performBackInitialization() {
        initializeContent();
    }


    @FXML
    void cost(javafx.event.ActionEvent event) throws IOException {
       view_table.setVisible(false);
        payment_but.setVisible(false);
        cost_but.setVisible(false);
        chart.setVisible(false);
        Parent lool = FXMLLoader.load(getClass().getResource("cost.fxml"));
        stackO.getChildren().removeAll();
        stackO.getChildren().setAll(lool);


    }
    @FXML
    void payment(javafx.event.ActionEvent event) throws IOException {
        view_table.setVisible(false);
        payment_but.setVisible(false);
        cost_but.setVisible(false);
        chart.setVisible(false);
        Parent lool = FXMLLoader.load(getClass().getResource("payment.fxml"));
        stackO.getChildren().removeAll();
        stackO.getChildren().setAll(lool);
    }

    private void showData() {
        try {
            ob.clear();

            // Replace the SQL query with your actual query
            String view = "SELECT enrollmentid, lesson_count, cost_per_lesson, payment_amount, " +
                    "cost_per_lesson * lesson_count::numeric - payment_amount AS amount_to_pay " +
                    "FROM driving_school.v1";

            Statement st = DBconn.con.createStatement();
            ResultSet resultSet = st.executeQuery(view);

            while (resultSet.next()) {
                // Assuming your viewFin class has a constructor that takes these parameters
                viewFin fin = new viewFin(
                        resultSet.getInt("enrollmentid"),
                        resultSet.getInt("lesson_count"),
                        resultSet.getDouble("cost_per_lesson"),
                        resultSet.getDouble("payment_amount"),
                        resultSet.getDouble("amount_to_pay"),
                        getStudentName(resultSet.getInt("enrollmentid")) // Implement this method
                );

                ob.add(fin);
            }

            enroll_id.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
            lesson_count.setCellValueFactory(new PropertyValueFactory<>("lessonCount"));
            cost_per_lesson.setCellValueFactory(new PropertyValueFactory<>("costPerLesson"));
            payment_amount_colm.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
            amount_to_pay_colm.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
            stud_name.setCellValueFactory(new PropertyValueFactory<>("stdu_name"));

            FilteredList<viewFin> filteredList = new FilteredList<>(ob, b -> true);

            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(viewFin -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        System.out.println("55");
                        return true;
                    }

                    String searchWord = newValue.toLowerCase();
                    return viewFin.getStdu_name().toLowerCase().contains(searchWord);
                });
            });

            SortedList<viewFin> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(view_table.comparatorProperty());
            view_table.setItems(sortedList);

        } catch (Exception ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }


    private String getStudentName(int enrollmentId) {
        // Implement this method to fetch and return student name based on enrollmentId
        // Use the student table and join with enrollment table
        // You will need another SQL query here
        // Example: SELECT CONCAT(fname, ' ', lname) AS student_name FROM student WHERE studentid = (SELECT studentid FROM enrollment WHERE enrollmentid = ?)
     String name="";
       try {


           String q = "Select fname,lname from driving_school.enrollment as e,driving_school.students as s where e.studentid=s.ssn and e.enrollmentid=" + enrollmentId;
           Statement st = DBconn.con.createStatement();
           ResultSet re = st.executeQuery(q);
           if(re.next())
           {
               name=re.getString(1)+ " " + re.getString(2);

           }
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }

        return name; // Placeholder, replace with actual logic
    }
    private void updateChart() {
        try {
            double paymentSum = getSumOfAmounts("SELECT SUM(amount) FROM driving_school.payment WHERE EXTRACT(MONTH FROM paymentdate) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM paymentdate) = EXTRACT(YEAR FROM CURRENT_DATE)");
            double costSum = getSumOfAmounts("SELECT SUM(amount) FROM driving_school.cost WHERE EXTRACT(MONTH FROM costdate) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM costdate) = EXTRACT(YEAR FROM CURRENT_DATE)");
            System.out.println(paymentSum +"  "+costSum);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

                    new PieChart.Data("Payment", paymentSum),
                    new PieChart.Data("Cost", costSum)
            );

            chart.setData(pieChartData);
        } catch (Exception ex) {
            System.out.println("Error updating chart: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private double getSumOfAmounts(String query) throws Exception {
        Statement st = DBconn.con.createStatement();
        ResultSet resultSet = st.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }

        return 0.0;
    }

    @FXML
    void report(ActionEvent event) throws IOException
    {
        InputStream inputStream;
        JasperReport jr;
        JasperDesign jd;
        JasperPrint jp;
        OutputStream outputStream;
        try {
            inputStream=new FileInputStream(new File("FinRep.jrxml"));
            jd= JRXmlLoader.load(inputStream);
            jr= JasperCompileManager.compileReport(jd);
            jp= JasperFillManager.fillReport(jr,null,DBconn.con);

            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Active Enrollment Financial Report");
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewer.setVisible(true);

            inputStream.close();
            // DBconn.con.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
    @FXML
    void compRep(ActionEvent event) throws IOException
    {
        InputStream inputStream;
        JasperReport jr;
        JasperDesign jd;
        JasperPrint jp;
        OutputStream outputStream;
        try {
            inputStream=new FileInputStream(new File("CompFin.jrxml"));
            jd= JRXmlLoader.load(inputStream);
            jr= JasperCompileManager.compileReport(jd);
            jp= JasperFillManager.fillReport(jr,null,DBconn.con);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Complete Enrollment Financial Report");
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewer.setVisible(true);

            inputStream.close();
            // DBconn.con.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
    //payandcost
    @FXML
    void payandcost(ActionEvent event) throws IOException
    {
        InputStream inputStream;
        JasperReport jr;
        JasperDesign jd;
        JasperPrint jp;
        OutputStream outputStream;
        try {
            inputStream=new FileInputStream(new File("PayAndCostReport.jrxml"));
            jd= JRXmlLoader.load(inputStream);
            jr= JasperCompileManager.compileReport(jd);
            jp= JasperFillManager.fillReport(jr,null,DBconn.con);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Payments and Cost Financial Report");
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewer.setVisible(true);

            inputStream.close();
            // DBconn.con.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }
}


