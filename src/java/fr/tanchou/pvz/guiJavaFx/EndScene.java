package fr.tanchou.pvz.guiJavaFx;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class EndScene extends Pane {

    public EndScene(String message) {
        super();
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        label.setLayoutX(800);
        label.setLayoutY(500);
        getChildren().add(label);
    }
}
