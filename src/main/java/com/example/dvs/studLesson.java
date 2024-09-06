package com.example.dvs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

public class studLesson implements Initializable {

    @FXML
    private TableColumn<enrollStudTable, Date> EnrolDate;

    @FXML
    private Button Viewlesson,ViewPayments;

    @FXML
    private Button back;

    @FXML
    private TableColumn<enrollStudTable, String> courseName;

    @FXML
    private TableColumn<enrollStudTable,Integer> enrolColm;

    @FXML
    private TableView<enrollStudTable> enrolTable;
    @FXML
    private TableView<lessonStudTable> lessonTable;

    @FXML
    private TableColumn<enrollStudTable, String> instColm;

    @FXML
    private TableColumn<lessonStudTable, Date> lessonDate;

    @FXML
    private TableColumn<lessonStudTable, Integer> lessonId;

    @FXML
    private TableColumn<lessonStudTable, Time> lessonStartTime;

    @FXML
    private TableColumn<stuPay, Integer> PymentIdCol;
    @FXML
    private TableColumn<stuPay, Date> Paydate;

    @FXML
    private TableColumn<stuPay, String> Paymethod;

    @FXML
    private TableColumn<stuPay, Double> amount;
    @FXML
    private TableColumn<enrollStudTable, Double>costPerLesson;
    @FXML
    private TableColumn<stuPay, String> transPay;
    @FXML
    private Label title;
    @FXML
    private ImageView img;


    @FXML
    private Button backPay;
    @FXML
    private AnchorPane payPane;
    @FXML
    private Label total_payment;

    @FXML
    private Label total_to_pay;

    @FXML
    private TableView<stuPay> payTable;

