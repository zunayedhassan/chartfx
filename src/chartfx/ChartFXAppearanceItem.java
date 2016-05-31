/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXAppearanceItem extends HBox {
    public ChartFXAppearanceItem(Control[] controls) {
        super(5);
        super.setPadding(new Insets(5));
        
        for (Control control : controls) {
            super.getChildren().add(control);
        }
        
        super.setAlignment(Pos.CENTER_LEFT);
    }
}
