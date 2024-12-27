package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FilterDialog {
	private final Stage dialog;
	private final TableWindow tableWindow;

	public FilterDialog(TableWindow tableWindow) {
		this.tableWindow = tableWindow;
		dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Filters");

		GridPane grid = new GridPane();
		grid.setPadding(new javafx.geometry.Insets(10));
		grid.setHgap(5);
		grid.setVgap(5);

		Label minLabel = new Label("Min Population:");
		TextField minField = new TextField();
		Label maxLabel = new Label("Max Population:");
		TextField maxField = new TextField();

		Button applyButton = new Button("Apply");
		applyButton.setOnAction(e -> {
			applyFilters(minField.getText(), maxField.getText());
			dialog.close();
		});

		grid.add(minLabel, 0, 0);
		grid.add(minField, 1, 0);
		grid.add(maxLabel, 0, 1);
		grid.add(maxField, 1, 1);
		grid.add(applyButton, 1, 2);

		Scene dialogScene = new Scene(grid, 300, 200);
		dialog.setScene(dialogScene);
	}

	public void show() {
		dialog.show();
	}

	private void applyFilters(String minPopulation, String maxPopulation) {
		try {
			int minPop = minPopulation.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(minPopulation);
			int maxPop = maxPopulation.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPopulation);
			tableWindow.filterCountries(minPop, maxPop);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Enter valid numbers.");
		}
	}
}