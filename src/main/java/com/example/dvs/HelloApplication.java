package com.example.dvs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
      /*  try{
            Parent root= FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }*/

    }

    public static void main(String[] args) throws SQLException {
        DBconn.initialize();
    //  launch();

      SplashScreen.showSplash(); // Show the splash screen
       launch(args); //
        DBconn.con.close();

    }
}