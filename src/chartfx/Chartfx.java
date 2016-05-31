/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import java.util.Optional;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author Zunayed Hassan
 */
public class Chartfx extends Application {
    BorderPane root = new BorderPane();
    Button insertChartWizerdButton = new Button("Insert Chart");
    StackPane pane = new StackPane();
    
    @Override
    public void start(Stage primaryStage) {
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(this.insertChartWizerdButton);
        
        this.root.setTop(toolBar);
        
        this.pane.setMaxSize(400, 400);
        this.root.setCenter(this.pane);
        
        Scene scene = new Scene(this.root, 800, 600);
        
        primaryStage.setTitle("ChartFX Example");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Events
        insertChartWizerdButton.setOnAction(new EventHandler<ActionEvent>() {
            ChartFXChartPane chart = null;
            
            @Override
            public void handle(ActionEvent event) {
                ChartFXDialogView dialog = new ChartFXDialogView("Insert Chart", "Chart Wizerd");                
                Optional<ChartFXDialogView.CHART_TYPE> result = dialog.showAndWait();
                
                // Events
                result.ifPresent(new Consumer<ChartFXDialogView.CHART_TYPE>() {
                    @Override
                    public void accept(ChartFXDialogView.CHART_TYPE chartType) {
                        chart = new ChartFXChartPane(chartType);
                        pane.getChildren().add(chart);
                    }
                });
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
