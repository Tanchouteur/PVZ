package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import fr.tanchou.pvz.guiJavaFx.props.EntityView;
import fr.tanchou.pvz.guiJavaFx.props.ZombieView;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ZombieLayer extends Pane {
    private final Row[] rows;
    private final AssetsLoader assetsLoader;
    private final SoundManager soundManager;

    public ZombieLayer(double width, double height, Row[] rows, AssetsLoader assetsLoader, SoundManager soundManager) {
        super();
        this.assetsLoader = assetsLoader;
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);

        this.soundManager = soundManager;

        //this.setStyle("-fx-background-color: rgba(241,7,7,0.3);");

        this.rows = rows;
    }

    public void update() {
        for (Row row : rows) {
            if ((row.getListZombies() == null || row.getListZombies().isEmpty()) && this.getChildren().isEmpty()) {
                continue;
            }

            // Supprimer les vues des zombies morts
            Iterator<Node> childIterator = this.getChildren().iterator();
            while (childIterator.hasNext()) {
                Node node = childIterator.next();
                EntityView entityView = (EntityView) node;

                if (entityView.getEntity().getHealthPoint() <= 0 || !(entityView.getEntity() instanceof Zombie)) {
                    childIterator.remove(); // Supprimer via l'Iterator
                }
            }

            // Parcourir les zombies dans la ligne
            List<Zombie> zombiesCopy = new ArrayList<>(row.getListZombies());
            for (Zombie zombie : zombiesCopy) {
                boolean found = false;

                for (Node node : this.getChildren()) {
                    EntityView entityView = (EntityView) node;
                    if (entityView.getEntity().equals(zombie)) {
                        entityView.update();
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    ZombieView entityView = new ZombieView(zombie, 260, 275, assetsLoader.getAssetEntity(zombie), soundManager);
                    entityView.update();
                    this.getChildren().add(entityView);
                }
            }
        }
    }

}
