package fr.tanchou.pvz.gui.layers.ihm;

import fr.tanchou.pvz.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import java.util.Objects;

public class SoldView extends HBox {
    private Label countLabel;
    private final Player player;
    public SoldView(int width, int height, Player player) {
        super();
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setStyle("-fx-background-color: rgba(0,0,0,0.05);");

        this.player = player;

        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/items/Sun/SunAnimated.gif")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setOpacity(0.9);
        countLabel = new Label(player.getSold() +"");
        countLabel.setStyle("-fx-font-size: 36; -fx-text-fill: yellow;");
        countLabel.setLayoutY(30);

        this.getChildren().addAll(imageView, countLabel);
        this.setSpacing(5); // Espacement entre l'image et le texte
    }

    public void update() {
        countLabel.setText(String.valueOf(player.getSold()));
    }
}
