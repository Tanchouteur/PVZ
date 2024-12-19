package fr.tanchou.pvz.web;

import fr.tanchou.pvz.ia.utils.GenerationManager;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketHandler extends WebSocketServer {

    private final GenerationManager generationManager; // Référence à votre backend principal
    private boolean running = false;

    private final MultiOutputStream multiOutputStream;

    // Constructeur
    public WebSocketHandler(int port, GenerationManager generationManager) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        super(new InetSocketAddress("0.0.0.0", port));

        this.generationManager = generationManager;
        this.multiOutputStream = new MultiOutputStream(System.out);
        PrintStream printStream = new PrintStream(multiOutputStream);
        System.setOut(printStream);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        this.multiOutputStream.addWebSocket(conn);
        conn.send(getConfiguration());
        System.out.println("Nouveau client connecté : " + conn.getRemoteSocketAddress() + " - " + MultiOutputStream.webSocket.size() + " clients connectés.");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        running = false;
        this.multiOutputStream.removeWebSocket(conn);
        System.out.println("Client déconnecté : " + conn.getRemoteSocketAddress()+ " - " + MultiOutputStream.webSocket + " clients connectés.");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Diviser le message en commande et paramètres
        String[] parts = message.split(" ", 2);  // Split message en 2 parties : commande et paramètre(s)
        String command = parts[0];  // La première partie est la commande

        // Le reste est considéré comme des paramètres
        String[] parameters = new String[0];
        if (parts.length > 1) {
            parameters = parts[1].split(" ");  // Split des paramètres
        }


        // Traitement des différentes commandes via switch
        switch (command) {
            case "GET_STATISTICS":
                // Envoi des statistiques au client
                String response = this.generationManager.getStatistics();
                conn.send(response);
                break;

            case "START_TRAINING":
                // Démarrage de l'entraînement
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(generationManager::fullAutoTrain);  // Démarrage de l'entraînement complet
                conn.send("Training démarré.");
                break;

            case "START_SEMI_AUTO_TRAIN":
                // Vérification si un paramètre est fourni
                if (!(parameters.length == 0)) {
                    try {
                        int nbGen = Integer.parseInt(parameters[0]);  // Récupère le nombre de générations
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

            case "UPDATE_CONFIG":
                // Mise à jour de la configuration
                generationManager.setNumberOfThreads(Integer.parseInt(parameters[0]));
                generationManager.setSimulationPerGeneration(Integer.parseInt(parameters[1]));
                generationManager.setMutationAmplitude(Double.parseDouble(parameters[2]));

                conn.send(getConfiguration());
                break;

            case "DESACTIVATE_CONSOLE":
                // Mise à jour de la configuration
                this.multiOutputStream.deactivateConsoleOutput();
                conn.send("Console désactivée.");
                break;

            case "ACTIVATE_CONSOLE":
                // Mise à jour de la configuration
                this.multiOutputStream.activateConsoleOutput();

                conn.send("Console activée.");
                break;

            default:
                // Commande inconnue
                conn.send("Commande inconnue.");
                break;
        }
    }

    //send configuration to client
    private String getConfiguration() {
        return "Configuration " + generationManager.getNumberOfThreads() + " " + generationManager.getSimulationPerGeneration() + " " + generationManager.getMutationAmplitude();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Serveur WebSocket démarré : " + getPublicIPUsingCurl() + ":" + this.getPort());
        running = true;
    }
    public static String getPublicIPUsingCurl() {
        try {
            Process process = Runtime.getRuntime().exec("curl -s https://api.ipify.org");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ip = reader.readLine();  // L'adresse IP publique est renvoyée
            process.waitFor();
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la récupération de l'adresse publique";
        }
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

    public void deactivateConsoleOutput() {
        this.multiOutputStream.deactivateConsoleOutput();
    }
}
