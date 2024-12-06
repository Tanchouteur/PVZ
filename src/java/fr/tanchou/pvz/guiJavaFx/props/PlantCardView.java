package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class PlantCardView extends StackPane { // StackPane permet de superposer facilement les éléments
    private final PlantCard plantCard;
    private final ImageView cardImageView;
    private final Rectangle cooldownBar;
    private boolean canBuy = false;

    public PlantCardView(PlantCard plantCard, int indexOfPlantCard) {
        this.plantCard = plantCard;

        // Image de la carte
        Image image = new Image(Objects.requireNonNull(plantCard.getClass().getResourceAsStream("/assets/cards/" + plantCard.getPlant().getName() + "Card.png")));
        this.cardImageView = new ImageView(image);
        this.cardImageView.setFitWidth(160);
        this.cardImageView.setFitHeight(100);

        // Barre de cooldown
        this.cooldownBar = new Rectangle(160, 100); // Même taille que la carte
        this.cooldownBar.setFill(Color.color(0, 0, 0, 0.5)); // Semi-transparent
        this.cooldownBar.setHeight(0); // Commence invisible
        this.cooldownBar.setMouseTransparent(true); // Ne bloque pas les clics

        // Ajout des éléments au StackPane
        this.getChildren().addAll(cardImageView, cooldownBar);

        // Positionnement global
        this.setCursor(Cursor.CROSSHAIR);
        this.setLayoutX(20);
        this.setLayoutY(indexOfPlantCard * 110 + 100);
        this.setDisable(true);
        this.setMouseTransparent(false);
        this.setOpacity(0.7);
    }

    public void update() {
        // Gestion de l'achat possible
        if (plantCard.canBuy() && !this.canBuy) {
            this.setDisable(false);
            this.setCursor(Cursor.HAND);
            this.canBuy = true;
            this.setOpacity(1);
        } else if (!plantCard.canBuy() && this.canBuy) {
            this.setDisable(true);
            this.setCursor(Cursor.CROSSHAIR);
            this.canBuy = false;
            this.setOpacity(0.7);
        }

        // Gestion du cooldown
        if (plantCard.getLastSelected() > 0) {
            double totalCooldown = plantCard.getCooldown(); // Exemple : cooldown de référence
            double cooldownRemaining = plantCard.getLastSelected();

            // Calcul de la hauteur de la barre
            double percentage = cooldownRemaining / totalCooldown;
            cooldownBar.setHeight(100 * percentage); // Ajuste la hauteur

            cooldownBar.setTranslateY((100 * (1 - percentage)) / 2); // Centre la barre

            // Couleur ou style pour visualiser le cooldown
            cooldownBar.setFill(Color.color(0.1, 0.1, 0.1, 0.5 + (0.5 * (1 - percentage))));
        } else {
            cooldownBar.setHeight(0); // Réinitialise la barre quand il n'y a plus de cooldown
        }
    }

    public PlantCard getPlantCard() {
        return plantCard;
    }
}
