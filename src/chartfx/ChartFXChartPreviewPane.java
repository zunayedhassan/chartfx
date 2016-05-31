/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXChartPreviewPane extends BorderPane {
    public ListView<ChartFXListItemVertical> ChartItemListView = new ListView<>();
    public ImageView Preview = new ImageView();
    public String[][] Data = null;
    
    public ChartFXChartPreviewPane(String[][] data) {
        this.Data = data;
        
        for (String[] chartItemData : data) {
            this.ChartItemListView.getItems().add(new ChartFXListItemVertical(chartItemData[0], chartItemData[1]));
        }
        
        StackPane previewPane = new StackPane();
        
        super.setCenter(previewPane);
        previewPane.getChildren().add(this.Preview);
        
        if (this.Data.length > 1) {
            this.ChartItemListView.setMaxHeight(100);
            this.ChartItemListView.setOrientation(Orientation.HORIZONTAL);
            super.setTop(this.ChartItemListView);

            // Events
            this.ChartItemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChartFXListItemVertical>() {
                @Override
                public void changed(ObservableValue<? extends ChartFXListItemVertical> observable, ChartFXListItemVertical oldValue, ChartFXListItemVertical newValue) {
                    if (newValue != null) {
                        int index = ChartItemListView.getItems().indexOf(newValue);
                        Preview.setImage(new Image(this.getClass().getResourceAsStream(data[index][2])));
                        ChartFXDialogView.CURRENT_CHART_TYPE = ChartFXDialogView.CHART_TYPE.GET_VALUE_OF(data[index][3]);
                    }
                }
            });
            
            this.ChartItemListView.getSelectionModel().select(0);
        }
        else {
            Preview.setImage(new Image(this.getClass().getResourceAsStream(data[0][2])));
            ChartFXDialogView.CURRENT_CHART_TYPE = ChartFXDialogView.CHART_TYPE.GET_VALUE_OF(data[0][3]);
        }
    }
}
