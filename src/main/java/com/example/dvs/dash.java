package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class dash extends home implements Initializable {

    @FXML
    private Label course_count;

    @FXML
    private AnchorPane enrol_count_home_pane;

    @FXML
    private AnchorPane enrol_count_home_pane11;

    @FXML
    private AnchorPane enrol_count_home_pane2;

    @FXML
    private AnchorPane enrol_count_home_pane21;

    @FXML
    private PieChart enroll_chart;

    @FXML
    private Label enroll_count;

    @FXML
    private AnchorPane enroll_count_home_pane1;

    @FXML
    private Label inst_count;

    @FXML
    private Label stud_count;

    @FXML
    private Label vehicle_count;
    @FXML
    private BarChart<String, Number> barChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home.st_lab.setText("Home DashBoard");
        Image newImage = new Image(getClass().getResource("/com/example/dvs/dashboard.png").toExternalForm());
        home.getStaticLogoImageView().setImage(newImage);
        try {


            String stuConut = "Select count(ssn) from driving_school.students ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(stuConut);
            if(re.next())
            {
                stud_count.setText(re.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {


            String InstConut = "Select count(issn) from driving_school.instructor ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(InstConut);
            if(re.next())
            {
                inst_count.setText(re.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {


            String VehConut = "Select count(licenseplate) from driving_school.vehicle ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(VehConut);
            if(re.next())
            {
                vehicle_count.setText(re.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {


            String EnrolConut = "Select count(enrollmentid) from driving_school.enrollment ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(EnrolConut);
            if(re.next())
            {
                enroll_count.setText(re.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {


            String CourseConut = "Select count(courseid) from driving_school.course ";
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(CourseConut);
            if(re.next())
            {
                course_count.setText(re.getString(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UpdChart();
        BarUpdate();

    }

    public void UpdChart()
    {
        try {
            int ActiveEnrol = getCount("SELECT count(enrollmentid) FROM driving_school.enrollment where status='active'");
            int CompEnrol = getCount("SELECT count(enrollmentid) FROM driving_school.enrollment where status='complete'");
            System.out.println(ActiveEnrol +"  "+CompEnrol);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

                    new PieChart.Data("Active Enrollment", ActiveEnrol),
                    new PieChart.Data("Completed Enrollment", CompEnrol)
            );

            enroll_chart.setData(pieChartData);
        } catch (Exception ex) {
            System.out.println("Error updating chart: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    private Integer getCount(String query) throws Exception {
        Statement st = DBconn.con.createStatement();
        ResultSet resultSet = st.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return 0;
    }
    public void BarUpdate()
    {
        try {
            // Fetch data from the database
            barChart.setTitle("Enrollment Count by Month");
            CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
            xAxis.setLabel("Month");
            NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
            yAxis.setLabel("Number of Students");
            ObservableList<XYChart.Series<String, Number>> barChartData = fetchDataForBarChart();

            // Clear existing data in the BarChart
            barChart.getData().clear();

            // Add the new data to the BarChart
            barChart.getData().addAll(barChartData);
        } catch (Exception ex) {
            System.out.println("Error updating bar chart: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private ObservableList<XYChart.Series<String, Number>> fetchDataForBarChart() throws Exception {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        // Fetch data from the database or use any other method to get the data
        // Example data (replace with actual data retrieval logic)
        for (int month = 1; month <= 12; month++) {
            int enrollmentCount = getCount("SELECT count(enrollmentid) FROM driving_school.enrollment WHERE EXTRACT(MONTH FROM enrollmentdate) = " + month);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Month " + month);

            series.getData().add(new XYChart.Data<>("Month " + month, enrollmentCount));

            barChartData.add(series);
        }

        return barChartData;
    }


}
