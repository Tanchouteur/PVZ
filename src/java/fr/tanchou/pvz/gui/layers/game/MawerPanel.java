package fr.tanchou.pvz.gui.layers.game;

import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.gui.props.MowerView;
import javafx.scene.layout.Pane;

public class MawerPanel extends Pane {
    private final MowerView[] mowerViewsArray = new MowerView[5];

    public MawerPanel(double prefWidth, double prefHeight, Row[] rows) {

        super();
        this.setPrefSize(prefWidth, prefHeight);
        this.setLayoutX(0);
        this.setLayoutY(0);

        for (Row row : rows){
            mowerViewsArray[row.getRowIndex()] = new MowerView(row.getMower());
            this.getChildren().add(mowerViewsArray[row.getRowIndex()]);
        }
    }

    public void update(){
        for (MowerView mowerView : mowerViewsArray){
            if (mowerView.getMower() != null) {
                mowerView.update();
            }else {
                this.getChildren().remove(mowerView);
            }
        }
    }
}
