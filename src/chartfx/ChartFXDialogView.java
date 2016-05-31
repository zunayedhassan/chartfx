/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXDialogView extends Dialog<ChartFXDialogView.CHART_TYPE> {
    public static enum CHART_TYPE {
        BAR_CHART_VERTICAL  ("bar_chart_vertical"  ),
        BAR_CHART_HORIZONTAL("bar_chart_horizontal"),
        BAR_CHART_STACKED   ("bar_chart_stacked"   ),
        LINE_CHART          ("line_chart"          ),
        PIE_CHART           ("pie_chart"           ),
        AREA_CHART          ("area_chart"          ),
        AREA_CHART_STACKED  ("area_chart_stacked"  ),
        SCATTERED_CHART     ("scattered_chart"     ),
        BUBBLE_CHART        ("bubble_chart"        );
        
        private String _value;
        
        CHART_TYPE(String value) {
            _value = value;
        }
        
        public String Get() {
            return _value;
        }
        
        public static CHART_TYPE GET_VALUE_OF(String value) {
            for (CHART_TYPE chartType : CHART_TYPE.values()) {
                if (chartType.Get().equals(value)) {
                    return chartType;
                }
            }
            
            return null;
        }
    }
    
    public BorderPane ContentPane = new BorderPane();
    public ButtonType OkButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
    public ListView<ChartFXListItemHorizontal> ChartsListView = new ListView<>();
    
    public static ChartFXDialogView.CHART_TYPE CURRENT_CHART_TYPE = CHART_TYPE.BAR_CHART_VERTICAL;
    
    public static String[][] CHART_TYPES = {
        {"Bar Chart", "icons/Actions-office-chart-bar-icon.png"},
        {"Line Chart", "icons/Actions-office-chart-line-stacked-icon.png"},
        {"Pie Chart", "icons/Actions-office-chart-pie-icon.png"},
        {"Area Chart", "icons/Actions-office-chart-area-icon.png"},
        {"Scattered Chart", "icons/Actions-office-chart-scatter-icon.png"}
    };
    
    public ChartFXChartPreviewPane[] ChartFXChartPreviewPanes = {
                // Bar Chart
                new ChartFXChartPreviewPane(
                    new String[][] {
                        { "Vertical", "icons/Actions-office-chart-bar-icon.png", "images/bar-chart-vertical-example.png", CHART_TYPE.BAR_CHART_VERTICAL.Get() },
                        { "Horizontal", "icons/Actions-office-chart-bar-icon-1.png", "images/bar-chart-horizontal-example.png", CHART_TYPE.BAR_CHART_HORIZONTAL.Get() },
                        { "Stacked", "icons/Actions-office-chart-bar-stacked-icon.png", "images/bar-stacked-example.png", CHART_TYPE.BAR_CHART_STACKED.Get() }
                    }),
        
                // Line Chart
                new ChartFXChartPreviewPane(
                        new String[][] {
                            { "Line Chart", "icons/Actions-office-chart-line-stacked-icon.png", "images/line-chart-example.png", CHART_TYPE.LINE_CHART.Get() }
                        }),
        
                // Pie Chart
                new ChartFXChartPreviewPane(
                        new String[][] {
                            { "Pie Chart", "icons/Actions-office-chart-pie-icon.png", "images/pie-chart-example.png", CHART_TYPE.PIE_CHART.Get() }
                        }),
        
                // Area Chart
                new ChartFXChartPreviewPane(
                        new String[][] {
                            { "Area", "icons/Actions-office-chart-area-icon.png", "images/area-chart-example.png", CHART_TYPE.AREA_CHART.Get() },
                            { "Stacked", "icons/Actions-office-chart-area-stacked-icon.png", "images/area-chart-stacked-example.png", CHART_TYPE.AREA_CHART_STACKED.Get() }
                        }),
        
                // Scattered Chart
                new ChartFXChartPreviewPane(
                        new String[][] {
                            { "Scattered", "icons/Actions-office-chart-scatter-icon.png", "images/scatter-chart-example.png", CHART_TYPE.SCATTERED_CHART.Get() },
                            { "Bubble", "icons/Actions-office-chart-bubble-icon.png", "images/bubble-chart-example.png", CHART_TYPE.BUBBLE_CHART.Get() }
                        }),
    };
    
    public ChartFXDialogView(String title, String headerText) {
        super.setTitle(title);
        super.setHeaderText(headerText);
        super.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("icons/chart-dialog-icon.png"))));
        super.initStyle(StageStyle.UTILITY);
        
        super.getDialogPane().setContent(this.ContentPane);
        
        this.ContentPane.setLeft(this.ChartsListView);
        
        for (String[] listData : CHART_TYPES) {
            this.ChartsListView.getItems().add(new ChartFXListItemHorizontal(listData[0], listData[1]));
        }
        
        super.getDialogPane().getButtonTypes().addAll(this.OkButtonType, ButtonType.CANCEL);
        super.setResizable(true);
        
        // Events
        this.ChartsListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null) {
                    ContentPane.setCenter(ChartFXChartPreviewPanes[newValue.intValue()]);
                    CURRENT_CHART_TYPE = CHART_TYPE.GET_VALUE_OF(ChartFXChartPreviewPanes[newValue.intValue()].Data[0][3]);
                }
            }
        });
        
        
        this.setResultConverter(new Callback<ButtonType, CHART_TYPE>() {
            @Override
            public CHART_TYPE call(ButtonType param) {
                if (param == OkButtonType) {
                    return CURRENT_CHART_TYPE;
                }
                
                return null;
            }
        });
        
        this.ChartsListView.getSelectionModel().select(0);        
    }
}
