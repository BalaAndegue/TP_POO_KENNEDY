package com.example.tp_poo2.service;

import com.example.tp_poo2.models.StolenObjet;
import com.example.tp_poo2.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseService {
    // URL de connexion à la base de données MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stolen_objects";
    private static final String DB_USERNAME = "lionnel";
    private static final String DB_PASSWORD = "lionnel10";
    private  static User authenticatedUser ;
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

    public static void registerstollentools(String type, String imei, String macaddress, String marque, String couleur,String stockage, String description, Date date, int id) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String insertQuery = "INSERT INTO stolen_objects (type, imei ,mac_address, brand, color, storage, description, date_reported ,owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, type);
            statement.setString(2, imei);
            statement.setString(3, macaddress);
            statement.setString(4, marque);
            statement.setString(5, couleur);
            statement.setString(6, stockage);
            statement.setString(7, description);
            statement.setDate(8, date);
            statement.setInt(9, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String searchQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(searchQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                authenticatedUser = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("birthDate"),
                        resultSet.getString("gender"),
                        resultSet.getString("city"),
                        resultSet.getString("neighborhood"),
                        resultSet.getString("username"),
                        resultSet.getString("password") // ⚠️ Pensez à hasher les mots de passe pour plus de sécurité
                );
                System.out.println("Authentification réussie pour : " + authenticatedUser.getUsername());
                return true;
            } else {
                System.out.println("Échec de l'authentification.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static ObservableList<StolenObjet> getAllStolenObjects() {
        ObservableList<StolenObjet> stolenObjects = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM stolen_objects";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StolenObjet obj = new StolenObjet(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("imei"),
                        resultSet.getString("mac_address"),
                        resultSet.getString("brand"),
                        resultSet.getString("color"),
                        resultSet.getString("storage"),
                        resultSet.getString("description"),
                        resultSet.getDate("date_reported"),
                        resultSet.getInt("owner_id")
                );

                stolenObjects.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stolenObjects;
    }

    // Getter pour récupérer l'utilisateur authentifié
    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}

