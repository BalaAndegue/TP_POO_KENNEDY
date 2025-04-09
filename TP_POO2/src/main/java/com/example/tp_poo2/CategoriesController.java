package com.example.tp_poo2;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoriesController {
    public static void showCategories() {
        Stage categoriesStage = new Stage();
        VBox categoriesLayout = new VBox(10);

        ListView<String> laptopsList = new ListView<>();
        ListView<String> phonesList = new ListView<>();
        ListView<String> tabletsList = new ListView<>();

        laptopsList.getItems().add("Laptop HP - Date: 01/04/2025");
        phonesList.getItems().add("Samsung Galaxy - Date: 15/03/2025");
        tabletsList.getItems().add("iPad - Date: 20/03/2025");

        categoriesLayout.getChildren().addAll(laptopsList, phonesList, tabletsList);

        categoriesStage.setScene(new Scene(categoriesLayout, 500, 300));
        categoriesStage.setTitle("Catégories");
        categoriesStage.show();
    }
}
