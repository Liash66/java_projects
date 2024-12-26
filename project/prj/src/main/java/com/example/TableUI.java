package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        // Создаем кнопки
        Button filterButton = new Button("Filters");
        filterButton.setOnAction(e -> showFilterDialog());

        Button addButton = new Button("Add Country");
        addButton.setOnAction(e -> showAddCountryDialog(countryList));

        Button deleteButton = new Button("Delete Country");
        deleteButton.setOnAction(e -> deleteSelectedCountry(countryList));

        // Создаем HBox для кнопок
        HBox buttonBox = new HBox(10); // 10 - это расстояние между кнопками
        buttonBox.getChildren().addAll(filterButton, addButton, deleteButton);

        // Создаем VBox для общего расположения
        VBox vbox = new VBox(buttonBox, tableView);
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
        dialog.show();
    }

    private void showAddCountryDialog(ObservableList<Country> countryList) {
        Stage dialog = new Stage();
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
            addCountry(nameField.getText(), populationField.getText(), countryList);
            dialog.close();
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(populationLabel, 0, 1);
        grid.add(populationField, 1, 1);
        grid.add(addButton, 1, 2);

        Scene dialogScene = new Scene(grid, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void addCountry(String name, String population, ObservableList<Country> countryList) {
        try {
            int pop = Integer.parseInt(population);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO country (Name, Population) VALUES ('" + name + "', " + pop + ")");
            countryList.add(new Country(name, pop)); // Добавляем новую страну в список
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedCountry(ObservableList<Country> countryList) {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM country WHERE Name = '" + selectedCountry.getName() + "'");
                countryList.remove(selectedCountry); // Удаляем страну из списка
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Можно добавить уведомление, если ничего не выбрано
            System.out.println("Please select a country to delete.");
        }
    }

    private void applyFilters(String minPopulation, String maxPopulation) {
        ObservableList<Country> filteredList = FXCollections.observableArrayList();
        try {
            int minPop = minPopulation.isEmpty() ? 0 : Integer.parseInt(minPopulation);
            int maxPop = maxPopulation.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPopulation);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name, Population FROM country WHERE Population >= " + minPop + " AND Population <= " + maxPop);
            
            while (rs.next()) {
                filteredList.add(new Country(rs.getString(1), rs.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.setItems(filteredList);
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