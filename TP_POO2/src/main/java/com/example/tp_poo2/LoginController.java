package com.example.tp_poo2;

import com.example.tp_poo2.service.DatabaseService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
    public static void showLoginWindow() {
        Stage loginStage = new Stage();
        VBox loginLayout = new VBox(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        Button loginButton = new Button("Se connecter");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAuthenticated = DatabaseService.authenticateUser(username, password);
            if (isAuthenticated) {
                MainMenuController.showMainMenu();
                loginStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Identifiants incorrects");
                alert.show();
            }
        });

        loginLayout.getChildren().addAll(usernameField, passwordField, loginButton);
        loginStage.setScene(new Scene(loginLayout, 300, 200));
        loginStage.setTitle("Connexion");
        loginStage.show();
    }
}
