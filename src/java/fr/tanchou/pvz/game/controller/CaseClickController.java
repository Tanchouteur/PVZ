package fr.tanchou.pvz.game.controller;

import fr.tanchou.pvz.game.board.Case;
import fr.tanchou.pvz.game.board.RowVue;
import fr.tanchou.pvz.player.Player;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CaseClickController implements EventHandler<MouseEvent> {
    private final Pane paneWithPlant;
    private final Case caseModel;
    private final Player player;

    // Constructeur qui prend en paramètre le modèle de case et le joueur
    public CaseClickController(Case caseModel, RowVue rowVue) {
        this.paneWithPlant = rowVue.getAnimationLayer();
        this.caseModel = caseModel;
        this.player = rowVue.getPlayer();
    }

    // Méthode appelée lors du clic
    @Override
    public void handle(MouseEvent event) {
        // Vérifie si une plante est sélectionnée par le joueur
        if (player.getSelectedPlant() != null) {
            // Place la plante sur la case
            if (caseModel.isOccupied()) {
                System.out.println("Case occupée !");
                return;
            }
            if (player.getSun() < player.getSelectedPlant().getCard().getCost()) {
                System.out.println("Pas assez de soleils !");
                return;
            }
            player.removeSun(player.getSelectedPlant().getCard().getCost());
            caseModel.placePlant(player.getSelectedPlant());
            paneWithPlant.getChildren().remove(player.getSelectedPlant().getVue().getImageView());
            player.setSelectedPlant(null);

            // Affiche un message dans la console pour confirmation
            System.out.println("Plante placée sur la case (" + caseModel.getCaseVue().getRowIndex() + ", " + caseModel.getCaseVue().getColumnIndex() + ")");
        } else {
            // Si aucune plante n'est sélectionnée, afficher un message d'erreur
            System.out.println("Aucune plante sélectionnée pour cette case !");
        }
    }
}