    @FXML
    private TableColumn<enrollStudTable, String> status;
    private ObservableList<enrollStudTable> ob= FXCollections.observableArrayList();
    private ObservableList<lessonStudTable> op= FXCollections.observableArrayList();
    private ObservableList<stuPay> oSP= FXCollections.observableArrayList();
    Integer enrollidLes=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("Enrollment");
        img.setImage(new Image(getClass().getResource("/com/example/dvs/enrollment (3).png").toExternalForm()));

        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        lessonStartTime.setCellValueFactory(new PropertyValueFactory<>("starttime"));
        lessonId.setCellValueFactory(new PropertyValueFactory<>("lessonid"));
        lessonDate.setCellValueFactory(new PropertyValueFactory<>("lessondate"));
        instColm.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        enrolColm.setCellValueFactory(new PropertyValueFactory<>("enrollmentid"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        EnrolDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentdate"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        PymentIdCol.setCellValueFactory(new PropertyValueFactory<>("payId"));
        Paydate.setCellValueFactory(new PropertyValueFactory<>("date"));
        Paymethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        transPay.setCellValueFactory(new PropertyValueFactory<>("tansId"));
        costPerLesson.setCellValueFactory(new PropertyValueFactory<>("costPerLesson"));


        lessonTable.setVisible(false);
        enrolTable.setVisible(true);
        back.setVisible(false);
        Viewlesson.setVisible(true);
        ViewPayments.setVisible(true);
        backPay.setVisible(false);
        payTable.setVisible(false);
        payPane.setVisible(false);
        showenroll();


    }


    void showenroll() {
        try {
            ob.clear();
            String qid = "select enrollmentid, status, c.coursename, CONCAT(i.fname, ' ', i.lname) AS instructor_names, enrollmentdate,c.costperlesson " +
                    "from driving_school.enrollment as e, driving_school.instructor as i, driving_school.course as c " +
                    "where e.instructorid = i.issn and e.courseid = c.courseid and studentid = ?";

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(qid);
            pst.setString(1, userInfo.ssn);

            ResultSet re = pst.executeQuery();

            while (re.next()) {
                ob.add(new enrollStudTable(
                        re.getInt(1),
                        re.getString(2),
                        re.getString(3),
                        re.getString(4),
                        re.getDate(5),
                        re.getDouble(6)
                ));
            }
            enrolTable.setItems(ob);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //Viewlesson
    @FXML
    void Viewlesson(ActionEvent event) throws IOException {

        int selectedRow = enrolTable.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            title.setText("Lesson ");
            img.setImage(new Image(getClass().getResource("/com/example/dvs/lesson (1).png").toExternalForm()));
            enrollStudTable m = enrolTable.getItems().get(selectedRow);
            enrollidLes= m.getEnrollmentid();
            System.out.println("enrollmentid=" + enrollidLes);
            enrolTable.setVisible(false);
            Viewlesson.setVisible(false);
            ViewPayments.setVisible(false);
            lessonTable.setVisible(true);
            back.setVisible(true);
            showLesson();

        }
        else {
            JOptionPane.showMessageDialog(null, "Please select enrollment row to show the associated lesson to it", "Information", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLesson() {
        try {
            op.clear();

            // Select all lessons along with their enrollment details
            String viewLessons = "SELECT l.lessonid AS lesson_ids, l.lessondate AS lesson_dates, l.starttime AS start_times FROM  driving_school.lesson as l WHERE enrollmentid = ? order by lessonid desc ";

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(viewLessons);
            pst.setInt(1, enrollidLes);

            ResultSet lessonResultSet = pst.executeQuery();

            while (lessonResultSet.next()) {
                op.add(new lessonStudTable(
                        lessonResultSet.getInt(1),
                        lessonResultSet.getDate(2),
                        lessonResultSet.getTime(3)
                ));
            }

            lessonTable.setItems(op);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
    @FXML
    void ViewPayments(ActionEvent event) throws IOException {
        int selectedRow = enrolTable.getSelectionModel().getSelectedIndex();
        if(selectedRow>=0) {
            title.setText("Payment ");
            img.setImage(new Image(getClass().getResource("/com/example/dvs/payment-method.png").toExternalForm()));
           // img.setImage(new Image("payment-method.png"));
            enrollStudTable m = enrolTable.getItems().get(selectedRow);
            enrollidLes= m.getEnrollmentid();
            System.out.println("enrollmentid=" + enrollidLes);
            enrolTable.setVisible(false);
            Viewlesson.setVisible(false);
            ViewPayments.setVisible(false);
            lessonTable.setVisible(false);
            back.setVisible(false);
            backPay.setVisible(true);
            payTable.setVisible(true);
            payPane.setVisible(true);
            showPay();
            try {
                String q2 = "SELECT payment_amount, amount_to_pay FROM driving_school.v2 WHERE v2.enrollmentid=?";
                java.sql.PreparedStatement pst = DBconn.con.prepareStatement(q2);
                pst.setInt(1, enrollidLes);
                ResultSet rs = pst.executeQuery();

                // Check if there are any rows in the result set
                if (rs.next()) {
                    // Move the cursor to the first row
                    total_payment.setText(String.valueOf(rs.getDouble("payment_amount")));
                    total_to_pay.setText(String.valueOf(rs.getDouble("amount_to_pay")));
                } else {
                    // Handle the case where no rows are returned
                    System.out.println("No data found for enrollmentid: " + enrollidLes);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        else {
            JOptionPane.showMessageDialog(null, "Please select enrollment row to show the associated payments to it", "Information", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        title.setText("Enrollment");
        img.setImage(new Image(getClass().getResource("/com/example/dvs/enrollment (3).png").toExternalForm()));

     //   img.setImage(new Image("enrollment (3).png"));
        enrolTable.setVisible(true);
        Viewlesson.setVisible(true);
        ViewPayments.setVisible(true);
        lessonTable.setVisible(false);
        back.setVisible(false);
        showenroll();

    }
    @FXML
    void backPay(ActionEvent event) throws IOException {
        title.setText("Enrollment");
        img.setImage(new Image(getClass().getResource("/com/example/dvs/enrollment (3).png").toExternalForm()));
        enrolTable.setVisible(true);
        Viewlesson.setVisible(true);
        ViewPayments.setVisible(true);
        backPay.setVisible(false);
        payTable.setVisible(false);
        payPane.setVisible(false);
        showenroll();
    }
    void showPay()
    {
        //oSP

        try {
            oSP.clear();

            // Select all lessons along with their enrollment details
            String viewPay = "SELECT paymentid, amount, paymentdate, paymentmethod, transactionid FROM driving_school.payment as p where p.enrollment_id=?";

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(viewPay);
            pst.setInt(1, enrollidLes);

            ResultSet lessonResultSet = pst.executeQuery();

            while (lessonResultSet.next()) {
                oSP.add(new stuPay(
                        lessonResultSet.getInt(1),
                        lessonResultSet.getDouble(2),
                        lessonResultSet.getDate(3),
                        lessonResultSet.getString(4),
                        lessonResultSet.getString(5)

                ));
            }

            payTable.setItems(oSP);

        } catch (Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }
    }
}
