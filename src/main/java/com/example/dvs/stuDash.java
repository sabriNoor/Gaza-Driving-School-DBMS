package com.example.dvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ResourceBundle;

public class stuDash implements Initializable {

    @FXML
    private Label enrolCount;

    @FXML
    private Label pay;

    @FXML
    private Label time;

    @FXML
    private Label toPay;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String qEnrolcount="select count(enrollmentid)from driving_school.enrollment where studentid=?";
        String qEnroll="select enrollmentid from driving_school.enrollment where studentid=?";

        try {

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(qEnrolcount);
            pst.setString(1, userInfo.ssn);
            ResultSet re = pst.executeQuery();
            if(re.next())
            enrolCount.setText(String.valueOf(re.getInt(1)));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try {

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(qEnroll);
            pst.setString(1, userInfo.ssn);
            ResultSet re = pst.executeQuery();
            double amountPayment=0.0;
          while(re.next())
          {
              amountPayment+=Payamount(re.getInt(1));
          }
          pay.setText(String.valueOf(amountPayment));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try {

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(qEnroll);
            pst.setString(1, userInfo.ssn);
            ResultSet re = pst.executeQuery();
            double amountToPayment=0.0;
            while(re.next())
            {
                amountToPayment+=ToPayamount(re.getInt(1));
            }
            toPay.setText(String.valueOf(amountToPayment));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try {
            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(qEnroll);
            pst.setString(1, userInfo.ssn);
            ResultSet re = pst.executeQuery();
            Time timeLessonToday = null;
            while (re.next()) {
                timeLessonToday = timeLesson(re.getInt(1));
            }
            if (timeLessonToday == null)
                time.setText("  you don't have a lesson today!");
            else
                time.setText(timeLessonToday.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    double Payamount(int x)
    {
        try {
            String q="SELECT payment_amount FROM driving_school.v2 WHERE v2.enrollmentid=?";

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(q);
            pst.setInt(1, x);
            ResultSet re = pst.executeQuery();
          if(re.next())
              return re.getDouble(1);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return 0.0;
    }
    double ToPayamount(int x)
    {
        try {
            String q="SELECT amount_to_pay FROM driving_school.v2 WHERE v2.enrollmentid=?";

            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(q);
            pst.setInt(1, x);
            ResultSet re = pst.executeQuery();
            if(re.next())
                return re.getDouble(1);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return 0.0;
    }
    Time timeLesson(int x)
    {
        try {
            String q = "SELECT starttime FROM driving_school.lesson WHERE lessondate = CURRENT_DATE and enrollmentid=?";
            java.sql.PreparedStatement pst = DBconn.con.prepareStatement(q);
            pst.setInt(1, x);
            ResultSet re = pst.executeQuery();
            if(re.next())
                return re.getTime(1);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;

    }
}
