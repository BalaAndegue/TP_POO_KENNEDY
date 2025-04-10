
package com.example.tp_poo2;

import com.example.tp_poo2.models.StolenObjet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.tp_poo2.service.DatabaseService.getAllStolenObjects;

public class MainMenuController {

    public static void showMainMenu() {
        Stage stage = new Stage();
        BorderPane mainLayout = new BorderPane();

        // Header
        HBox header = createHeader(stage);
        mainLayout.setTop(header);

        // Body with ListView
        VBox body = createBody();
        mainLayout.setCenter(body);

        // Scene and Stage setup
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Application de Déclaration d'Objets Volés");
        stage.show();
    }

    private static HBox createHeader(Stage stage) {
        // Barre de recherche
        TextField searchBar = new TextField();
        searchBar.setPromptText("Rechercher par marque...");
        searchBar.setStyle("-fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 5;");
        searchBar.setPrefWidth(400);

        // Icône de l'application
        ImageView appIcon = new ImageView(new Image(MainMenuController.class.getResource("/com/example/tp_poo2/image/logos.png").toExternalForm())); // Remplacez "resources/logo.png" par le chemin de votre image
        appIcon.setFitHeight(30);
        appIcon.setFitWidth(30);

        // Bouton "+"
        Button addButton = new Button("+");
        addButton.setStyle("-fx-font-size: 16; -fx-background-color: #5bc0de; -fx-text-fill: white; -fx-background-radius: 15;");
        addButton.setOnAction(event -> addNewItem());

        // Bouton pour afficher le menu latéral
        Button contactButton = new Button("Contact");
        contactButton.setStyle("-fx-font-size: 16; -fx-background-color: #5cb85c; -fx-text-fill: white; -fx-background-radius: 15;");
        contactButton.setOnAction(event -> showSideMenu(stage));


        // Créer des espaces flexibles pour aligner correctement les éléments
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        // Header layout
        HBox header = new HBox( appIcon,leftSpacer, searchBar,rightSpacer, addButton, contactButton);
        header.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: #f5f5f5;");
        return header;
    }

    private static VBox createBody() {
        // Utilisation d'ObservableList
        ObservableList<String> data = loadItemsFromDatabase();

        // Création de la ListView avec ObservableList
        ListView<String> listView = new ListView<>(data);

        // Bouton "Actualiser"
        Button refreshButton = new Button("Actualiser");
        refreshButton.setStyle("-fx-font-size: 14; -fx-background-color: #0275d8; -fx-text-fill: white; -fx-background-radius: 15;");
        refreshButton.setOnAction(event -> {
            data.setAll(loadItemsFromDatabase()); // Mise à jour dynamique
        });

        VBox body = new VBox(10, listView, refreshButton);
        body.setStyle("-fx-padding: 10;");
        return body;
    }

    private static void showSideMenu(Stage stage) {
        // Menu latéral
        VBox sideMenu = new VBox(10);
        sideMenu.setStyle("-fx-padding: 20; -fx-background-color: #dcdcdc;");

        Button modifyAccountButton = new Button("Modifier les infos du compte");
        Button changeProfilePhotoButton = new Button("Changer la photo de profil");
        Button addDescriptionButton = new Button("Ajouter une description");

        sideMenu.getChildren().addAll(modifyAccountButton, changeProfilePhotoButton, addDescriptionButton);

        Stage sideMenuStage = new Stage();
        Scene sideMenuScene = new Scene(sideMenu, 300, 400);
        sideMenuStage.setScene(sideMenuScene);
        sideMenuStage.setTitle("Actions du Compte");
        sideMenuStage.show();
    }

    private static void addNewItem() {
        System.out.println("Ajout d'un nouvel objet volé...");
        AddStollenToolsController.showAddStollenToolsController();
        // Logique pour ajouter un nouvel objet (ouvre une nouvelle interface ou formulaire)
    }

    public static ObservableList<String> loadItemsFromDatabase() {
        // Récupérer la liste des objets volés depuis la base de données
        ObservableList<StolenObjet> stolenObjects = getAllStolenObjects();

        // Transformer les objets en format "Marque - Date"
        ObservableList<String> items = FXCollections.observableArrayList();
        for (StolenObjet obj : stolenObjects) {
            items.add(obj.getBrand() + " - " + obj.getDateReported());
        }

        return items;
    }
}
