package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.guiJavaFx.props.MowerView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Map;

public class MawerPanel extends Pane {
    private final MowerView[] mowerViewsArray = new MowerView[5];
    private final Row[] rows;

    public MawerPanel(double prefWidth, double prefHeight, Row[] rows, Map<String, Image> assetsLoaded) {
        super();
        this.rows = rows;
        this.setPrefSize(prefWidth, prefHeight);
        this.setLayoutX(0);
        this.setLayoutY(0);

        for (Row row : rows){
            if (row.getMower() != null){
                mowerViewsArray[row.getRowIndex()] = new MowerView(row.getMower(), assetsLoaded.get("normal"));
                this.getChildren().add(mowerViewsArray[row.getRowIndex()]);
            }
        }
    }

    public void update(){
        for (Row row : rows){
            if (row.getMower() == null){
                this.getChildren().remove(mowerViewsArray[row.getRowIndex()]);
                continue;
            }
            mowerViewsArray[row.getRowIndex()].update();
        }
    }
}
