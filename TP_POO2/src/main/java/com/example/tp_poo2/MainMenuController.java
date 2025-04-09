package com.example.tp_poo2;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuController {
    public static void showMainMenu() {
        Stage mainMenuStage = new Stage();
        BorderPane mainLayout = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Fichier");
        MenuItem saveContacts = new MenuItem("Enregistrer Contacts");
        MenuItem reportItem = new MenuItem("Signaler Objets");
        MenuItem verifyMac = new MenuItem("Vérifier MAC");
        MenuItem exit = new MenuItem("Quitter");
        exit.setOnAction(event -> mainMenuStage.close());
        fileMenu.getItems().addAll(saveContacts, reportItem, verifyMac, exit);

        Menu categoriesMenu = new Menu("Catégories");
        categoriesMenu.setOnAction(event -> CategoriesController.showCategories());

        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().addAll(fileMenu, categoriesMenu, helpMenu);
        mainLayout.setTop(menuBar);

        mainMenuStage.setScene(new Scene(mainLayout, 600, 400));
        mainMenuStage.setTitle("Menu Principal");
        mainMenuStage.show();
    }
}
