package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application{
     
    public static void main(String[] args) {
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Qwerty@2");
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select Name from country where Population<10000;");
                while(rs.next())
                {
                    System.out.println(rs.getString(1));
                }
                con.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) throws Exception {
         
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml")); 
        Scene scene = new Scene(root);
         
        stage.setScene(scene);
         
        stage.setTitle("Hello world");
        stage.setWidth(250);
        stage.setHeight(200);
         
        stage.show();
    }
}