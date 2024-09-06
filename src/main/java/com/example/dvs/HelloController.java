package com.example.dvs;



import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventObject;

public class HelloController {

    @FXML
    private ImageView book;

    @FXML
    private ImageView car;

    @FXML
    private FontAwesomeIcon close;

    @FXML
    private Button closeBtn;

    @FXML
    private ImageView driver;

    @FXML
    private Hyperlink forgetPass;

    @FXML
    private FontAwesomeIcon key;

    @FXML
    private AnchorPane leftpane;

    @FXML
    private Button loginBtn;

    @FXML
    private Button loginBtn1;

    @FXML
    private ImageView mainlogo;

    @FXML
    private BorderPane pane;

    @FXML
    private PasswordField passwordTB;

    @FXML
    private Label titlelbl;

    @FXML
    private FontAwesomeIcon user;

    @FXML
    private Label userlb;

    @FXML
    private TextField usernameTB;

    @FXML
    private FontAwesomeIcon users;
    private EventObject actionEvent;

    @FXML
    void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void forgetpassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetpassword.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }
    @FXML
    void signup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signUp.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }



    @FXML
    void login_click(ActionEvent event) {
        System.out.println("heelo");
        String email=usernameTB.getText();
        String password=passwordTB.getText();

        int t=1;
        try {
            String LogInSQL = "select email,password,issn,fname,mname,lname,gender from driving_school.instructor " ;
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(LogInSQL);
            while(re.next()) {
                String emaildb = re.getString(1);
                String passworddb = re.getString(2);



                System.out.println("email=" + emaildb);
                if (email.equals(emaildb) && password.equals(passworddb)) {

                    userInfo.issn = re.getString(3);
                    userInfo.fname = re.getString(4);
                    userInfo.mname = re.getString(5);
                    userInfo.lname = re.getString(6);
                    userInfo.gender=re.getString(7);

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructorUser.fxml"));
                        Parent root = loader.load();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    t=0;
                }

            }

        }
        catch(Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }


        try {
            String LogInSQL = "select email,password,ssn,gender from driving_school.students " ;
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(LogInSQL);
            while(re.next()) {
                String emaildb = re.getString(1);
                String passworddb = re.getString(2);
                String ssn = re.getString(3);
                String gender = re.getString(4);

                System.out.println("email=" + emaildb);
                if (email.equals(emaildb) && password.equals(passworddb)) {
                    try {
                        userInfo.ssn=ssn;
                        userInfo.email=emaildb;
                        userInfo.gender=gender;


                        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentHome.fxml"));
                        Parent root = loader.load();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    t=0;
                }

            }

        }
        catch(Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }


        try {
            String LogInSQL = "select email,password,ssn,fname,lname,gender from driving_school.admin " ;
            Statement st = DBconn.con.createStatement();
            ResultSet re = st.executeQuery(LogInSQL);
            while(re.next()) {
                String emaildb = re.getString(1);
                String passworddb = re.getString(2);
                String ssn=re.getString(3);
                String fname= re.getString(4);
                String lname= re.getString(5);
                String gender= re.getString(6);


                System.out.println("email=" + emaildb);
                if (email.equals(emaildb) && password.equals(passworddb)) {
                    try {
                        userInfo.fname=fname;userInfo.lname=lname;
                        userInfo.ssn=ssn;
                        userInfo.gender=gender;
                        System.out.println("email in side"+userInfo.email);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                        Parent root = loader.load();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception appropriately (e.g., show an error message)
                    }
                    t=0;
                }

            }

        }
        catch(Exception ex) {
            System.out.println("ERROR");
            System.out.println(ex.toString());
        }


        if(t==1){
            JOptionPane.showMessageDialog(null,"Couldnt login Make sure from your password & email");
        }
    }

}


