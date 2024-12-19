package fr.tanchou.pvz.web;

import fr.tanchou.pvz.ia.data.Statistics;
import fr.tanchou.pvz.ia.utils.GenerationManager;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketHandler extends WebSocketServer {

    private final GenerationManager generationManager; // Référence à votre backend principal
    private final Statistics statistics; // Référence aux statistiques
    private boolean running = false;
    private WebSocket webSocket;

    // Constructeur
    public WebSocketHandler(int port, GenerationManager generationManager, Statistics statistics) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        super(new InetSocketAddress("0.0.0.0", port));

        this.generationManager = generationManager;
        this.statistics = statistics;

    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Nouveau client connecté : " + conn.getRemoteSocketAddress());
        webSocket = conn;
        System.setOut(new PrintStream(new DualOutputStream(System.out, webSocket)));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client déconnecté : " + conn.getRemoteSocketAddress());
        running = false;
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Diviser le message en commande et paramètres
        String[] parts = message.split(" ", 2);  // Split message en 2 parties : commande et paramètre(s)
        String command = parts[0];  // La première partie est la commande
        String parameters = parts.length > 1 ? parts[1] : "";  // Le reste est considéré comme paramètre(s)

        // Traitement des différentes commandes via switch
        switch (command) {
            case "GET_STATISTICS":
                // Envoi des statistiques au client
                String response = statistics.getScoresHistory().toString();
                conn.send("Statistics : "+response);
                break;

            case "START_TRAINING":
                // Démarrage de l'entraînement
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(generationManager::fullAutoTrain);  // Démarrage de l'entraînement complet
                conn.send("Training démarré.");
                break;

            case "START_SEMI_AUTO_TRAIN":
                // Vérification si un paramètre est fourni
                if (!parameters.isEmpty()) {
                    try {
                        int nbGen = Integer.parseInt(parameters);  // Récupère le nombre de générations
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(() -> generationManager.semiAutoTrain(nbGen));  // Démarrage de l'entraînement semi-auto
                        conn.send("Training semi-auto démarré pour " + nbGen + " générations.");
                    } catch (NumberFormatException e) {
                        conn.send("Erreur : Le nombre de générations doit être un entier.");
                    }
                } else {
                    conn.send("Erreur : Paramètre manquant pour START_SEMI_AUTO_TRAIN.");
                }
                break;

            case "STOP_TRAINING":
                // Arrêt de l'entraînement
                generationManager.stopTraining();
                break;

            default:
                // Commande inconnue
                conn.send("Commande inconnue.");
                break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Serveur WebSocket démarré !");
        running = true;
    }

    // Méthode pour envoyer des messages à tous les clients connectés
    public void broadcastMessage(String message) {
        for (WebSocket conn : this.getConnections()) {
            conn.send(message);
        }
    }

    public boolean isOpen() {
        return this.isRunning();
    }

    private boolean isRunning() {
        return running;
    }

}
