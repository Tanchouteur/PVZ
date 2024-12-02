package fr.tanchou.pvz.game.board;

import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.game.controller.CaseClickController;
import fr.tanchou.pvz.player.Player;
import javafx.scene.layout.Pane;

public class RowVue {
    private final Pane caseLayer;
    private final Pane zombieLayer;
    private final Pane bulletLayer;

    private final Player player;
    private final Pane animationLayer;

    private final Pane rowPane;  // Nouveau Pane parent qui contient les trois couches
    private final Row row;

    public RowVue(Row row, Player player, Pane animationLayer) {
        this.row = row;
        this.player = player;
        this.animationLayer = animationLayer;
        zombieLayer = new Pane();
        bulletLayer = new Pane();
        caseLayer = new Pane();
        rowPane = new Pane();  // Initialisation du Pane parent

        //rowPane.setStyle("-fx-background-color: linear-gradient(to bottom, #00ff00, #00cc00);");
        rowPane.setPrefSize(1090, 800);  // Taille du Pane parent
        caseLayer.setPrefSize(1090, 800);
        caseLayer.setLayoutY(rowPane.getWidth());
        caseLayer.setLayoutX(rowPane.getHeight());
        // Ajout des couches au Pane parent (rowPane)
        rowPane.getChildren().addAll(caseLayer, zombieLayer, bulletLayer );

        caseLayer.setPickOnBounds(false);

        for (Case caseModel : row.getListCases()) {
            CaseClickController clickController = new CaseClickController(caseModel, this);
            caseModel.getCaseVue().setOnMouseClicked(clickController);
            caseLayer.getChildren().add(caseModel.getCaseVue());
        }

        // Ajouter les zombies à la couche correspondante
        for (Zombie zombie : row.getListZombies()) {
            EntitieVue zombieVue = zombie.createVue(zombieLayer);
            zombieLayer.getChildren().add(zombieVue.getImageView());
        }

        // Ajouter les projectiles à la couche correspondante
        for (Entity bullet : row.getListBullets()) {
            EntitieVue bulletVue = bullet.createVue(bulletLayer);
            bulletLayer.getChildren().add(bulletVue.getImageView());
        }
    }

    public void setSize(double width, double height) {
        rowPane.setPrefSize(width, height);
    }

    public void update() {

        // Met à jour la position et l'état des plantes
        for (Case caseModel : row.getListCases()) {
            caseModel.getCaseVue().update();
        }

        // Met à jour la position et l'état des zombies
        for (Zombie zombie : row.getListZombies()) {
            if (zombie.getVue() == null) {
                zombie.setVue(zombie.createVue(zombieLayer));
            }
            if (!zombieLayer.getChildren().contains(zombie.getVue().getImageView())) {
                zombieLayer.getChildren().add(zombie.getVue().getImageView());
            }
            zombie.getVue().update(zombieLayer);
        }

        // Met à jour la position et l'état des projectiles
        for (Entity bullet : row.getListBullets()) {
            if (bullet.getVue() == null) {
                bullet.setVue(bullet.createVue(bulletLayer));
            }
            if (!bulletLayer.getChildren().contains(bullet.getVue().getImageView())) {
                bulletLayer.getChildren().add(bullet.getVue().getImageView());
            }
            bullet.getVue().update(bulletLayer);
        }
    }

    public Pane getRowPane() {
        return rowPane;  // Retourne le Pane parent qui contient toutes les couches
    }

    public Player getPlayer() {
        return player;
    }

    public Pane getAnimationLayer() {
        return animationLayer;
    }
}