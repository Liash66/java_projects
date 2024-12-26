package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class App extends Application{
     
    public static void main(String[] args) {
        {
            // try
            // {
            //     Class.forName("com.mysql.cj.jdbc.Driver");
            //     Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Qwerty@2");
            //     Statement stmt=con.createStatement();
            //     ResultSet rs=stmt.executeQuery("select Name from country where Population<10000;");
            //     while(rs.next())
            //     {
            //         System.out.println(rs.getString(1));
            //     }
            //     con.close();
            // }
            // catch(Exception e)
            // {
            //     System.out.println(e);
            // }
        }
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) throws Exception {
         
        // Parent root = FXMLLoader.load(getClass().getResource("First.fxml")); 
        // Scene scene = new Scene(root);
         
        // stage.setScene(scene);
         
        // stage.setTitle("Authorization");
        // stage.setWidth(640);
        // stage.setHeight(640);
         
        // stage.show();

        stage.setTitle("Calculator");

        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Scene myScene = new Scene(rootNode, 640, 600);

        rootNode.add(new Label("User:"), 0, 0);
        TextField firstValue = new TextField();
        rootNode.add(firstValue, 1, 0);
        rootNode.add(new Label("Password:"), 0, 1);
        TextField secondValue = new TextField();
        rootNode.add(secondValue, 1, 1);
        Button aButton = new Button("Sign in");
        rootNode.add(aButton, 1, 2);
        GridPane.setHalignment(aButton, HPos.LEFT);
        TextField result = new TextField();
        result.setEditable(false);
        rootNode.add(result, 1, 3);

        aButton.setOnAction(e -> {
            String value1 = firstValue.getText();
            String value2 = secondValue.getText();
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", value1, value2);
                result.setText("success");
            }
            catch(Exception err)
            {
                result.setText("err");
            }
        });

        stage.setScene(myScene);

        stage.show();
    }

    static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}