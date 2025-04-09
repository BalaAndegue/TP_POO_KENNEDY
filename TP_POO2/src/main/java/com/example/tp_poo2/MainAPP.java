package com.example.tp_poo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainAPP extends Application {
    @Override
    public void start(Stage primaryStage) {

        String defaultstyle = "-fx-background-color: #4285F4;"+
        "-fx-text-fill: white;"+
                "-fx-background-radius: : 40;"+
                "-fx-cursor: hand;"+
                "-fx-pref-height :40;"+
                "-fx-pref-width :120;";

        // Charger l'image
        Image logo = new Image(getClass().getResource("/com/example/tp_poo2/image/logos.png").toExternalForm());

        ImageView imageView = new ImageView(logo);
        imageView.setFitHeight(100); // Ajustez la taille du logo
        imageView.setFitWidth(100);

        Label logotitleLabel = new Label("FINDKENNEDY");
        logotitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        logotitleLabel.setTextFill(Color.BLUE);


        Label titleLabel = new Label("BIENVENU SUR FINDKENNEDY");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label("signalez vos objets et obtenez de l'aide .Entrez votre address IMEI , MAC ... ");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        subtitleLabel.setTextFill(Color.WHITE);
        subtitleLabel.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Recherchez un objet");
        searchField.setPrefWidth(300);
        searchField.setStyle(
                "-fx-background-radius-: 75;"+
                        "-fx-background-color: white;"+
                        "-fx-padding: 10 100 10 100;"+
                        "-fx-font-size: 14px;"+
                        "-fx-pref-height :50;"+
                        "-fx-pref-width :80;"

        );
        ImageView searchicon = new ImageView("https://img.icons8.com/ios-glyphs/30/000000/search--v1.png");
        searchicon.setFitHeight(20);
        searchicon.setFitWidth(20);

        StackPane searchBox = new StackPane(searchField, searchicon);
        StackPane.setAlignment(searchicon, Pos.CENTER_LEFT);
        StackPane.setMargin(searchicon, new Insets(0,50,0,10));

        VBox mainwindow = new VBox(10);

        VBox presentation = new VBox(10);
        presentation.setPrefHeight(400);

        HBox entryWindow = new HBox(10);

        Button registrationButton = new Button("Inscription");
        registrationButton.setStyle( defaultstyle
        );
        Button loginButton = new Button("Connexion");
        loginButton.setStyle(defaultstyle);

        registrationButton.setOnAction(event -> {
            RegistrationController.showRegistrationWindow();
        });

        loginButton.setOnAction(event -> {
            LoginController.showLoginWindow();
        });
        VBox description = new VBox(20, titleLabel, subtitleLabel, searchBox);
        description.setPrefHeight(600); // Hauteur de la moitié inférieure
        description.setStyle(
                "-fx-background-image: url('" + getClass().getResource("/com/example/tp_poo2/image/fondappli2.jpeg").toExternalForm() + "');"
                        + // Chemin de l'image
                        "-fx-background-size: cover; " +
                        "-fx-background-repeat: no-repeat;"
        );

        description.setAlignment(Pos.CENTER);
        description.setPadding(new Insets(40));

        entryWindow.getChildren().addAll(registrationButton, loginButton);
        entryWindow.setAlignment(Pos.CENTER_RIGHT);
        entryWindow.setPadding(new Insets(10));
        presentation.getChildren().addAll(imageView,logotitleLabel,entryWindow);
        mainwindow.getChildren().addAll(presentation, description);

        Scene scene = new Scene(mainwindow, 500, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fenêtre d'Entrée");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
