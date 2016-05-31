/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Path;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXXYChartPropertiesDialogView extends ChartFXChartPropertiesDialogView {
    public VBox MainVBox = new VBox(5);
    public VBox ChartSeriesVBox = new VBox(5);
    public ToggleGroup SeriesToggleGroup = new ToggleGroup();
    public Button AddSeriesButton = new Button("Add a New Series");
    public Button RemoveSeriesButton = new Button("Remove Series");
    
    public ChartFXXYChartPropertiesDialogView(ChartFXDialogView.CHART_TYPE chartType, Chart chart) {
        super(chartType, chart);   
        
        BorderPane borderPane = new BorderPane();
        super.DataTab.setContent(borderPane);
        
        borderPane.setTop(this.MainVBox);
        
        this.MainVBox.getChildren().addAll(
                new ChartFXAppearanceItem(new Control[] {
                    new Label("X Axis Label"),
                    new TextField(((XYChart) super.CurrentChart).getXAxis().getLabel())
                }),
                
                new ChartFXAppearanceItem(new Control[] {
                    new Label("Y Axis Label"),
                    new TextField(((XYChart) super.CurrentChart).getYAxis().getLabel())
                }),
                
                new ChartFXAppearanceItem(new Control[] {
                    this.AddSeriesButton,
                    this.RemoveSeriesButton
                })
        );
        
        
        BorderPane editDataContentBorderPane = new BorderPane();
        borderPane.setCenter(editDataContentBorderPane);
        
        this.ChartSeriesVBox.setPadding(new Insets(5));
        
        ScrollPane scrollPane = new ScrollPane();
        editDataContentBorderPane.setLeft(scrollPane);
        scrollPane.setContent(this.ChartSeriesVBox);

        for (XYChart.Series series : (ObservableList<XYChart.Series>) ((XYChart) super.CurrentChart).getData()) {
            ChartFXSeriesItem item = new ChartFXSeriesItem(series, (XYChart) super.CurrentChart, chartType);
            item.setToggleGroup(this.SeriesToggleGroup);
            this.ChartSeriesVBox.getChildren().add(item);
        }
        
        
        // X Axis Label
        TextField xAxisLabel = (TextField) (((ChartFXAppearanceItem) this.MainVBox.getChildren().get(0)).getChildren().get(1));
        
        // Y Axis Label
        TextField yAxisLabel = (TextField) (((ChartFXAppearanceItem) this.MainVBox.getChildren().get(1)).getChildren().get(1));
        
        
        Path gridLines = (Path) ((XYChart) super.CurrentChart).lookup(".chart-vertical-grid-lines");
        
        super.GeneralTabContent.getChildren().addAll(
                // Tick Label on X Axis
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("X Axis Tick Label"),
                    new Spinner(0, 100, ((XYChart) CurrentChart).getXAxis().getTickLabelGap(), 1),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/format-stroke-color.png"))))
                }),
                
                // Tick Label on Y Axis
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Y Axis Tick Label"),
                    new Spinner(0, 100, ((XYChart) CurrentChart).getYAxis().getTickLabelGap(), 1),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/format-stroke-color.png"))))
                }),
                
                // Tick Mark on X Axis
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Show X Axis Tick Mark")
                }),
                
                // Tick Mark on Y Axis
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Show Y Axis Tick Mark")
                }),
                
                // Grid Line
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Grid Line"),
                    new Spinner(1, 5, gridLines.getStrokeWidth(), 0.25),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/format-stroke-color.png"))))
                }),
                
                // Alternate Row
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Alterning Row"),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/fill-color.png"))))
                }),
                
                // Grid Background
                new ChartFXAppearanceItem(new Control[] {
                    new Label("Grid Background"),
                    new ColorChooserButton(new ImageView(new Image(this.getClass().getResourceAsStream("icons/fill-color.png"))))
                })
        );
        
        
        // Tick Label on X Axis
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(0)).setSelected(((XYChart) super.CurrentChart).getXAxis().isTickLabelsVisible());
        
        // X Label Gap
        Spinner xLabelGapSpinner = (Spinner) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(1);
        xLabelGapSpinner.setEditable(true);
        
        // X Label Color
        ColorChooserButton xLabelColorChooserButton = (ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(2);
        xLabelColorChooserButton.SetColor((Color) ((XYChart) super.CurrentChart).getXAxis().getTickLabelFill());
        
        // Tick Label on Y Axis
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(4)).getChildren().get(0)).setSelected(((XYChart) super.CurrentChart).getYAxis().isTickLabelsVisible());
        
        Spinner yLabelGapSpinner = (Spinner) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(4)).getChildren().get(1);
        yLabelGapSpinner.setEditable(true);

        // Y Label Color
        ColorChooserButton yLabelColorChooserButton = (ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(4)).getChildren().get(2);
        yLabelColorChooserButton.SetColor((Color) ((XYChart) super.CurrentChart).getYAxis().getTickLabelFill());
        
        // Show tick marks on X axis
        CheckBox xAxisTickMarkCheckbox = (CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(5)).getChildren().get(0);
        xAxisTickMarkCheckbox.setSelected(((XYChart) super.CurrentChart).getXAxis().isTickMarkVisible());
        
        // Show tick marks on Y axis
        CheckBox yAxisTickMarkCheckbox = (CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(6)).getChildren().get(0);
        yAxisTickMarkCheckbox.setSelected(((XYChart) super.CurrentChart).getYAxis().isTickMarkVisible());
        
        
        // Grid Line
        final double checkBoxWidth = 105;

        CheckBox gridLineCheckbox = (CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(7)).getChildren().get(0);
        
        gridLineCheckbox.setMinWidth(checkBoxWidth);
        gridLineCheckbox.setSelected(((XYChart) super.CurrentChart).getVerticalGridLinesVisible());
        
        // Grid Line Width
        Spinner gridLineWidthSpinner = (Spinner) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(7)).getChildren().get(1);
        
        
        // Grid Line Color
        ColorChooserButton gridLineCheckboxColorButton = (ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(7)).getChildren().get(2);

        gridLineCheckboxColorButton.SetColor((Color) gridLines.getStroke());
        
        // Alternate Row
        Path alternateRow = (Path) ((XYChart) super.CurrentChart).lookup(".chart-alternative-row-fill");
        
        CheckBox alternateRowCheckBox = (CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(8)).getChildren().get(0);
        ColorChooserButton alternateRowColorButton = (ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(8)).getChildren().get(1);
        
        alternateRowCheckBox.setSelected(((Color) alternateRow.getFill()) == null ? false : true);
        alternateRowColorButton.SetColor(((Color) alternateRow.getFill()) == null ? Color.WHITE : (Color) alternateRow.getFill());
        
        // Chart plot background color
        String chartPlotBackgroundColorAsString = ((Region) ((XYChart) super.CurrentChart).lookup(".chart-plot-background")).getBackground().getFills().get(0).getFill().toString();
        chartPlotBackgroundColorAsString = chartPlotBackgroundColorAsString.substring(2, chartPlotBackgroundColorAsString.length() - 2);
        Color chartPlotBackgroundColor = Color.web("#" + chartPlotBackgroundColorAsString);
        
        ColorChooserButton chartPlotColorButton = (ColorChooserButton) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(9)).getChildren().get(1);
        chartPlotColorButton.SetColor(chartPlotBackgroundColor);
        
        // Events
        // General Properties
        // Is X Tick Label
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(0)).selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                ((XYChart) CurrentChart).getXAxis().setTickLabelsVisible(isSelected);
            }
        });
        
        // X Label Gap
        xLabelGapSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null) {
                    ((XYChart) CurrentChart).getXAxis().setTickLabelGap(newValue.doubleValue());
                }
            }
        });
        
        // X Label Color
        xLabelColorChooserButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                ((XYChart) CurrentChart).getXAxis().setTickLabelFill(newValue);
            }
        });
        
        // Is Y Tick Label
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(4)).getChildren().get(0)).selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                ((XYChart) CurrentChart).getYAxis().setTickLabelsVisible(isSelected);
            }
        });
        
        // Y Label Gap
        yLabelGapSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null) {
                    ((XYChart) CurrentChart).getYAxis().setTickLabelGap(newValue.doubleValue());
                }
            }
        });
        
        // Y Label Color
        yLabelColorChooserButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                ((XYChart) CurrentChart).getYAxis().setTickLabelFill(newValue);
            }
        });

        
        // Show tick marks on X axis
        xAxisTickMarkCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                ((XYChart) CurrentChart).getXAxis().setTickMarkVisible(isSelected);
                
                Path minorAxis = (Path) ((Axis) ((XYChart) CurrentChart).getXAxis()).lookup(".axis-minor-tick-mark");
                
                if (minorAxis != null) {
                    if (isSelected) {
                        minorAxis.setOpacity(1);
                    }
                    else {
                        minorAxis.setOpacity(0);
                    }
                }
            }
        });
        
        // Show tick marks on Y axis
        yAxisTickMarkCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                ((XYChart) CurrentChart).getYAxis().setTickMarkVisible(isSelected);
                
                Path minorAxis = (Path) ((Axis) ((XYChart) CurrentChart).getYAxis()).lookup(".axis-minor-tick-mark");
                
                if (minorAxis != null) {
                    if (isSelected) {
                        minorAxis.setOpacity(1);
                    }
                    else {
                        minorAxis.setOpacity(0);
                    }
                }
            }
        });
        
        
        // Is Grid line
        gridLineCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                ((XYChart) CurrentChart).setVerticalGridLinesVisible(isSelected);
                ((XYChart) CurrentChart).setHorizontalGridLinesVisible(isSelected);
            }
        });
        
        // Grid Line Width
        gridLineWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Path verticalGridLine = (Path) ((XYChart) CurrentChart).lookup(".chart-vertical-grid-lines");
                verticalGridLine.setStrokeWidth(newValue.doubleValue());
                
                Path horizontalGridLine = (Path) ((XYChart) CurrentChart).lookup(".chart-horizontal-grid-lines");
                horizontalGridLine.setStrokeWidth(newValue.doubleValue());
            }
        });
        
        // Grid Line Color
        gridLineCheckboxColorButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                Path verticalGridLine = (Path) ((XYChart) CurrentChart).lookup(".chart-vertical-grid-lines");
                verticalGridLine.setStroke(newValue);
                
                Path horizontalGridLine = (Path) ((XYChart) CurrentChart).lookup(".chart-horizontal-grid-lines");
                horizontalGridLine.setStroke(newValue);
            }
        });
         
        // Is Alternate Row
        alternateRowCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isSelected) {
                if (isSelected) {
                    alternateRow.setFill(((ColorChooserButton) ((ChartFXAppearanceItem) GeneralTabContent.getChildren().get(8)).getChildren().get(1)).CustomColorPicker.ColorProperty.get());
                }
                else {
                    alternateRow.setFill(null);
                }
            }
        });
        
        // Alternate Row Fill
        alternateRowColorButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if ((newValue != null) && (alternateRow.getFill() != null)) {
                    alternateRow.setFill(newValue);
                }
            }
        });
        
        // Chart plot background color
        chartPlotColorButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                ((Region) ((XYChart) CurrentChart).lookup(".chart-plot-background")).setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        
        
        // Edit Data
        // X Axis Label
        xAxisLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ((XYChart) CurrentChart).getXAxis().setLabel(newValue);
                
                ChartFXSeriesItem toggle = (ChartFXSeriesItem) SeriesToggleGroup.getSelectedToggle();
                
                if (toggle != null) {
                    toggle.FirstColumn.setText(newValue);
                    toggle.XColumnTextField.setPromptText(newValue);
                }
            }
        });
        
        // Y Axis Label
        yAxisLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ((XYChart) CurrentChart).getYAxis().setLabel(newValue);
                
                ChartFXSeriesItem toggle = (ChartFXSeriesItem) SeriesToggleGroup.getSelectedToggle();
                
                if (toggle != null) {
                    toggle.LastColumn.setText(newValue);
                    toggle.YColumnTextField.setPromptText(newValue);
                }
            }
        });
        
        // Add a New Series
        this.AddSeriesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = ((ObservableList<XYChart.Series>) ((XYChart) CurrentChart).getData()).size() + 1;
                
                XYChart.Series series = new XYChart.Series<>("New Series " + index, FXCollections.observableArrayList());
                
                if ((chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED) ||
                    (chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART)) {
                    
                    series.getData().add(new XYChart.Data(0, 0));
                }
                
                ((ObservableList<XYChart.Series>) ((XYChart) CurrentChart).getData()).add(series);
                
                ChartFXSeriesItem item = new ChartFXSeriesItem(series, (XYChart) CurrentChart, chartType);
                item.setToggleGroup(SeriesToggleGroup);
                
                ChartSeriesVBox.getChildren().add(item);
                item.setSelected(true);
            }
        });
        
        
        // Remove Series
        this.RemoveSeriesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (((ChartFXSeriesItem) SeriesToggleGroup.getSelectedToggle()) != null) {
                    ((ObservableList<XYChart.Series>) ((XYChart) CurrentChart).getData()).remove(((ChartFXSeriesItem) SeriesToggleGroup.getSelectedToggle()).ChartSeries);
                    ChartSeriesVBox.getChildren().remove(((ChartFXSeriesItem) SeriesToggleGroup.getSelectedToggle()));
                    
                    editDataContentBorderPane.setCenter(null);
                }
            }
        });
        
        
        // Series Toggle Group
        this.SeriesToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null) {
                    ChartFXSeriesItem toggle = (ChartFXSeriesItem) newValue;
                    
                    editDataContentBorderPane.setCenter(toggle.DataVBox);
                }
            }
        });
        
        ((ChartFXSeriesItem) this.SeriesToggleGroup.getToggles().get(0)).setSelected(true);
    }
}
