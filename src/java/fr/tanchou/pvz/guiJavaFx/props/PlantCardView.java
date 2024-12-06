package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlantCardView extends ImageView {
    private final PlantCard plantCard;
    private boolean canBuy = false;

    public PlantCardView(PlantCard plantCard, int indexOfPlantCard){
        Image image = new Image(Objects.requireNonNull(plantCard.getClass().getResourceAsStream("/assets/cards/" + plantCard.getPlant().getName() + "Card.png")));
        super(image);
        this.plantCard=plantCard;
        this.setCursor(Cursor.CROSSHAIR);

        this.setFitWidth(160);
        this.setFitHeight(100);

        this.setLayoutX(20);
        this.setLayoutY(indexOfPlantCard*110 + 100);

        this.setDisable(true);
        this.setMouseTransparent(false);
        this.setOpacity(0.7);
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

            this.setOpacity(0.7);
        }

        if (plantCard.getCooldown() > 0){
            //en fonction du temps restant avant de pouvoir acheter la plante, on fait un bar qui se rempli sur la card comme dans le vrais plante versus zombie
            //Le cooldown est fixe (valeur de référence)
            //On peut faire un calcul pour avoir une barre qui se rempli en fonction du temps restant

        }else if (this.getStyle() != null){
            this.setStyle(null);
        }
    }

    public PlantCard getPlantCard(){
        return plantCard;
    }
}
