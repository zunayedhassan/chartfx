/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import com.sun.webkit.BackForwardList;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Path;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXSeriesItem extends RadioButton {
    public class ChartFXXYChartTableData {
        private SimpleStringProperty _xColumn = null;
        private SimpleStringProperty _yColumn = null;
        private SimpleStringProperty _zColumn = null;
        
        public ChartFXXYChartTableData(String xColumn, String yColumn) {
            this._xColumn = new SimpleStringProperty(xColumn);
            this._yColumn = new SimpleStringProperty(yColumn);
            
            
            // Events
            // X Column
            this._xColumn.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if ((_chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) ||
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) ||
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED)) {
                        
                        String xValue = newValue;
                        double yValue = Double.parseDouble(_yColumn.get());
                        
                        for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                            if (data.getXValue().equals(oldValue) && (((Number) data.getYValue()).doubleValue() == yValue)) {
                                data.setXValue(xValue);
                                data.setYValue(yValue);
                                
                                break;
                            }
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL) {
                        if (IS_DIGIT(newValue.trim())) {
                            double xValue = Double.parseDouble(newValue);
                            String yValue = _yColumn.get();

                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {

                                if (data.getYValue().equals(yValue) && (((Number) data.getXValue()).doubleValue() == Double.parseDouble(oldValue)))  {
                                    data.setXValue(xValue);
                                    data.setYValue(yValue);

                                    break;
                                }
                            }
                        }
                    }
                    else if ((_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART)) {
                        
                        if (IS_DIGIT(newValue.trim())) {
                            double xValue = Double.parseDouble(newValue);
                            double yValue = Double.parseDouble(_yColumn.get());

                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if ((((Number) data.getXValue()).doubleValue() == Double.parseDouble(oldValue)) &&
                                    (((Number) data.getYValue()).doubleValue() == Double.parseDouble(_yColumn.get()))) {

                                    data.setXValue(xValue);
                                    data.setYValue(yValue);

                                    break;
                                }
                            }
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                        if (IS_DIGIT(newValue.trim())) {
                            double xValue = Double.parseDouble(newValue);
                            double yValue = Double.parseDouble(_yColumn.get());
                            double zValue = Double.parseDouble(_zColumn.get());

                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if ((((Number) data.getXValue()).doubleValue() == Double.parseDouble(oldValue)) &&
                                    (((Number) data.getYValue()).doubleValue() == Double.parseDouble(_yColumn.get()))) {

                                    data.setXValue(xValue);
                                    data.setYValue(yValue);
                                    data.setYValue(zValue);

                                    break;
                                }
                            }
                        }
                    }
                }
            });
            
            // Y Column
            this._yColumn.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if ((_chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) ||
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) ||
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED)) {
                        
                        String xValue = _xColumn.get();
                        
                        if (IS_DIGIT(newValue.trim())) {
                            double yValue = Double.parseDouble(newValue);
                        
                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if (data.getXValue().equals(xValue) && (((Number) data.getYValue()).doubleValue() == Double.parseDouble(oldValue))) {
                                    data.setXValue(xValue);
                                    data.setYValue(yValue);

                                    break;
                                }
                            }
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL) {
                        double xValue = Double.parseDouble(_xColumn.get());
                        
                        String yValue = newValue;
                        
                        for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                            if (data.getYValue().equals(oldValue) && (((Number) data.getXValue()).doubleValue() == xValue)) {
                                data.setXValue(xValue);
                                data.setYValue(yValue);
                                
                                break;
                            }
                        }
                    }
                    else if ((_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART)) {
                        
                        double xValue = Double.parseDouble(_xColumn.get());
                        
                        if (IS_DIGIT(newValue.trim())) {
                            double yValue = Double.parseDouble(newValue);
                        
                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if ((((Number) data.getXValue()).doubleValue() == Double.parseDouble(_xColumn.get())) &&
                                    (((Number) data.getYValue()).doubleValue() == Double.parseDouble(oldValue))) {

                                    data.setXValue(xValue);
                                    data.setYValue(yValue);

                                    break;
                                }
                            }
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                        double xValue = Double.parseDouble(_xColumn.get());
                        
                        if (IS_DIGIT(newValue.trim())) {
                            double yValue = Double.parseDouble(newValue);
                            double zValue = Double.parseDouble(_zColumn.get());

                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if ((((Number) data.getXValue()).doubleValue() == Double.parseDouble(_xColumn.get())) &&
                                    (((Number) data.getYValue()).doubleValue() == Double.parseDouble(oldValue))) {

                                    data.setXValue(xValue);
                                    data.setYValue(yValue);
                                    data.setYValue(zValue);

                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
        
        public ChartFXXYChartTableData(String xColumn, String yColumn, String zColumn) {
            this(xColumn, yColumn);
            this._zColumn = new SimpleStringProperty(zColumn);

            // Events
            // Z Column
            this._zColumn.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                        double xValue = Double.parseDouble(_xColumn.get());
                        double yValue = Double.parseDouble(_yColumn.get());
                        
                        if (IS_DIGIT(newValue.trim())) {
                            double zValue = Double.parseDouble(newValue);
                        
                            for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if ((((Number) data.getXValue()).doubleValue() == Double.parseDouble(_xColumn.get())) &&
                                    (((Number) data.getYValue()).doubleValue() == Double.parseDouble(_yColumn.get()))) {

                                    data.setXValue(xValue);
                                    data.setYValue(yValue);
                                    data.setExtraValue(zValue);

                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }

        public String getXColumn() {
            return this._xColumn.get();
        }

        public String getYColumn() {
            return this._yColumn.get();
        }
        
        public String getZColumn() {
            return this._zColumn.get();
        }

        public void setXColumn(String category) {
            this._xColumn.set(category);
        }

        public void setYColumn(String value) {
            this._yColumn.set(value);
        }
        
        public void setZColumn(String value) {
            this._zColumn.set(value);
        }
    }
    
    private XYChart _currentChart = null;
    private AnchorPane _anchorPane = new AnchorPane();
    private TextField _label = null;
    private ChartFXDialogView.CHART_TYPE _chartType = null;
    
    public ColorChooserButton ColorChooserButton = null;
    public VBox DataVBox = new VBox(5);
    public TableView Table = new TableView();
    public XYChart.Series ChartSeries = null;
    public TableColumn FirstColumn = null;
    public TableColumn LastColumn = null;
    public TableColumn ExtraColumn = null;
    public ObservableList<ChartFXXYChartTableData> Data = FXCollections.observableArrayList();
    public TextField XColumnTextField = new TextField();
    public TextField YColumnTextField = new TextField();
    public TextField ZColumnTextField = new TextField();
    public Button AddDataButton = new Button("Add", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-add.png"))));
    public Button RemoveDataButton = new Button("Remove", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-remove.png"))));
    
    public ChartFXSeriesItem(XYChart.Series series, XYChart chart, ChartFXDialogView.CHART_TYPE chartType) {
        this.ChartSeries = series;
        this._currentChart = chart;
        this._label = new TextField(this.ChartSeries.getName());
        this._chartType = chartType;
        
        if (this._chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) {
            this.ColorChooserButton = new ColorChooserButton(new Rectangle(10, 10, ((Path) this.ChartSeries.getNode()).getStroke()));
        }
        else if ((this._chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) ||
                 (this._chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED) ||
                 (this._chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL)) {
            
            for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {
                    Region legendContent = ((Region) ((Label) currentLegendNode).getGraphic());
                    
                    Color legendColor = Color.BLACK;
                    
                    if (((Region) legendContent).getBackground() != null) {
                        if (legendContent.getBackground().getFills().get(0).getFill() instanceof LinearGradient) {
                            legendColor = ((LinearGradient) legendContent.getBackground().getFills().get(0).getFill()).getStops().get(4).getColor();
                        }
                    }
                    
                    this.ColorChooserButton = new ColorChooserButton(new Rectangle(10, 10, legendColor));

                    break;
                }
            }
        }
        else if ((this._chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                 (this._chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED)) {
            
            Color areaColor = (Color) ((Path) ((Group) ChartSeries.getNode()).getChildren().get(0)).getFill();
            areaColor = (areaColor == null) ? Color.BLACK : areaColor;
            
            this.ColorChooserButton = new ColorChooserButton(new Rectangle(10, 10, Color.rgb((int) (areaColor.getRed() * 255), (int) (areaColor.getGreen() * 255), (int) (areaColor.getBlue() * 255), 1.0)));
        }
        else if (this._chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART) {
            Color legendColor = Color.BLACK;
            
            for (Node node : _currentChart.lookupAll(".chart-legend-item")) {
                Label label = (Label) node;
                
                if (label.getText().equals(ChartSeries.getName())) {
                    if (((Region) label.getGraphic()).getBackground() != null) {
                        legendColor = (Color) ((Region) label.getGraphic()).getBackground().getFills().get(0).getFill();
                    }
                    
                    break;
                }
            }
            
            this.ColorChooserButton = new ColorChooserButton(new Rectangle(10, 10, legendColor));
        }
        else if (this._chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            Color legendColor = Color.BLACK;

            for (Node node : _currentChart.lookupAll(".chart-legend-item")) {
                Label label = (Label) node;
                
                if (label.getText().equals(ChartSeries.getName())) {
                    if (((Region) label.getGraphic()).getBackground() != null) {
                        Region region = (Region) label.getGraphic();
                        
                        RadialGradient radialGradient = (RadialGradient) region.getBackground().getFills().get(0).getFill();
                        Color legendColorWithOpacity = radialGradient.getStops().get(1).getColor();
                        
                        legendColor = Color.web(legendColorWithOpacity.toString().substring(2, legendColorWithOpacity.toString().length() - 2));
                    }
                    
                    break;
                }
            }
            
            
            this.ColorChooserButton = new ColorChooserButton(new Rectangle(10, 10, legendColor));
        }
        
        this._anchorPane.getChildren().addAll(
                this._label,
                this.ColorChooserButton
        );
        
        AnchorPane.setLeftAnchor(this._label, 0.0);
        AnchorPane.setRightAnchor(this.ColorChooserButton, 0.0);
        
        super.setGraphic(this._anchorPane);
        
        this.XColumnTextField.setPromptText(((XYChart) chart).getXAxis().getLabel());
        this.YColumnTextField.setPromptText(((XYChart) chart).getYAxis().getLabel());
        this.ZColumnTextField.setPromptText("Extra Value");
        
        this.XColumnTextField.setPrefWidth(60);
        this.YColumnTextField.setPrefWidth(60);
        this.ZColumnTextField.setPrefWidth(60);
        
        if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            this.DataVBox.getChildren().add(
                new ChartFXAppearanceItem(
                        new Control[] {
                            this.XColumnTextField,
                            this.YColumnTextField,
                            this.ZColumnTextField,
                            this.AddDataButton,
                            this.RemoveDataButton
                        })
            );
        }
        else {
            this.DataVBox.getChildren().add(
                new ChartFXAppearanceItem(
                        new Control[] {
                            this.XColumnTextField,
                            this.YColumnTextField,
                            this.AddDataButton,
                            this.RemoveDataButton
                        })
            );
        }
        
        this.DataVBox.getChildren().add(
                this.Table
        );
        

        this.DataVBox.setPadding(new Insets(0, 0, 0, 10));
        
        this.FirstColumn = new TableColumn(this._currentChart.getXAxis().getLabel());
        this.LastColumn = new TableColumn(this._currentChart.getYAxis().getLabel());
        
        if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            this.ExtraColumn = new TableColumn("Extra Value");
        }
        
        this.FirstColumn.setCellValueFactory(new PropertyValueFactory<ChartFXXYChartTableData, String>("XColumn"));
        this.LastColumn.setCellValueFactory(new PropertyValueFactory<ChartFXXYChartTableData, String>("YColumn"));
        
        if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            this.ExtraColumn.setCellValueFactory(new PropertyValueFactory<ChartFXXYChartTableData, String>("ZColumn"));
            this.Table.getColumns().addAll(this.FirstColumn, this.LastColumn, this.ExtraColumn);
        }
        else {
            this.Table.getColumns().addAll(this.FirstColumn, this.LastColumn);
        }
        
        this.Table.setItems(this.Data);
        this.Table.setEditable(true);
        
        this.FirstColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.LastColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            this.ExtraColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        
        
        for (XYChart.Data data : (ObservableList<XYChart.Data>) this.ChartSeries.getData()) {
            String xValue = data.getXValue().toString();
            String yValue = data.getYValue().toString();
            
            if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                String zValue = data.getExtraValue().toString();
                this.Data.add(new ChartFXXYChartTableData(xValue, yValue, zValue));
            }
            else {
                this.Data.add(new ChartFXXYChartTableData(xValue, yValue));
            }
        }
        
        // Events
        // Validation
        switch(_chartType) {
            case AREA_CHART:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                break;
                
            case AREA_CHART_STACKED:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                break;

            case BAR_CHART_HORIZONTAL:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                break;

            case BAR_CHART_STACKED:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                break;

            case BAR_CHART_VERTICAL:
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                break;

            case BUBBLE_CHART:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                this.SetTextFieldAsNumberOnly(this.ZColumnTextField);
                break;

            case LINE_CHART:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                break;

            case PIE_CHART:
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                break;

            case SCATTERED_CHART:
                this.SetTextFieldAsNumberOnly(this.XColumnTextField);
                this.SetTextFieldAsNumberOnly(this.YColumnTextField);
                break;
        }
        
        // Table
        this.FirstColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<ChartFXXYChartTableData, String>>() {
                @Override
                public void handle(CellEditEvent<ChartFXXYChartTableData, String> t) {
                    ((ChartFXXYChartTableData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setXColumn(t.getNewValue());
                }
            }
        );
        
        this.LastColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<ChartFXXYChartTableData, String>>() {
                @Override
                public void handle(CellEditEvent<ChartFXXYChartTableData, String> t) {
                    ((ChartFXXYChartTableData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setYColumn(t.getNewValue());
                }
            }
        );
        
        if (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
            this.ExtraColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<ChartFXXYChartTableData, String>>() {
                    @Override
                    public void handle(CellEditEvent<ChartFXXYChartTableData, String> t) {
                        ((ChartFXXYChartTableData) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setZColumn(t.getNewValue());
                    }
                }
            );
        }
        
        // Series Name
        this._label.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ChartSeries.setName(newValue);
            }
        });
        
        // Series Color
        this.ColorChooserButton.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                ((Rectangle) ColorChooserButton.PreviewContent).setFill(newValue);
                String colorAsString = newValue.toString();
                colorAsString = "#" + colorAsString.substring(2, colorAsString.length() - 2);
                
                // Change Series Color
                if (_chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) {
                    ChartSeries.getNode().setStyle("-fx-stroke: " + colorAsString + ";");

                    // Change Markers Color
                    for (XYChart.Data data : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                        BackgroundFill secondBackgroundFill = ((StackPane) data.getNode()).getBackground().getFills().get(1);

                        ((StackPane) data.getNode()).setBackground(new Background(
                                new BackgroundFill(Color.web(colorAsString), new CornerRadii(5), Insets.EMPTY),
                                secondBackgroundFill
                        ));
                    }

                    // Change Legend Color
                    for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                        if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {

                            BackgroundFill secondBackgroundFill = ((Region) ((Label) currentLegendNode).getGraphic()).getBackground().getFills().get(1);

                            ((Region) ((Label) currentLegendNode).getGraphic()).setBackground(new Background(
                                new BackgroundFill(Color.web(colorAsString), new CornerRadii(5), Insets.EMPTY),
                                secondBackgroundFill
                            ));

                            break;
                        }
                    }
                }
                else if ((chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) ||
                         (chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED) ||
                         (chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL)) {
                    // Change the Bar Color
                    for (BarChart.Data data : (ObservableList<BarChart.Data>) ((BarChart.Series) ChartSeries).getData()) {
                        ((StackPane) data.getNode()).setStyle("-fx-bar-fill: " + colorAsString + ";");
                    }
                    
                    // Change the legend color
                    for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                        if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {
                            ((Region) ((Label) currentLegendNode).getGraphic()).setBackground(new Background(new BackgroundFill(Color.web(colorAsString), CornerRadii.EMPTY, Insets.EMPTY)));
                            
                            break;
                        }
                    }
                }
                else if ((chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                         (chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED)) {
                    // Change the Path Area Color
                    ((Path) ((Group) ChartSeries.getNode()).getChildren().get(0)).setFill(Color.rgb((int) (newValue.getRed() * 255), (int) (newValue.getGreen() * 255), (int) (newValue.getBlue() * 255), 0.3));
                    ((Path) ((Group) ChartSeries.getNode()).getChildren().get(1)).setStroke(newValue);
                    
                    for (XYChart.Data currentData : ((ObservableList<XYChart.Data>) ChartSeries.getData())) {
                        ((StackPane) currentData.getNode()).setBorder(new Border(new BorderStroke(newValue, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
                    }
                    
                    // Change Legend Color
                    for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                        if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {

                            BackgroundFill secondBackgroundFill = ((Region) ((Label) currentLegendNode).getGraphic()).getBackground().getFills().get(1);

                            ((Region) ((Label) currentLegendNode).getGraphic()).setBackground(new Background(
                                new BackgroundFill(Color.web(colorAsString), new CornerRadii(5), Insets.EMPTY),
                                secondBackgroundFill
                            ));

                            break;
                        }
                    }
                }
                else if (chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART) {
                    // Change the Path Area Color
                    for (ScatterChart.Data data : (ObservableList<ScatterChart.Data>) ((ScatterChart.Series) ChartSeries).getData()) {
                        StackPane dataNode = (StackPane) data.getNode();
                        
                        BackgroundFill backgroundFill = new BackgroundFill(newValue, dataNode.getBackground().getFills().get(0).getRadii(), dataNode.getBackground().getFills().get(0).getInsets());
                        dataNode.setBackground(new Background(backgroundFill));
                    }
                    
                    // Change Legend Color
                    for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                        if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {
                            Region legendRegion = (Region) ((Label) currentLegendNode).getGraphic();
                            BackgroundFill backgroundFill = new BackgroundFill(newValue, legendRegion.getBackground().getFills().get(0).getRadii(), legendRegion.getBackground().getFills().get(0).getInsets());
                            legendRegion.setBackground(new Background(backgroundFill));
                            
                            break;
                        }
                    }
                }
                else if (chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                    RadialGradient radialGradient = null;
                    
                    // Change Legend Color
                    Region region = null;
                    
                    for (Node node : _currentChart.lookupAll(".chart-legend-item")) {
                        Label label = (Label) node;

                        if (label.getText().equals(ChartSeries.getName())) {
                            if (((Region) label.getGraphic()).getBackground() != null) {
                                region = (Region) label.getGraphic();
                                
                                Color color1 = Color.web(newValue.toString().substring(2, newValue.toString().length() - 2) + "b3");
                                Color color2 = Color.hsb(newValue.getHue(), CLAMP(newValue.getSaturation() - 0.18), CLAMP(newValue.getBrightness() + 0.29));
                                color2 = Color.web(color2.toString().substring(2, color2.toString().length() - 2) + "b3");
                                 
                                ArrayList<Stop> stops = new ArrayList<Stop>();
                                stops.add(new Stop(0.8, color1));
                                stops.add(new Stop(0.2, color2));
                                
                                radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, stops);

                                region.setBackground(new Background(new BackgroundFill(radialGradient, region.getBackground().getFills().get(0).getRadii(), Insets.EMPTY)));
                            }

                            break;
                        }
                    }
                    
                    // Change Data Node Color
                    for (BubbleChart.Data data : (ObservableList<BubbleChart.Data>) ChartSeries.getData()) {
                        ((StackPane) data.getNode()).setBackground(new Background(new BackgroundFill(radialGradient, region.getBackground().getFills().get(0).getRadii(), Insets.EMPTY)));
                    }
                }
            }
        });
        
        // Add Data Button
        this.AddDataButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((XColumnTextField.getText().trim().length() > 0) && (YColumnTextField.getText().trim().length() > 0)) {
                    
                    ChartFXDialogView.CHART_TYPE chartType = _chartType;
                    
                    if ((chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) ||
                        (chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) ||
                        (chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED) ) {
                        
                        String xValue = XColumnTextField.getText().trim();
                        double yValue = Double.parseDouble(YColumnTextField.getText().trim());
                        
                        XYChart.Data newData = new XYChart.Data(xValue, yValue);
                        ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(newData);
                        
                        Data.add(new ChartFXXYChartTableData(xValue, Double.toString(yValue))); 
                    }
                    else if (chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL) {
                        
                        double xValue = Double.parseDouble(XColumnTextField.getText().trim());
                        String yValue = YColumnTextField.getText().trim();
                        
                        XYChart.Data newData = new XYChart.Data(xValue, yValue);
                        ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(newData);
                        
                        Data.add(new ChartFXXYChartTableData(Double.toString(xValue), yValue));
                    }
                    else if ((chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                             (chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED) ||
                             (chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART)) {
                        
                        double xValue = Double.parseDouble(XColumnTextField.getText().trim());
                        double yValue = Double.parseDouble(YColumnTextField.getText().trim());
                        
                        XYChart.Data newData = new XYChart.Data(xValue, yValue);
                        ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(newData);
                        
                        Data.add(new ChartFXXYChartTableData(Double.toString(xValue), Double.toString(yValue)));

                        Background legendBackground = null;
                        
                        for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                            if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {
                                legendBackground = ((Region) ((Label) currentLegendNode).getGraphic()).getBackground();

                                break;
                            }
                        }
                        
                        ((StackPane) newData.getNode()).setBackground(legendBackground);
                    }
                    else if (chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART) {
                        
                        double xValue = Double.parseDouble(XColumnTextField.getText().trim());
                        double yValue = Double.parseDouble(YColumnTextField.getText().trim());
                        double zValue = Double.parseDouble(ZColumnTextField.getText().trim());
                        
                        XYChart.Data newData = new XYChart.Data(xValue, yValue, zValue);
                        ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(newData);
                        
                        Data.add(new ChartFXXYChartTableData(Double.toString(xValue), Double.toString(yValue), Double.toString(zValue)));

                        Background legendBackground = null;
                        
                        for (Node currentLegendNode : _currentChart.lookupAll(".chart-legend-item")) {
                            if (((Label) currentLegendNode).getText().equals(ChartSeries.getName())) {
                                legendBackground = ((Region) ((Label) currentLegendNode).getGraphic()).getBackground();

                                break;
                            }
                        }
                        
                        ((StackPane) newData.getNode()).setBackground(legendBackground);
                        
                        ZColumnTextField.clear();
                    }
                    
                    XColumnTextField.clear();
                    YColumnTextField.clear();
                }
            }
        });
        
        // Remove Data Button
        this.RemoveDataButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChartFXXYChartTableData tableData = (ChartFXXYChartTableData) Table.getSelectionModel().getSelectedItem();
                
                if (tableData != null) {
                    // Line Chart or BAR Chart (Vertical)
                    if ((_chartType == ChartFXDialogView.CHART_TYPE.LINE_CHART) ||
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_VERTICAL) || 
                        (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_STACKED)) {
                        
                        for (XYChart.Data currentChartData : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                            if (tableData.getXColumn().equals(currentChartData.getXValue().toString()) && ((Number) currentChartData.getYValue()).doubleValue() == Double.parseDouble(tableData.getYColumn())) {
                                Data.remove(tableData);
                                ((ObservableList<XYChart.Data>) ChartSeries.getData()).clear();
                                break;
                            }
                        }
                        
                        for (ChartFXXYChartTableData tempTableData : Data) {
                            ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(new XYChart.Data(tempTableData.getXColumn(), Double.parseDouble(tempTableData.getYColumn())));
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.BAR_CHART_HORIZONTAL) {
                        
                        for (XYChart.Data currentChartData : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                            if (tableData.getYColumn().equals(currentChartData.getYValue().toString()) && ((Number) currentChartData.getXValue()).doubleValue() == Double.parseDouble(tableData.getXColumn())) {
                                Data.remove(tableData);
                                ((ObservableList<XYChart.Data>) ChartSeries.getData()).clear();
                                break;
                            }
                        }
                        
                        for (ChartFXXYChartTableData tempTableData : Data) {
                            ((ObservableList<XYChart.Data>) ChartSeries.getData()).add(new XYChart.Data(Double.parseDouble(tempTableData.getXColumn()), tempTableData.getYColumn()));
                        }
                        
                    }
                    else if ((_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.SCATTERED_CHART) ||
                             (_chartType == ChartFXDialogView.CHART_TYPE.BUBBLE_CHART)) {
                        
                        for (XYChart.Data currentChartData : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                            if ((((Number) currentChartData.getXValue()).doubleValue() == Double.parseDouble(tableData.getXColumn())) &&
                                (((Number) currentChartData.getYValue()).doubleValue() == Double.parseDouble(tableData.getYColumn()))) {
                                
                                Data.remove(tableData);
                                ((ObservableList<XYChart.Data>) ChartSeries.getData()).remove(currentChartData);
                                
                                break;
                            }
                        }
                    }
                    else if (_chartType == ChartFXDialogView.CHART_TYPE.AREA_CHART_STACKED) {
                        if (ChartSeries.getData().size() > 1) {
                            for (XYChart.Data currentChartData : (ObservableList<XYChart.Data>) ChartSeries.getData()) {
                                if (tableData.getXColumn().equals(((Number) currentChartData.getXValue()).toString()) &&
                                    tableData.getYColumn().equals(((Number) currentChartData.getYValue()).toString())) {

                                    Data.remove(tableData);
                                    ((ObservableList<XYChart.Data>) ChartSeries.getData()).remove(currentChartData);

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
    public static double CLAMP(double value) {
        return ((value < 0) ? 0 : ((value > 1) ? 1 : value));
    }
    
    public static boolean IS_DIGIT(String value) {
        boolean isDigit = true;
        int totalNumberOfDot = 0;
                    
        for (char character : value.toCharArray()) {
            boolean isNumber = ((character >= '0') && (character <= '9'));

            if (!isNumber) {
                isDigit &= (character == '.');
                totalNumberOfDot++;
                
                if (totalNumberOfDot > 1) {
                    return false;
                }
            }
        }
        
        return isDigit;
    }
    
    public void SetTextFieldAsNumberOnly(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!IS_DIGIT(newValue)) {
                    textField.setText(oldValue);
                }
            }
        });
    }
}
