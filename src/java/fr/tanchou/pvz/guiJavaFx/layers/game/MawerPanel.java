package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.guiJavaFx.props.MowerView;
import javafx.scene.layout.Pane;

public class MawerPanel extends Pane {
    private final MowerView[] mowerViewsArray = new MowerView[5];
    private final Row[] rows;
    public MawerPanel(double prefWidth, double prefHeight, Row[] rows) {
        super();
        this.rows = rows;
        this.setPrefSize(prefWidth, prefHeight);
        this.setLayoutX(0);
        this.setLayoutY(0);

        for (Row row : rows){
            if (row.getMower() != null){
                mowerViewsArray[row.getRowIndex()] = new MowerView(row.getMower());
                this.getChildren().add(mowerViewsArray[row.getRowIndex()]);
            }
        }
    }

    public void update(){
        for (Row row : rows){
            if (row.getMower() == null){
                this.getChildren().remove(mowerViewsArray[row.getRowIndex()]);
            }
        }
    }
}
