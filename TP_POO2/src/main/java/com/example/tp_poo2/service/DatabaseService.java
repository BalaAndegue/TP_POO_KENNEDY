package com.example.tp_poo2.service;

import java.sql.*;

public class DatabaseService {
    // URL de connexion à la base de données MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stolen_objects";
    private static final String DB_USERNAME = "lionnel";
    private static final String DB_PASSWORD = "lionnel10";

    public static void initializeDatabase() throws Exception {
        // Connexion à la base de données MySQL
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        // Vérification de la connexion réussie
        if (connection != null) {
            System.out.println("Connexion réussie à la base de données 'stolen_objects' !");
        }

        // Fermeture de la connexion
        connection.close();
    }


    public static void registerUser(String nom, String prenom, Date birthDate, String genre, String ville, String quartier, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String insertQuery = "INSERT INTO users (firstName, lastName, birthDate, gender, city, neighborhood, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setDate(3, birthDate);
            statement.setString(4, genre);
            statement.setString(5, ville);
            statement.setString(6, quartier);
            statement.setString(7, username);
            statement.setString(8, password);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String searchQuery = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(searchQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            boolean isAuthenticated = resultSet.next();
            connection.close();
            return isAuthenticated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
