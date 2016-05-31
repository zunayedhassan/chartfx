/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXItemPositionChoiceBox extends ChoiceBox {
    public ChartFXItemPositionChoiceBox(Side side) {
        super.setItems(FXCollections.observableArrayList(
                "Top", "Right", "Bottom", "Left"
        ));
        
        switch (side) {
            case TOP:
                super.getSelectionModel().select(0);
                break;
                
            case RIGHT:
                super.getSelectionModel().select(1);
                break;
                
            case BOTTOM:
                super.getSelectionModel().select(2);
                break;
                
            case LEFT:
                super.getSelectionModel().select(3);
                break;
        }
    }
}
