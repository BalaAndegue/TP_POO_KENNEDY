

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    // Map pour stocker les clients connectés (ID -> ClientHandler)
    private static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static final int PORT = 5000;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(50); // Pool de threads pour gérer les connexions

        System.out.println("Serveur en écoute sur le port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accepter une nouvelle connexion
                //System.out.println("Connexion acceptée de : " + clientSocket.getInetAddress());
                System.out.println("Connexion acceptée de : " + clientSocket.getPort());

                // Créer un ClientHandler pour gérer cette connexion
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.submit(clientHandler); // Exécuter le ClientHandler dans un thread du pool
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        } finally {
            threadPool.shutdown(); // Ferme le pool de threads en douceur pour ne pas avoir d'effet desagreable
        }
    }

    // Classe interne pour gérer chaque client
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private BufferedReader in; // Pour messages texte
        private PrintWriter out;   // Pour réponses texte
        private DataInputStream fileIn; // Pour recevoir des fichiers
        private DataOutputStream fileOut; // Pour envoyer des fichiers
        private String clientId; // Identifiant unique

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Initialisation des flux d'entrée/sortie
                //socket.getInputStream() : Cette méthode retourne un flux d'entrée associé au socket. Elle permet de recevoir des données envoyées par le client (ou le serveur si côté client).
                //new InputStreamReader(...) : C'est un adaptateur qui convertit un flux d'octets brut (InputStream) en un flux de caractères, permettant la lecture de données sous forme de texte.
                //new BufferedReader(...) : Il enveloppe l'InputStreamReader pour optimiser la lecture, en permettant de lire des lignes complètes de texte (grâce à sa méthode .readLine()), plutôt que de traiter caractère par caractère.
                //socket.getOutputStream() : Cette méthode retourne un flux de sortie associé au socket, permettant d'envoyer des données vers le client (ou le serveur si côté client).
                //new PrintWriter(...) : Crée un écrivain de flux de caractères, utile pour envoyer du texte.
                //Paramètre true : Ce paramètre active l'auto-flush. Cela signifie que les données sont immédiatement envoyées à la sortie après un appel à des méthodes comme .println() ou .write().

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                fileIn = new DataInputStream(socket.getInputStream());
                fileOut = new DataOutputStream(socket.getOutputStream());

                // Identification du client
                out.println("Bienvenue ! Veuillez entrer votre identifiant unique :");
                clientId = in.readLine();
                if (clientId == null || clientId.isEmpty()) {
                    out.println("Identifiant invalide. Connexion fermée.");
                    socket.close();
                    return;
                }

                // Ajouter le client à la liste des connectés
                clients.put(clientId, this);
                broadcast("Client connecté : " + clientId);

                // Communication avec le client
                String command;
                while ((command = in.readLine()) != null) {
                    if (command.startsWith("/list")) {
                        sendClientList(); // Envoie la liste des clients connectés
                    } else if (command.startsWith("/msg")) {
                        handleMessageRouting(command); // Gère le routage des messages
                    } else if (command.startsWith("/file")) {
                        handleFileTransfer(command); // Gère le transfert des fichiers

                    } else if(command.equals("/quit")){
                        out.println("Déconnexion en cours...");
                        disconnectClient(); // Gère la déconnexion
                        break;
                    }
                    else {
                        out.println("Commande inconnue. Utilisez /list, /msg, ou /file.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur avec le client " + clientId + ": " + e.getMessage());
            } finally {
                // Nettoyer à la déconnexion
                disconnectClient();
            }
        }

        // Envoie un message à un client spécifique
        private void handleMessageRouting(String command) {
            try {
                String[] parts = command.split(" ", 3); // Format attendu : /msg DEST_ID MESSAGE
                if (parts.length < 3) {
                    out.println("Commande invalide. Utilisation : /msg DEST_ID MESSAGE");

                    return;
                }

                String targetId = parts[1];
                String msg = parts[2];
                ClientHandler target = clients.get(targetId);

                if (target != null) {
                    target.out.println("Message de " + clientId + " : " + msg);
                } else {
                    out.println("Le client " + targetId + " n'est pas connecté.");
                }
            } catch (Exception e) {
                out.println("Erreur de routage du message.");
            }
        }

        // Gère le transfert des fichiers
        private void handleFileTransfer(String command) {
            try {
                String[] parts = command.split(" ", 2); // Format attendu : /file DEST_ID
                if (parts.length < 2) {
                    out.println("Commande invalide. Utilisation : /file DEST_ID");
                    return;
                }

                String targetId = parts[1];
                ClientHandler target = clients.get(targetId);

                if (target != null) {
                    out.println("Envoi du fichier. Entrez le nom du fichier :");
                    String fileName = in.readLine();

                    // Envoyer le nom du fichier au destinataire
                    target.out.println("/file " + fileName);

                    // Lire et transférer le fichier
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        target.fileOut.write(buffer, 0, bytesRead);
                        target.fileOut.flush();
                        if (bytesRead < buffer.length) break; // Fin du fichier
                    }
                    out.println("Fichier envoyé à " + targetId);
                } else {
                    out.println("Le client " + targetId + " n'est pas connecté.");
                }
            } catch (IOException e) {
                out.println("Erreur lors de l'envoi du fichier.");
            }
        }

        // Envoie la liste des clients connectés
        private void sendClientList() {
            out.println("Clients connectés :");
            clients.keySet().forEach(out::println);
        }

        // Diffuse un message à tous les clients connectés
        private void broadcast(String message) {
            clients.values().forEach(client -> client.out.println(message));
        }

        // Déconnexion d'un client
        private void disconnectClient() {
            try {
                clients.remove(clientId);
                broadcast("Client déconnecté : " + clientId);
                if (socket != null) socket.close();
                System.out.println("Client déconnecté : " + clientId);
            } catch (IOException e) {
                System.err.println("Erreur lors de la déconnexion : " + e.getMessage());
            }
        }
    }
}
