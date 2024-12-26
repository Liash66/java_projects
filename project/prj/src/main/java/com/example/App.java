package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application{
     
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Authorization");

        AuthUI authUI = new AuthUI();
        Scene myScene = new Scene(authUI.createLayout(), 640, 600);

        stage.setScene(myScene);
        stage.show();
    }
}