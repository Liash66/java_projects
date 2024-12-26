package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TableWindow {
    private Connection con;
    private TableView<Country> tableView;
    private ObservableList<Country> countryList;

    public TableWindow(Connection con) {
        this.con = con;
        this.countryList = FXCollections.observableArrayList();
        loadCountries();
    }

    public VBox createLayout() {
        tableView = new TableView<>();

        TableColumn<Country, String> nameColumn = new TableColumn<>("Country Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Country, Integer> populationColumn = new TableColumn<>("Population");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(populationColumn);

        tableView.setItems(countryList);

        Button filterButton = new Button("Filters");
        filterButton.setOnAction(e -> {
            FilterDialog filterDialog = new FilterDialog(this);
            filterDialog.show();
        });

        Button addButton = new Button("Add Country");
        addButton.setOnAction(e -> {
            AddCountryDialog addCountryDialog = new AddCountryDialog(countryList, con);
            addCountryDialog.show();
        });

        Button deleteButton = new Button("Delete Country");
        deleteButton.setOnAction(e -> deleteSelectedCountry(countryList));

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(filterButton, addButton, deleteButton);

        VBox vbox = new VBox(buttonBox, tableView);
        return vbox;
    }

    private void loadCountries() {
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

    public void filterCountries(int minPopulation, int maxPopulation) {
        ObservableList<Country> filteredList = FXCollections.observableArrayList();
        for (Country country : countryList) {
            if (country.getPopulation() >= minPopulation && country.getPopulation() <= maxPopulation) {
                filteredList.add(country);
            }
        }
        tableView.setItems(filteredList);
    }

    private void deleteSelectedCountry(ObservableList<Country> countryList) {
        Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM country WHERE Name = '" + selectedCountry.getName() + "'");
                countryList.remove(selectedCountry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Select a country to delete.");
        }
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