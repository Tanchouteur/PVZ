package fr.tanchou.pvz.guiJavaFx.layers.ihm;

import fr.tanchou.pvz.game.spawn.ZombieSpawner;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameInfoLayer extends Pane {

    private final Label stateLabel;
    private final Label tickLabel;
    private final Label waveLabel;
    private final Label zombiesLabel;

    private final ZombieSpawner zombieSpawner;

    public GameInfoLayer(ZombieSpawner zombieSpawner) {
        super();
        this.zombieSpawner = zombieSpawner;
        // Dimensions et positionnement du panneau
        this.setPrefHeight(50); // Hauteur de la barre
        this.setPrefWidth(600); // Largeur ajustée (centrée plus tard par le parent)
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-border-color: white;");

        // Labels pour afficher les informations
        stateLabel = createLabel("State: " + zombieSpawner.getCurrentState(), 10);
        tickLabel = createLabel("Ticks: " + zombieSpawner.getTickCount(), 150);
        waveLabel = createLabel("Wave: " + (zombieSpawner.isInWave() ? "Active" : "Inactive"), 300);
        zombiesLabel = createLabel("Zombies Left: " + zombieSpawner.getZombiesToSpawn(), 450);

        // Ajouter les labels au panneau
        this.getChildren().addAll(stateLabel, tickLabel, waveLabel, zombiesLabel);
    }

    // Méthode pour mettre à jour les informations dynamiques
    public void update() {
        stateLabel.setText("State: " + zombieSpawner.getCurrentState());
        tickLabel.setText(" Ticks: " + zombieSpawner.getTickCount() + " / " + zombieSpawner.getTotalTick());
        waveLabel.setText("Wave: " + (zombieSpawner.isInWave() ? "Active" : "Inactive"));
        zombiesLabel.setText("Zombies Left: " + zombieSpawner.getZombiesToSpawn());
    }

    // Méthode utilitaire pour créer un label avec un style cohérent
    private Label createLabel(String text, double x) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(x);
        label.setLayoutY(10); // Centré verticalement dans le panneau
        return label;
    }
}
