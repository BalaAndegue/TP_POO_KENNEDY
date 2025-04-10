package com.example.tp_poo2;



import com.example.tp_poo2.service.DatabaseService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date;

public class RegistrationController {
    public static void showRegistrationWindow() {
        Stage registrationStage = new Stage();

        String defaultstyle =


                "-fx-cursor: hand;"+
                "-fx-pref-height :30;"+
                "-fx-pref-width :220;"+
                "-fx-border-radius: 35;"+
                        "-fx-background-radius: 35;"+
                        "-fx-padding: 5;";

        // Création du GridPane
        GridPane registrationLayout = new GridPane();
        registrationLayout.setHgap(10); // Espacement horizontal entre les colonnes
        registrationLayout.setVgap(10); // Espacement vertical entre les lignes
        registrationLayout.setStyle("-fx-padding: 20;"); // Marges autour de la grille

        // Création des labels et champs
        Label firstNameLabel = new Label("Prénom :");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Entrez votre prénom");
        firstNameField.setStyle(defaultstyle);

        Label lastNameLabel = new Label("Nom :");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Entrez votre nom");
        lastNameField.setStyle(defaultstyle);

        Label birthDateLabel = new Label("Date de naissance :");
        TextField birthDateField = new TextField();
        birthDateField.setPromptText("JJ/MM/AAAA");
        birthDateField.setStyle(defaultstyle);


        Label genderLabel = new Label("Sexe :");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("M", "F");
        genderComboBox.setStyle(defaultstyle);

        Label cityLabel = new Label("Ville :");
        TextField cityField = new TextField();
        cityField.setPromptText("Entrez votre ville");
        cityField.setStyle(defaultstyle);

        Label neighborhoodLabel = new Label("Quartier :");
        TextField neighborhoodField = new TextField();
        neighborhoodField.setPromptText("Entrez votre quartier");
        neighborhoodField.setStyle(defaultstyle);

        Label usernameLabel = new Label("Nom d'utilisateur :");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Entrez votre nom d'utilisateur");
        usernameField.setStyle(defaultstyle);

        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");
        passwordField.setStyle(defaultstyle);

        // Bouton d'inscription
        Button registerButton = new Button("S'inscrire");
        registerButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String birthDate = birthDateField.getText();
            String gender = genderComboBox.getValue();
            String city = cityField.getText();
            String neighborhood = neighborhoodField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Enregistrement des données dans la base
            DatabaseService.registerUser(
                    firstName, lastName, Date.valueOf(birthDate), gender, city, neighborhood, username, password
            );
        });

        // Placement des composants dans la grille
        registrationLayout.add(firstNameLabel, 0, 0); // Colonne 0, Ligne 0
        registrationLayout.add(firstNameField, 1, 0); // Colonne 1, Ligne 0
        registrationLayout.add(lastNameLabel, 0, 1); // Colonne 0, Ligne 1
        registrationLayout.add(lastNameField, 1, 1); // Colonne 1, Ligne 1
        registrationLayout.add(birthDateLabel, 0, 2); // Colonne 0, Ligne 2
        registrationLayout.add(birthDateField, 1, 2); // Colonne 1, Ligne 2
        registrationLayout.add(genderLabel, 0, 3); // Colonne 0, Ligne 3
        registrationLayout.add(genderComboBox, 1, 3); // Colonne 1, Ligne 3
        registrationLayout.add(cityLabel, 0, 4); // Colonne 0, Ligne 4
        registrationLayout.add(cityField, 1, 4); // Colonne 1, Ligne 4
        registrationLayout.add(neighborhoodLabel, 0, 5); // Colonne 0, Ligne 5
        registrationLayout.add(neighborhoodField, 1, 5); // Colonne 1, Ligne 5
        registrationLayout.add(usernameLabel, 0, 6); // Colonne 0, Ligne 6
        registrationLayout.add(usernameField, 1, 6); // Colonne 1, Ligne 6
        registrationLayout.add(passwordLabel, 0, 7); // Colonne 0, Ligne 7
        registrationLayout.add(passwordField, 1, 7); // Colonne 1, Ligne 7
        registrationLayout.add(registerButton, 1, 8); // Colonne 1, Ligne 8

        // Affichage de la fenêtre
        registrationStage.setScene(new Scene(registrationLayout, 400, 450));
        registrationStage.setTitle("Inscription");
        registrationStage.show();
    }
}
