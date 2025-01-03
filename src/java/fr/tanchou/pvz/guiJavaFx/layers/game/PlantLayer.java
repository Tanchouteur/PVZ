package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import fr.tanchou.pvz.guiJavaFx.controller.CellGridController;
import fr.tanchou.pvz.guiJavaFx.controller.ExitCellController;
import fr.tanchou.pvz.guiJavaFx.controller.HoverCellController;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.layout.GridPane;

public class PlantLayer extends GridPane {

    private final CellView[][] plantCasesView = new CellView[5][9];

    public PlantLayer(double width, double height, Partie partie, AssetsLoader assetsLoader, SoundManager soundManager) {
        super();
        this.setPrefSize(width*(0.771), height*0.84);

        //this.setStyle("-fx-background-color: rgba(36,255,6,0.34);");

        this.setLayoutX(0);
        this.setLayoutY(0);

        int rows = 5;
        int cols = 9;

        // Créer et ajouter les cellules
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                CellView cell = new CellView(width / cols, height / rows, partie.getOneRow(row).getPlantCase(col), assetsLoader, soundManager);
                cell.setOnMouseClicked(new CellGridController(partie.getPlayer(), cell, soundManager));
                cell.setOnMouseEntered(new HoverCellController(partie.getPlayer() , cell));
                cell.setOnMouseExited(new ExitCellController(partie.getPlayer() , cell));
                cell.setMouseTransparent(false);
                cell.setPickOnBounds(true);
                plantCasesView[row][col] = cell;
                this.add(plantCasesView[row][col], col, row);

            }
        }
    }

    public void update() {
        for (CellView[] row : plantCasesView) {
            for (CellView cell : row) {
                cell.update();
            }
        }
    }
}
