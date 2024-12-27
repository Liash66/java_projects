package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AuthWindow {
	private TextField firstValue;
	private PasswordField secondValue;
	private Text result;
	private Stage stage;

	public AuthWindow(Stage stage) {
		this.stage = stage;
	}

	public GridPane createLayout() {
		GridPane rootNode = new GridPane();
		rootNode.setPadding(new Insets(15));
		rootNode.setHgap(5);
		rootNode.setVgap(5);
		rootNode.setAlignment(Pos.CENTER);

		addInputFields(rootNode);
		addSignInButton(rootNode);

		return rootNode;
	}

	private void addInputFields(GridPane rootNode) {
		rootNode.add(new Label("User:"), 0, 0);
		firstValue = new TextField();
		rootNode.add(firstValue, 1, 0);

		rootNode.add(new Label("Password:"), 0, 1);
		secondValue = new PasswordField();
		rootNode.add(secondValue, 1, 1);

		result = new Text();
		rootNode.add(result, 1, 2);
	}

	private void addSignInButton(GridPane rootNode) {
		Button aButton = new Button("Sign in");
		rootNode.add(aButton, 1, 3);
		GridPane.setHalignment(aButton, HPos.LEFT);

		aButton.setOnAction(e -> handleSignIn());
	}

	private void handleSignIn() {
		String user = firstValue.getText();
		String password = secondValue.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", user, password);
			showTable(con);
		} catch (Exception err) {
			firstValue.clear();
			secondValue.clear();
			result.setText("Incorrect login or password");
		}
	}

	private void showTable(Connection con) {
		TableWindow tableUI = new TableWindow(con);
		Scene tableScene = new Scene(tableUI.createLayout(), 400, 400);
		stage.setScene(tableScene);
		stage.setTitle("Table");
	}
}