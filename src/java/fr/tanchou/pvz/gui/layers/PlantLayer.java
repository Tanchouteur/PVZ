package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.rowComponent.PlantCase;
import fr.tanchou.pvz.game.rowComponent.Row;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PlantLayer extends GridPane {

    private final CellView[][] plantCasesView = new CellView[5][9];

    public PlantLayer(int width, int height, Partie partie) {
        super();
        this.setPrefSize(width*(0.771), height*0.84);

        this.setStyle("-fx-background-color: rgba(36,255,6,0.34);");

        this.setLayoutX(0);
        this.setLayoutY(0);

        int rows = 5;
        int cols = 9;

        // Créer et ajouter les cellules
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                plantCasesView[row][col] = createCell(width / cols, height / rows, partie.getOneRow(row).getPlantCase(col));
                this.add(plantCasesView[row][col], col, row);
            }
        }
    }

    private CellView createCell(double cellWidth, double cellHeight, PlantCase plantCase) {
        CellView cell = new  CellView(plantCase);
        cell.setPrefSize(cellWidth, cellHeight);
        cell.setStyle("-fx-border-color: rgba(0,0,0,0.76); -fx-border-width: 1;"); // Bordure pour visualiser les cellules
        return cell;
    }

    public void update() {
        for (CellView[] row : plantCasesView) {
            for (CellView cell : row) {
                cell.update();
            }
        }
    }

    public CellView[][] getPlantCasesView() {
        return plantCasesView;
    }
}
