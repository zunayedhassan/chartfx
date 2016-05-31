/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXListItemVertical extends VBox {
    public String Text = null;

    public ChartFXListItemVertical(String text, String imageLocation) {
        super(5);
        this.Text = text;
        
        super.setAlignment(Pos.CENTER);
        
        super.getChildren().addAll(
                new ImageView(new Image(this.getClass().getResourceAsStream(imageLocation))),
                new Label(text)
        );
    }
    
    @Override
    public String toString() {
        return ("ChartFXListViewItem: " + this.Text);
    }
}
