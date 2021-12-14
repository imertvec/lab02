package yefimov483.figurepaint;

import eu.hansolo.tilesfx.tools.Pixel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.w3c.dom.events.MouseEvent;
import yefimov483.figurepaint.GraphicsHelper.Drawer;
import yefimov483.figurepaint.Figures.CircleCustom;
import yefimov483.figurepaint.Figures.Figure;
import yefimov483.figurepaint.Figures.SquareCustom;
import yefimov483.figurepaint.Figures.TriangleCustom;
import yefimov483.figurepaint.GraphicsHelper.RGB;
import yefimov483.figurepaint.GraphicsHelper.Vec2;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Controller{
    private String[] lst = new String[]{"CircleCustom", "SquareCustom", "TriangleCustom"};
    private String[] algsLst = new String[]{"fill", "sdf", "sdf+rgb"};
    private Figure fig;
    public List<Figure> figs = new ArrayList<>();
    private String selectedName;

    @FXML
    public ComboBox<String> algs;

    @FXML
    public Canvas canvas;

    @FXML
    public ComboBox<String> comboBox;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ListView<String> list;
    @FXML
    public BorderPane borderPane;

    @FXML
    protected void addAction() {
        //add figure on canvas
        switch (comboBox.getValue().toLowerCase()){
            case "circlecustom": fig = new CircleCustom(50, colorPicker.getValue(), canvas);
                break;
            case "squarecustom": fig = new SquareCustom(50, colorPicker.getValue(), canvas);
                break;
            case "trianglecustom": fig = new TriangleCustom(50, colorPicker.getValue(), canvas);
        }

        //add figs in list and their names in other list
        list.getItems().add(fig.name);
        figs.add(fig);
        Drawer.invalidate(canvas, figs);

        list.getSelectionModel().clearSelection();
    }

    @FXML
    protected void render(){
        //bitmap's analogue
        PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

        if(algs.getSelectionModel().getSelectedItem().equals("sdf")){
            Drawer.invalidate(canvas, figs);
            for(int x = 0; x < canvas.getWidth() - 1; x++){
                for(int y = 0; y < canvas.getHeight() - 1; y++){
                    RGB col = new RGB();

                    Vec2 p = new Vec2(x, y);
                    double dMin = 100;
                    for(Figure fig : figs){
                        double d = fig.sdf(p);
                        if(d < dMin) dMin = d;
                    }

                    col = new RGB(dMin / 100, dMin / 100, dMin / 100);


                    pixelWriter.setColor(x, y, col.getColor());
                }
            }
        }
        else if(algs.getSelectionModel().getSelectedItem().equals("fill")){
            Drawer.invalidate(canvas, figs);
            for(int x = 0; x < canvas.getWidth() - 1; x++) {
                for (int y = 0; y < canvas.getHeight() - 1; y++) {
                    for(Figure figure : figs){
                        if(figure.isSelected(x, y)){
                            pixelWriter.setColor(x, y, figure.color.getColor());
                        }
                    }
                }
            }
        }
        else if(algs.getSelectionModel().getSelectedItem().equals("sdf+rgb")){
            Drawer.invalidate(canvas, figs);
            System.out.println("not found");
        }
    }

    @FXML
    protected void deleteAction(){
        for(int i = 0; i < list.getItems().size(); i++){
            if(list.getSelectionModel().isSelected(i)) {
                list.getItems().remove(i);
                figs.remove(i);
                Drawer.invalidate(canvas, figs);
            }
        }

        list.getSelectionModel().clearSelection();
    }

    public void initialize() {
        //default setting for combo
        comboBox.setItems(FXCollections.observableList(Arrays.stream(lst).toList()));
        comboBox.getSelectionModel().selectFirst();

        algs.setItems(FXCollections.observableList(Arrays.stream(algsLst).toList()));
        algs.getSelectionModel().selectFirst();

        //fill canvas with starting
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //figure clicked
        canvas.setOnMouseClicked(event -> {
            //selecting figs with shift
            if(event.isShiftDown()){
                list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                for(Figure x : figs){
                    if(x.isSelected(event.getX(), event.getY())){
                        x.selected = true;
                        list.getSelectionModel().select(figs.indexOf(x));
                        Drawer.invalidate(canvas, figs);
                    }
                }
            }
            else{
                //selecting figs without shift
                for(Figure x : figs){
                    if(x.isSelected(event.getX(), event.getY())){
                        x.selected = true;

                        //set current selected figure in list
                        selectedName = x.name;
                        for(int i = 0; i < list.getItems().size(); i++){
                            if(list.getItems().get(i) == selectedName){
                                list.getSelectionModel().select(i);
                                break;
                            }
                        }
                    }
                    else {
                        x.selected = false;
                    }
                }
                Drawer.invalidate(canvas, figs);
            }

        });

        //figure moved
        canvas.setOnMouseDragged(event ->{
            if(event.isShiftDown()){
                double dx = 0;
                double dy = 0;
                for(Figure x : figs){
                    if(x.isSelected(event.getX(), event.getY()) && x.selected){
                        dx = x.pos.x - event.getX();
                        dy = x.pos.y - event.getY();
                        x.pos.x = event.getX();
                        x.pos.y = event.getY();
                    }
                    if(!x.isSelected(event.getX(), event.getY()) && x.selected){
                        x.pos.x -= dx;
                        x.pos.y -= dy;
                    }

                }
                Drawer.invalidate(canvas, figs);

            }
            else {
                for(Figure x : figs){
                    //if cursor includes in fig and fig was selected
                    if(x.isSelected(event.getX(), event.getY()) && x.name == selectedName){
                        x.pos.x = event.getX();
                        x.pos.y = event.getY();
                        selectedName = x.name;
                        Drawer.invalidate(canvas, figs);
                        break;
                    }
                }
            }

        });

        //change item from list
        list.setOnMouseClicked(event -> {
            list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            if(event.getClickCount() == 2){
                String figName = list.getSelectionModel().getSelectedItem();

                TextInputDialog dialog = new TextInputDialog(figName);
                dialog.setTitle("Changing name");
                dialog.setHeaderText("Enter new name for figure:");
                dialog.setContentText("Name:");
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    for(Figure x : figs){
                        if(x.name.equals(figName)){
                            x.name = name;
                            list.getItems().set(figs.indexOf(x), name);
                            break;
                        }
                    }
                });
            }

            for(Figure x : figs){
                if(x.name.equals(list.getSelectionModel().getSelectedItem())){
                    selectedName = x.name;
                    x.selected = true;
                }
                else {
                    x.selected = false;
                }
                Drawer.invalidate(canvas, figs);
            }
        });
    }
}