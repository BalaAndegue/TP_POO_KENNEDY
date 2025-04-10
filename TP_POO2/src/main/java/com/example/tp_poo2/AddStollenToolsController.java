package com.example.tp_poo2;

import com.example.tp_poo2.models.User;
import com.example.tp_poo2.service.DatabaseService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date;

public class AddStollenToolsController {

    public static void showAddStollenToolsController() {
        Stage registrationStage = new Stage();
        User authenticatedUser = DatabaseService.getAuthenticatedUser();
        // Création du GridPane
        GridPane registrationLayout = new GridPane();
        registrationLayout.setHgap(10); // Espacement horizontal entre les colonnes
        registrationLayout.setVgap(10); // Espacement vertical entre les lignes
        registrationLayout.setStyle("-fx-padding: 20;"); // Marges autour de la grille

        // Création des labels et champs

        Label typeLabel = new Label("type:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("telephone", "modem", "ordinateur");



        Label imeiLabel = new Label("IMEI:");
        TextField imeiField = new TextField();
        imeiField.setPromptText("Entrez votre nom");

        Label macaddressLabel = new Label("MAC_ADDRESS:");
        TextField macaddressField = new TextField();
        macaddressField.setPromptText("Entrez votre prénom");

        Label marqueLabel = new Label("MARQUE :");
        TextField marqueField = new TextField();
        marqueField.setPromptText("Entrez votre ville");

        Label couleurLabel = new Label("COULEUR:");
        TextField couleurField = new TextField();
        couleurField.setPromptText("Entrez votre quartier");

        Label stockageLabel = new Label("STOCKAGE:");
        TextField stockageField = new TextField();
        stockageField.setPromptText("Entrez votre nom d'utilisateur");

        Label descriptionLabel = new Label("Description :");
        PasswordField descriptionField = new PasswordField();
        descriptionField.setPromptText("Entrez votre mot de passe");

        Label dateLabel = new Label("Date  :");
        TextField dateField = new TextField();
        dateField.setPromptText("JJ/MM/AAAA");

        // Bouton d'inscription
        Button registerButton = new Button("S'inscrire");
        registerButton.setOnAction(event -> {
            String type = typeComboBox.getValue();
            String imei = imeiField.getText();
            String macaddress = macaddressField.getText();
            String marque= marqueField.getText();
            String couleur = couleurField.getText();
            String stockage = stockageField.getText();
            String description= descriptionField.getText();
            String date = dateField.getText();

            // Enregistrement des données dans la base
            DatabaseService.registerstollentools(
                    type, imei, macaddress,marque ,couleur, stockage, description, Date.valueOf(date), authenticatedUser.getId()
                    );
        });

        // Placement des composants dans la grille
        registrationLayout.add(typeLabel, 0, 0); // Colonne 0, Ligne 0
        registrationLayout.add(typeComboBox, 1, 0); // Colonne 1, Ligne 0
        registrationLayout.add(imeiLabel, 0, 1); // Colonne 0, Ligne 1
        registrationLayout.add(imeiField, 1, 1); // Colonne 1, Ligne 1
        registrationLayout.add(macaddressLabel, 0, 2); // Colonne 0, Ligne 2
        registrationLayout.add(macaddressField, 1, 2); // Colonne 1, Ligne 2
        registrationLayout.add(marqueLabel, 0, 3); // Colonne 0, Ligne 3
        registrationLayout.add(marqueField, 1, 3); // Colonne 1, Ligne 3
        registrationLayout.add(stockageLabel, 0, 4); // Colonne 0, Ligne 4
        registrationLayout.add(stockageField, 1, 4); // Colonne 1, Ligne 4
        registrationLayout.add(descriptionLabel, 0, 5); // Colonne 0, Ligne 5
        registrationLayout.add(descriptionField, 1, 5); // Colonne 1, Ligne 5
        registrationLayout.add(dateLabel, 0, 6); // Colonne 0, Ligne 6
        registrationLayout.add(dateField, 1, 6); // Colonne 1, Ligne 6

        registrationLayout.add(registerButton, 2, 8); // Colonne 1, Ligne 8

        // Affichage de la fenêtre
        registrationStage.setScene(new Scene(registrationLayout, 400, 450));
        registrationStage.setTitle("Inscription");
        registrationStage.show();
    }
}
