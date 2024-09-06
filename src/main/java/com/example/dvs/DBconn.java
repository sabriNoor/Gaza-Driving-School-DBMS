package com.example.dvs;

import com.almasb.fxgl.net.Connection;
import org.controlsfx.control.Notifications;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DBconn {
    public static java.sql.Connection con;
    public static void initialize() throws SQLException {
        //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        try  {
            Class.forName("org.postgresql.Driver");


            if(con!=null)
                System.out.println("sucess");
            else
                System.out.println("faild");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        con=DriverManager.getConnection( "jdbc:postgresql://localhost:5432/postgres", "sabri", "123456");

    }
}