package fr.tanchou.pvz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PartieVue extends Application {
    private Pane rootPane;
    private GridPane gridPane;
    private Pane animationLayer;
    private PartieController controller;

    @Override
    public void start(Stage primaryStage) {
        rootPane = new Pane();
        gridPane = new GridPane();
        animationLayer = new Pane();

        rootPane.getChildren().addAll(gridPane, animationLayer);

        controller = new PartieController(this); // Lien avec le contrôleur
        primaryStage.setScene(new Scene(rootPane, 800, 600));
        primaryStage.show();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Pane getAnimationLayer() {
        return animationLayer;
    }
}
