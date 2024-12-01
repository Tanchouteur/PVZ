package fr.tanchou.pvz.game.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CaseVue extends Pane {

    private final int rowIndex;
    private final double columnIndex;
    private int x, y;

    public CaseVue(double columnIndex, int rowIndex) {

        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.x = (int) (columnIndex * 150) - 10;
        this.y = rowIndex + 25;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: rgba(0, 0, 0, 0.5);");
        this.setPrefSize(149, 149);
    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public double getColumnIndex() {
        return columnIndex;
    }
}
