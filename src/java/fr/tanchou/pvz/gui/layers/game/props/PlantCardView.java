package fr.tanchou.pvz.gui.layers.game.props;

import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlantCardView extends ImageView {
    private final PlantCard plantCard;
    private boolean canBuy = false;
    public PlantCardView(PlantCard plantCard){
        Image image = new Image(Objects.requireNonNull(plantCard.getClass().getResourceAsStream("/assets/card/" + plantCard.getPlant().getName() + "Card.png")));
        super(image);
        this.plantCard=plantCard;
        this.setCursor(Cursor.CROSSHAIR);

        this.setOpacity(0.5);
    }

    public void update(){
        if (plantCard.canBuy() && !this.canBuy){
            this.setDisable(false);
            this.setCursor(Cursor.HAND);

            this.canBuy = true;

            this.setOpacity(1);

        }else if (!plantCard.canBuy() && this.canBuy){
            this.setDisable(true);
            this.setCursor(Cursor.CROSSHAIR);

            this.canBuy = false;

            this.setOpacity(0.5);
        }
    }

    public PlantCard getPlantCard(){
        return plantCard;
    }
}
