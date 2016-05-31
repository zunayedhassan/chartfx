/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import com.sun.net.httpserver.Filter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import java.util.Set;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXChartPropertiesDialogView extends Dialog {
    public TabPane PropertiesTabPane = new TabPane();
    public Tab GeneralTab = new Tab("Appearances");
    public Tab DataTab = new Tab("Edit Data");
    public Chart CurrentChart = null;
    public VBox GeneralTabContent = new VBox(5);
    
    public ChartFXChartPropertiesDialogView(ChartFXDialogView.CHART_TYPE chartType, Chart chart) {
        String chartName = "";
        String icon = "chart-dialog-icon.png";
        
        switch (chartType) {
            case AREA_CHART:
                chartName = "Area Chart";
                icon = "Actions-office-chart-area-icon.png";
                this.CurrentChart = (AreaChart) chart;
                break;
                
            case AREA_CHART_STACKED:
                chartName = "Area Chart Stacked";
                icon = "Actions-office-chart-area-stacked-icon.png";
                this.CurrentChart = (StackedAreaChart) chart;
                break;

            case BAR_CHART_HORIZONTAL:
                chartName = "Bar Chart Horizontal";
                icon = "Actions-office-chart-bar-icon-1.png";
                this.CurrentChart = (BarChart) chart;
                break;

            case BAR_CHART_STACKED:
                chartName = "Stacked Chart";
                icon = "Actions-office-chart-bar-stacked-icon.png";
                this.CurrentChart = (StackedBarChart) chart;
                break;

            case BAR_CHART_VERTICAL:
                chartName = "Bar Chart Vertical";
                icon = "Actions-office-chart-bar-icon.png";
                this.CurrentChart = (BarChart) chart;
                break;

            case BUBBLE_CHART:
                chartName = "Bubble Chart";
                icon = "Actions-office-chart-bubble-icon.png";
                this.CurrentChart = (BubbleChart) chart;
                break;

            case LINE_CHART:
                chartName = "Line Chart";
                icon = "Actions-office-chart-line-stacked-icon.png";
                this.CurrentChart = (LineChart) chart;
                break;

            case PIE_CHART:
                chartName = "Pie Chart";
                icon = "Actions-office-chart-pie-icon.png";
                this.CurrentChart = (PieChart) chart;
                break;

            case SCATTERED_CHART:
                chartName = "Scattered Chart";
                icon = "Actions-office-chart-scatter-icon.png";
                this.CurrentChart = (ScatterChart) chart;
                break;
        }
        
        super.setTitle("Edit " + chartName);
        super.setHeaderText(super.getTitle());
        
        super.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("icons/" + icon))));
        super.initStyle(StageStyle.UTILITY);
        
        super.getDialogPane().setContent(this.PropertiesTabPane);
        
        super.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        this.PropertiesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.PropertiesTabPane.getTabs().addAll(this.DataTab, this.GeneralTab);
        
        this.GeneralTabContent.setPadding(new Insets(8));
        this.GeneralTab.setContent(this.GeneralTabContent);
        
        this.GeneralTabContent.getChildren().addAll(
                // Title
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Title"),
                    new TextField(this.CurrentChart.getTitle()),
                    new ChartFXItemPositionChoiceBox(this.CurrentChart.getTitleSide()),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/format-text-color.png"))))
                }),
                
                // Legends
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Legends"),
                    new ChartFXItemPositionChoiceBox(this.CurrentChart.getLegendSide()),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/format-text-color.png")))),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/fill-color.png"))))
                }),
                
                // Background
                new ChartFXAppearanceItem(new Control[] {
                    new Label("Background"),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/fill-color.png"))))
                })
        );
        
        // Is title visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(0)).setSelected(((Label) CurrentChart.lookupAll(".chart-title").toArray()[0]).getOpacity() > 0 ? true : false);
        ((TextField) ((ChartFXAppearanceItem) GeneralTabContent.getChildren().get(0)).getChildren().get(1)).setDisable(((Label) CurrentChart.lookupAll(".chart-title").toArray()[0]).getOpacity() > 0 ? false : true);
        
        // Title Color
        Color chartTitleColor = (Color) ((Label) CurrentChart.lookupAll(".chart-title").toArray()[0]).getTextFill();
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(3)).SetColor(chartTitleColor);

        // Is legend visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(0)).setSelected(CurrentChart.legendVisibleProperty().get());
        
        // Legend text color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(2)).SetColor((Color) ((Label) CurrentChart.lookup(".chart-legend-item")).getTextFill());

        // Legend Background Color
        String legendFillColor = ((Pane) CurrentChart.lookup(".chart-legend")).getBackground().getFills().get(0).getFill().toString();
        legendFillColor = "#" + legendFillColor.substring(2, legendFillColor.length() - 2);
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(3)).SetColor(Color.web(legendFillColor));
        
        // Background Color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(2)).getChildren().get(1)).SetColor(Color.WHITE);
        
        // Events
        // Is Title Visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(0)).selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                Label chartTitle = (Label) CurrentChart.lookupAll(".chart-title").toArray()[0];
                
                if (isSelected) {
                    chartTitle.setOpacity(1);
                    chartTitle.setPrefHeight(-1);
                }
                else {
                    chartTitle.setOpacity(0);
                    chartTitle.setPrefHeight(0);
                }
                
                ((TextField) ((ChartFXAppearanceItem) GeneralTabContent.getChildren().get(0)).getChildren().get(1)).setDisable(!isSelected);
            }
        });
        
        // Title
        ((TextField) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(1)).textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    CurrentChart.setTitle(newValue);
                }
            }
        });
        
        // Title Side
        ((ChartFXItemPositionChoiceBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(2)).getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null) {
                    switch (newValue.intValue()) {
                        case 0:
                            CurrentChart.setTitleSide(Side.TOP);
                            break;
                            
                        case 1:
                            CurrentChart.setTitleSide(Side.RIGHT);
                            break;
                            
                        case 2:
                            CurrentChart.setTitleSide(Side.BOTTOM);
                            break;
                            
                        case 3:
                            CurrentChart.setTitleSide(Side.LEFT);
                            break;
                    }
                }
            }
        });
        
        // Title Color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(0)).getChildren().get(3)).CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (newValue != null) {
                    ((Label) CurrentChart.lookupAll(".chart-title").toArray()[0]).setTextFill(newValue);
                }
            }
        });
        
        // Is legend visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(0)).selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                CurrentChart.setLegendVisible(newValue);
            }
        });
        
        // Legend side
        ((ChartFXItemPositionChoiceBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(1)).getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null) {
                    switch (newValue.intValue()) {
                        case 0:
                            CurrentChart.setLegendSide(Side.TOP);
                            break;
                            
                        case 1:
                            CurrentChart.setLegendSide(Side.RIGHT);
                            break;
                            
                        case 2:
                            CurrentChart.setLegendSide(Side.BOTTOM);
                            break;
                            
                        case 3:
                            CurrentChart.setLegendSide(Side.LEFT);
                            break;
                    }
                }
            }
        });
        
        // Legend Text Color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(2)).CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (newValue != null) {
                    Set<Node> nodes = CurrentChart.lookupAll(".chart-legend-item");
                    
                    for (Node node : nodes) {
                        Label label = (Label) node;
                        label.setTextFill(newValue);
                    }
                }
            }
        });
        
        // Legend Fill Color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(1)).getChildren().get(3)).CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (newValue != null) {
                    ((Pane) CurrentChart.lookup(".chart-legend")).setBackground(new Background(new BackgroundFill(newValue, new CornerRadii(5), Insets.EMPTY)));
                }
            }
        });
        
        // Background color
        ((ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(2)).getChildren().get(1)).CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (newValue != null) {
                    CurrentChart.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });
    }
}
