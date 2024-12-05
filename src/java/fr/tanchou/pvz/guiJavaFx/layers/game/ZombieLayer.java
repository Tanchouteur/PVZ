package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.guiJavaFx.props.EntityView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class ZombieLayer extends Pane {
    private final Row[] rows;

    public ZombieLayer(double width, double height, Row[] rows) {
        super();
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);

        //this.setStyle("-fx-background-color: rgba(241,7,7,0.3);");

        this.rows = rows;
    }

    public void update() {
        for (Row row : rows) {
            if ((row.getListZombies() == null || row.getListZombies().isEmpty()) && this.getChildren().isEmpty()) {
                continue;
            }
            //Mettre a jour les vues des zombies et supprimer les vue si ils sont morts ou avec 0 de vie
            //Je vais me servir de la liste this.getChildren() pour parcourir et stocker les vues des zombies

            LinkedList<Node> toRemove = new LinkedList<>();
            for (Node node : this.getChildren()) {
                EntityView entityView = (EntityView) node;

                if (entityView.getEntity().getHealthPoint() <= 0 || !(entityView.getEntity() instanceof Zombie)) {
                    toRemove.add(node);
                }
            }

            for (Node node : toRemove) {
                EntityView entityView = (EntityView) node;
                this.getChildren().remove(entityView);
            }

            for (Zombie zombie : row.getListZombies()) {
                boolean found = false;

                for (Node node : this.getChildren()) {
                    EntityView entityView = (EntityView) node;
                    if (entityView.getEntity().equals(zombie)) {
                        entityView.updateZombiePosition();
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    EntityView entityView = new EntityView(zombie, 275, 330);
                    entityView.updateZombiePosition();
                    this.getChildren().add(entityView);
                }
            }
        }
    }
}