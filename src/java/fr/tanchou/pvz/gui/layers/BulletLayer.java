package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.gui.props.BulletView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class BulletLayer extends Pane {
    private final Row[] rows;

    public BulletLayer(double width, double height, Row[] rows) {
        super();
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);

        //this.setStyle("-fx-background-color: rgba(213,12,255,0.7);");

        this.rows = rows;
    }

    public void update() {
        List<Bullet> allBullets = new ArrayList<>();
        for (Row row : rows) {
            allBullets.addAll(row.getListBullets());
        }
        // Si aucune bullet n'existe et aucune vue n'est affichée, continue
        if (allBullets.isEmpty() && this.getChildren().isEmpty()) {
            return;
        }

        // Stocker les BulletViews à supprimer et celles à ajouter
        List<Node> viewsToRemove = new ArrayList<>();
        List<Bullet> bulletsToAdd = new ArrayList<>();

        // Identifier les vues obsolètes
        for (Node node : this.getChildren()) {
            BulletView bulletView = (BulletView) node;

            if (!allBullets.contains(bulletView.getBullet())) {
                viewsToRemove.add(bulletView);
            }
        }

        // Identifier les bullets sans vue
        for (Bullet bullet : allBullets) {
            boolean exists = false;
            for (Node node : this.getChildren()) {
                BulletView bulletView = (BulletView) node;
                if (bulletView.getBullet() == bullet) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                bulletsToAdd.add(bullet);
            }
        }

        // Appliquer les modifications : suppression et ajout
        this.getChildren().removeAll(viewsToRemove);
        for (Bullet bullet : bulletsToAdd) {
            this.getChildren().add(new BulletView(bullet));
        }

        // Mettre à jour les positions des BulletViews existants
        for (Node node : this.getChildren()) {
            BulletView bulletView = (BulletView) node;
            bulletView.updateBulletPosition();
        }
    }
}
