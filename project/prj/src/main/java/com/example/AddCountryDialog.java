package com.example;

import java.sql.Connection;
import java.sql.Statement;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddCountryDialog {
	private final Stage dialog;
	private final ObservableList<TableWindow.Country> countryList;
	private final Connection con;

	public AddCountryDialog(ObservableList<TableWindow.Country> countryList, Connection con) {
		this.countryList = countryList;
		this.con = con;

		dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Add Country");

		GridPane grid = new GridPane();
		grid.setPadding(new javafx.geometry.Insets(10));
		grid.setHgap(5);
		grid.setVgap(5);

		Label nameLabel = new Label("Country Name:");
		TextField nameField = new TextField();
		Label populationLabel = new Label("Population:");
		TextField populationField = new TextField();

		Button addButton = new Button("Add");
		addButton.setOnAction(e -> {
			addCountry(nameField.getText(), populationField.getText());
			dialog.close();
		});

		grid.add(nameLabel, 0, 0);
		grid.add(nameField, 1, 0);
		grid.add(populationLabel, 0, 1);
		grid.add(populationField, 1, 1);
		grid.add(addButton, 1, 2);

		Scene dialogScene = new Scene(grid, 300, 200);
		dialog.setScene(dialogScene);
	}

	public void show() {
		dialog.show();
	}

	private void addCountry(String name, String population) {
		try {
			int pop = Integer.parseInt(population);
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO country (Name, Population) VALUES ('" + name + "', " + pop + ")");
			countryList.add(new TableWindow.Country(name, pop));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
