package fr.tanchou.pvz.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import fr.tanchou.pvz.ia.data.ModelManager;
import fr.tanchou.pvz.ia.data.Statistics;
import fr.tanchou.pvz.ia.utils.GenerationManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebApi {
    private final HttpServer server;
    private final GenerationManager generationManager; // Référence au backend principal
    private final Statistics statistics; // Référence aux statistiques

    public WebApi(int port, GenerationManager generationManager, Statistics statistics) throws IOException {
        this.generationManager = generationManager;
        this.server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        this.statistics = statistics;

        // Définir les endpoints
        server.createContext("/api/statistics", this::handleStatistics);
        server.createContext("/api/train/full-auto", this::handleFullAutoTrain);
        server.createContext("/api/train/semi-auto", this::handleSemiAutoTrain);
        server.createContext("/api/train/stop", this::handleStopTrain);
        server.createContext("/api/config", this::handleConfig);
        server.createContext("/api/model/save", this::handleSaveModel);

        server.setExecutor(null); // Default executor
    }

    public void start() {
        System.out.println("API server running...");
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    private void handleStatistics(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        if ("GET".equals(exchange.getRequestMethod())) {
            // Obtenir les statistiques
            String response = parseStatistics();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private String parseStatistics() {
        return statistics.getScoresHistory().toString();
    }

    // Parseur de requêtes POST
    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                params.put(pair[0], pair[1]);
            }
        }
        return params;
    }

    private void handleFullAutoTrain(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        if ("POST".equals(exchange.getRequestMethod())) {
            // Lancer le training automatique

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(generationManager::fullAutoTrain);

            sendResponse(exchange, "Training automatique lancé avec succès !");
        } else {
            sendMethodNotAllowed(exchange);
        }
    }

    private void handleSemiAutoTrain(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        if ("POST".equals(exchange.getRequestMethod())) {
            String query = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> params = parseQuery(query);

            int nbGen = Integer.parseInt(params.getOrDefault("generations", "10"));
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> generationManager.semiAutoTrain(nbGen));

            sendResponse(exchange, "Training semi-auto lancé pour " + nbGen + " générations.");
        } else {
            sendMethodNotAllowed(exchange);
        }
    }

    private void handleConfig(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        if ("POST".equals(exchange.getRequestMethod())) {
            // Lire les paramètres de configuration
            String query = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> params = parseQuery(query);

            // Exemple : configurer nbThread
            int nbThread = Integer.parseInt(params.getOrDefault("nbThread", "8"));
            generationManager.setNumberOfThreads(nbThread);

            sendResponse(exchange, "Configuration mise à jour.");
        } else {
            sendMethodNotAllowed(exchange);
        }
    }

    private void handleStopTrain(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        if ("POST".equals(exchange.getRequestMethod())) {
            this.generationManager.stopTraining();
            sendResponse(exchange, "Training arrêté.");
        } else {
            sendMethodNotAllowed(exchange);
        }
    }

    private void handleSaveModel(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        if ("POST".equals(exchange.getRequestMethod())) {
            // Lire le nom du modèle
            String query = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> params = parseQuery(query);
            String modelName = params.getOrDefault("name", "model");

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            // Sauvegarder le modèle
            executorService.submit(() -> ModelManager.saveModel(generationManager.getBestModels().get(0), modelName));

            sendResponse(exchange, "Modèle sauvegardé.");

        } else {
            sendMethodNotAllowed(exchange);
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(405, -1);
        System.out.println("Method Not Allowed");
    }
}
