package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TableUI {
    private Connection con;
    private TableView<Country> tableView;

    public TableUI(Connection con) {
			this.con = con;
    }

    public VBox createLayout() {
			tableView = new TableView<>();

			TableColumn<Country, String> nameColumn = new TableColumn<>("Country Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Country, Integer> populationColumn = new TableColumn<>("Population");
			populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));

			tableView.getColumns().add(nameColumn);
			tableView.getColumns().add(populationColumn);

			ObservableList<Country> countryList = FXCollections.observableArrayList();
			loadCountries(countryList);

			tableView.setItems(countryList);

			Button filterButton = new Button("Filter");
			filterButton.setOnAction(e -> showFilterDialog());

			VBox vbox = new VBox(filterButton, tableView);
			return vbox;
    }

    private void loadCountries(ObservableList<Country> countryList) {
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Name, Population FROM country");
				while (rs.next()) {
					countryList.add(new Country(rs.getString(1), rs.getInt(2)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    private void showFilterDialog() {
			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.setTitle("Enter Population Filters");

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
				try {
					int minPopulation = Integer.parseInt(minField.getText());
					int maxPopulation = Integer.parseInt(maxField.getText());
					filterCountries(minPopulation, maxPopulation);
					dialog.close();
				} catch (NumberFormatException ex) {
					showAlert("Invalid input", "Please enter valid integers for population.");
				}
			});

			grid.add(minLabel, 0, 0);
			grid.add(minField, 1, 0);
			grid.add(maxLabel, 0, 1);
			grid.add(maxField, 1, 1);
			grid.add(applyButton, 1, 2);

			Scene dialogScene = new Scene(grid, 300, 200);
			dialog.setScene(dialogScene);
			dialog.show();
    }

    private void filterCountries(int minPopulation, int maxPopulation) {
			ObservableList<Country> filteredList = FXCollections.observableArrayList();

			try {
				Statement stmt = con.createStatement();
				// Применяем фильтр к запросу
				ResultSet rs = stmt.executeQuery("SELECT Name, Population FROM country WHERE Population >= " + minPopulation + " AND Population <= " + maxPopulation);
				while (rs.next()) {
						filteredList.add(new Country(rs.getString(1), rs.getInt(2)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			tableView.setItems(filteredList);
    }

    private void showAlert(String title, String message) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
    }

    public static class Country {
			private final String name;
			private final int population;

			public Country(String name, int population) {
				this.name = name;
				this.population = population;
			}

			public String getName() {
				return name;
			}

			public int getPopulation() {
				return population;
			}
    }
}