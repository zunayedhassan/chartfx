/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author Zunayed Hassan
 */
public class ChartFXPieChartPropertiesDialogView extends ChartFXChartPropertiesDialogView {
    public class ChartFXPieChartTableData {
        private SimpleStringProperty _category = null;
        private SimpleStringProperty _value = null;

        public ChartFXPieChartTableData(String category, String value) {
            this._category = new SimpleStringProperty(category);
            this._value = new SimpleStringProperty(value);

            // Events
            this._category.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = Table.getSelectionModel().selectedIndexProperty().get();

                    if (index > -1) {
                        (((PieChart) CurrentChart).dataProperty().get()).get(index).setName(newValue);
                        
                        for (int i = 1; i < CategoryContent.getChildren().size(); i++) {
                            Label categoryLabel = (Label) ((ChartFXAppearanceItem) CategoryContent.getChildren().get(i)).getChildren().get(0);
                            
                            if (categoryLabel.getText().equals(oldValue)) {
                                categoryLabel.setText(newValue);
                                break;
                            }
                        }
                    }
                }
            });

            this._value.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = Table.getSelectionModel().selectedIndexProperty().get();

                    if (index > -1) {
                        (((PieChart) CurrentChart).dataProperty().get()).get(index).setPieValue(Double.parseDouble(newValue));
                    }
                }
            });
        }

        public String getCategory() {
            return this._category.get();
        }

        public String getValue() {
            return this._value.get();
        }

        public void setCategory(String category) {
            this._category.set(category);
        }

        public void setValue(String value) {
            this._value.set(value);
        }
    }

    public TableView Table = new TableView();
    public VBox MainVBox = new VBox(5);
    public TableColumn FirstColumn = new TableColumn("Category");
    public TableColumn LastColumn = new TableColumn("Value");
    public ObservableList<ChartFXPieChartTableData> Data = FXCollections.observableArrayList();
    public VBox CategoryContent = new VBox(5);
    
    public ChartFXPieChartPropertiesDialogView(ChartFXDialogView.CHART_TYPE chartType, Chart chart) {
        super(chartType, chart);
        
        BorderPane borderPane = new BorderPane();
        super.DataTab.setContent(borderPane);
        
        this.FirstColumn.setCellValueFactory(new PropertyValueFactory<ChartFXPieChartTableData, String>("category"));
        this.LastColumn.setCellValueFactory(new PropertyValueFactory<ChartFXPieChartTableData, String>("value"));
        this.Table.getColumns().addAll(this.FirstColumn, this.LastColumn);
        
        borderPane.setCenter(this.MainVBox);
        
        TextField categoryTextField = new TextField();
        categoryTextField.setPromptText("Category");
        
        TextField valueTextField = new TextField();
        valueTextField.setPromptText("Value");
        
        this.MainVBox.getChildren().addAll(
                new ChartFXAppearanceItem(new Control[] {
                    categoryTextField,
                    valueTextField,
                    new Button("Add", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-add.png")))),
                    new Button("Remove", new ImageView(new Image(this.getClass().getResourceAsStream("icons/list-remove.png"))))
                }),
                
                this.Table
        );

        for (PieChart.Data data : ((PieChart) super.CurrentChart).dataProperty().get()) {
            String category = data.getName();
            String value = Double.toString(data.getPieValue());
            
            this.Data.add(new ChartFXPieChartTableData(category, value));
        }

        this.Table.setEditable(true);
        this.Table.setItems(this.Data);
        
        this.FirstColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.LastColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        borderPane.setRight(this.CategoryContent);
        
        ScrollPane scrollPane = new ScrollPane();
        VBox categoriesVBox = new VBox(5);
        scrollPane.setContent(categoriesVBox);
        
        this.CategoryContent.setPadding(new Insets(5));
        this.CategoryContent.getChildren().addAll(new Label("Change Color of Categories:"), scrollPane);
        
        
        for (int i = 0; i < ((PieChart) super.CurrentChart).getData().size(); i++) {
            PieChart.Data currentData = ((PieChart) super.CurrentChart).getData().get(i);
            
            String radialGradientData = ((Region) currentData.getNode()).getBackground().getFills().get(0).getFill().toString();
            Color color = Color.web("#" + radialGradientData.substring(93, radialGradientData.length() - 27));
            
            Rectangle colorChooserButtonPreview = new Rectangle(10, 10, color);
            
            ColorChooserButton colorChooserButton = new ColorChooserButton(colorChooserButtonPreview);
            colorChooserButton.SetColor(color);
            
            Label categoryLabel = new Label(currentData.getName());
            categoryLabel.setTooltip(new Tooltip(currentData.getName()));
            categoryLabel.setMinWidth(100);
            categoryLabel.setMaxWidth(100);
            
            categoriesVBox.getChildren().add(new ChartFXAppearanceItem(new Control[] {
                categoryLabel,
                colorChooserButton
            }));
            
            // Events
            SetOnPieColorChangedEvent(colorChooserButton, currentData);
        }
        
        
        // Label
        super.GeneralTabContent.getChildren().add(
                new ChartFXAppearanceItem(new Control[] {
                    new CheckBox("Label"),
                    new Spinner(1, 100, ((PieChart) CurrentChart).getLabelLineLength(), 1)
                })
        );
        
        // Label length
        ((Spinner) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(1)).setEditable(true);
        
        // Is label visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(0)).setSelected(((PieChart) super.CurrentChart).getLabelsVisible());
        
        // Events
        // Is label visible
        ((CheckBox) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(0)).selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ((PieChart) CurrentChart).setLabelsVisible(newValue);
                ((Spinner) ((ChartFXAppearanceItem) GeneralTabContent.getChildren().get(3)).getChildren().get(1)).setDisable(!newValue);
            }
        });
        
        
        // Label length
        ((Spinner) ((ChartFXAppearanceItem) this.GeneralTabContent.getChildren().get(3)).getChildren().get(1)).valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    ((PieChart) CurrentChart).setLabelLineLength(((Number) newValue).doubleValue());
                }
            }
        });
        
        
        // Table
        this.FirstColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<ChartFXPieChartTableData, String>>() {
                @Override
                public void handle(CellEditEvent<ChartFXPieChartTableData, String> t) {
                    ((ChartFXPieChartTableData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setCategory(t.getNewValue());
                }
            }
        );
        
        this.LastColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<ChartFXPieChartTableData, String>>() {
                @Override
                public void handle(CellEditEvent<ChartFXPieChartTableData, String> t) {
                    ((ChartFXPieChartTableData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setValue(t.getNewValue());
                }
            }
        );
        
        // Value TextField
        ((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(1)).textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    ((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(1)).setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        // Add
        ((Button) ((ChartFXAppearanceItem) this.MainVBox.getChildren().get(0)).getChildren().get(2)).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(0)).getText().trim().length() > 0) && (((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(1)).getText().trim().length() > 0)) {
                    String categoryName = ((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(0)).getText().trim();
                    double value = Double.parseDouble(((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(1)).getText().trim());
                
                    
                    PieChart.Data data = new PieChart.Data(categoryName, value);
                    ((PieChart) CurrentChart).getData().add(data);

                    Data.add(new ChartFXPieChartTableData(data.getName(), Double.toString(data.getPieValue())));

                    ((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(0)).clear();
                    ((TextField) ((ChartFXAppearanceItem) MainVBox.getChildren().get(0)).getChildren().get(1)).clear();

                    Color color = GET_RANDOM_COLOR();                
                    data.getNode().setStyle("-fx-pie-color: " + GET_RANDOM_COOR_AS_STRING(color));

                    SetLegendColor(data, color);

                    Rectangle colorChooserButtonPreview = new Rectangle(10, 10, color);

                    ColorChooserButton colorChooserButton = new ColorChooserButton(colorChooserButtonPreview);
                    colorChooserButton.SetColor(color);

                    Label categoryLabel = new Label(categoryName);
                    categoryLabel.setTooltip(new Tooltip(categoryName));
                    categoryLabel.setMinWidth(100);
                    categoryLabel.setMaxWidth(100);

                    categoriesVBox.getChildren().add(new ChartFXAppearanceItem(new Control[] {
                        categoryLabel,
                        colorChooserButton
                    }));
                    
                    SetOnPieColorChangedEvent(colorChooserButton, data);
                }
            }
        });
        
        // Remove
        ((Button) ((ChartFXAppearanceItem) this.MainVBox.getChildren().get(0)).getChildren().get(3)).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChartFXPieChartTableData data = (ChartFXPieChartTableData) Table.getSelectionModel().getSelectedItem();
                
                if (data != null) {
                    Table.getItems().remove(data);
                    
                    String category = data.getCategory();
                    double value = Double.parseDouble(data.getValue());
                    
                    for (PieChart.Data currentData : ((PieChart) CurrentChart).dataProperty().get()) {
                        if (currentData.getName().equals(category) && (currentData.getPieValue() == value)) {
                            ((PieChart) CurrentChart).dataProperty().get().remove(currentData);
                            
                            break;
                        }
                    }
                    
                    for (int i = 0; i < categoriesVBox.getChildren().size(); i++) {
                        Label categoryLabel = (Label) ((ChartFXAppearanceItem) categoriesVBox.getChildren().get(i)).getChildren().get(0);

                        if (categoryLabel.getText().equals(category)) {
                            categoriesVBox.getChildren().remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }
    
    public void SetOnPieColorChangedEvent(ColorChooserButton button, PieChart.Data data) {
        button.CustomColorPicker.ColorProperty.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (newValue != null) {
                    ((Rectangle) button.PreviewContent).setFill(newValue);
                    data.getNode().setStyle("-fx-pie-color: " + GET_RANDOM_COOR_AS_STRING(newValue));

                    SetLegendColor(data, newValue);
                }
            }
        });
    }
    
    public void SetLegendColor(PieChart.Data data, Color color) {
        Set<Node> legendNodes = super.CurrentChart.lookupAll(".chart-legend-item");

        for (Node currentLegendNode : legendNodes) {
            if (((Label) currentLegendNode).getText().equals(data.getName())) {
                ((Region) ((Label) currentLegendNode).getGraphic()).setBackground(new Background(new BackgroundFill(color, new CornerRadii(60), Insets.EMPTY)));
                break;
            }
        }
    }
    
    public static int GET_RANDOM_INT(int min, int max) {
       Random rand = new Random();
       int randomNum = rand.nextInt((max - min) + 1) + min;

       return randomNum;
   }
   
   public static Color GET_RANDOM_COLOR() {
       Color color = Color.rgb(GET_RANDOM_INT(0, 255), GET_RANDOM_INT(0, 255), GET_RANDOM_INT(0, 255));
       
       return color;
   }
   
   public static String GET_RANDOM_COOR_AS_STRING(Color color) {
       String hexColor = "#" + color.toString().substring(2, color.toString().length() - 2);
       return hexColor;
   }
}
