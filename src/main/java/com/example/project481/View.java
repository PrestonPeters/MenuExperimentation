package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Label;
import javafx.scene.text.*;

import static java.lang.Math.sqrt;

public class View extends StackPane implements Subscriber {
    Canvas canvas;
    GraphicsContext gc;

    MenuItem hovering;
    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems;
    private MenuModeLabel menuModeLabel;
    public View() {
        setFocusTraversable(true);
        menuItems = new ArrayList<>();
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.menuMode = Controller.MenuMode.NONE;
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        draw();

        StackPane.setAlignment(menuModeLabel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(menuModeLabel, new Insets(10));
    }

    public void setUpEvents(Controller controller) {
        this.setOnKeyPressed(controller::handleKeyPressed);
        this.setOnKeyReleased(controller::handleKeyReleased);
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseReleased(controller::handleMouseReleased);
        this.setOnMouseMoved(controller::handleMouseMoved);
        this.setOnMouseDragged(controller::handleMouseDragged);
    }

    public void draw(){
        switch (menuMode) {
            case LINEAR:
                menuModeLabel.setText("Linear menu selected");
                this.getChildren().add(menuModeLabel);

                double itemWidth = ((LinearMenuItem) menuItems.get(0)).getItemWidth();
                double itemHeight = ((LinearMenuItem) menuItems.get(0)).getItemHeight();

                Pane menuBox = new Pane();
                setAlignment(menuBox, Pos.TOP_LEFT);

                for (MenuItem item : menuItems) {
                    Pane tempPane = new Pane();
                    Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
                    if (hovering == item) menuItem.setFill(Color.WHITE);
                    else menuItem.setFill(new Color(0.95, 0.95, 0.95, 1));
                    menuItem.setStroke(Color.BLACK);
                    Label itemLabel = new Label(item.getText());
                    itemLabel.setAlignment(Pos.CENTER);
                    itemLabel.setPrefWidth(itemWidth);
                    itemLabel.setPrefHeight(itemHeight); // Set explicit preferred size for the item label

                    tempPane.getChildren().addAll(menuItem, itemLabel); // Add the item container to the VBox
                    tempPane.setTranslateX(((LinearMenuItem) item).getX());
                    tempPane.setTranslateY(((LinearMenuItem) item).getY());
                    menuBox.getChildren().add(tempPane);
                }

                this.getChildren().add(menuBox); // Add the VBox to the layout
                break;

            case RADIAL:
                menuModeLabel.setText("Radial menu selected.");
                this.getChildren().add(menuModeLabel);

                // The baseItem that is to be added in the center circle is initialized here.
                Pane baseItemPane = new Pane();
                Pane masterPane = new Pane();

                // Now each wedge is built individually so they can properly respond to hit detection
                for (MenuItem item : menuItems) {
                    RadialMenuItem radialItem = (RadialMenuItem) item;

                    // If the current item is the base item, then initializes the base item circle,
                    // but does not add it to the hierarchy. This will be done after all the other
                    // wedges are drawn so that the baseItem appears above the other items in the
                    // z ordering.
                    if (radialItem.isBaseItem()) {
                        Circle baseItem = new Circle(0, 0, radialItem.getBaseItemRadius(),
                                (hovering == radialItem) ? Color.WHITE : new Color(0.95, 0.95, 0.95, 1));
                        baseItem.setStroke(Color.BLACK);
                        baseItem.setStrokeWidth(3);

                        Text baseItemLabel = new Text(radialItem.getText());
                        baseItemLabel.setTranslateX(-baseItemLabel.getLayoutBounds().getWidth() / 2);
                        baseItemLabel.setTranslateY(baseItemLabel.getLayoutBounds().getHeight() / 5);

                        baseItemPane.getChildren().addAll(baseItem, baseItemLabel);
                        baseItemPane.setTranslateX(radialItem.getOriginX());
                        baseItemPane.setTranslateY(radialItem.getOriginY());
                    }

                    // If the item is not a base item, draws its wedge and adds it to the hierarchy.
                    else {
                        double startAngle = (radialItem.getIndex() - 1) * (360.0 / radialItem.getMenuSize());
                        double angleSize = 360.0 / radialItem.getMenuSize();
                        double radius = radialItem.getRadius();
                        Arc wedge = new Arc(0, 0, radius, radius, startAngle, angleSize);
                        wedge.setType(ArcType.ROUND);
                        if (hovering == radialItem) wedge.setFill(Color.WHITE);
                        else wedge.setFill(new Color(0.925, 0.925, 0.925, 1));
                        wedge.setStroke(Color.BLACK);
                        wedge.setStrokeWidth(2);
                        Text wedgeText = new Text(radialItem.getText());

                        double textX = (radius * 2.0 / 3.0) * Math.sin(Math.toRadians(startAngle + angleSize / 2 + 90))
                                - wedgeText.getLayoutBounds().getWidth() / 2;
                        double textY = (radius * 2.0 / 3.0) * Math.cos(Math.toRadians(startAngle + angleSize / 2 + 90));

                        Pane temp = new Pane();
                        temp.getChildren().addAll(wedge, wedgeText);
                        temp.setTranslateX(radialItem.getOriginX());
                        temp.setTranslateY(radialItem.getOriginY());
                        wedgeText.setTranslateX(textX);
                        wedgeText.setTranslateY(textY);

                        masterPane.getChildren().add(temp);
                    }
                }

                masterPane.getChildren().add(baseItemPane);
                this.getChildren().add(masterPane);
                break;

            case GRID:
                menuModeLabel.setText("Grid menu selected");
                this.getChildren().add(menuModeLabel);
                itemWidth = ((GridMenuItem) menuItems.get(0)).getItemWidth();
                itemHeight = ((GridMenuItem) menuItems.get(0)).getItemHeight();

//                menuBox = new Pane();
//                setAlignment(menuBox, Pos.TOP_LEFT);

                // numCols and numRows *should* be the same and create a square grid
                int numCols = (int)Math.ceil(sqrt(menuItems.size()));
                int numRows = (int)Math.ceil(menuItems.size() / numCols);

                double gridWidth = numCols * itemWidth;
                double gridHeight = numRows * itemHeight;

                double gridX = (800 - gridWidth) / 2;
                double gridY = (800 - gridHeight) / 2;

                menuBox = new Pane();
                for (int i=0; i<numRows; i++) {
                    HBox row = new HBox();
                    for (int j=0; j<numCols; j++){
                        Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
                        if (hovering == menuItems.get(i*numCols + j)) menuItem.setFill(Color.WHITE);
                        else menuItem.setFill(new Color(0.95, 0.95, 0.95, 1));
                        menuItem.setStroke(Color.BLACK);
                        Label itemLabel = new Label(menuItems.get(i*numCols + j).getText());
                        itemLabel.setAlignment(Pos.CENTER);
                        itemLabel.setPrefWidth(itemWidth);
                        itemLabel.setPrefHeight(itemHeight);

                        row.getChildren().add(new StackPane(menuItem, itemLabel));
                    }
                    row.setTranslateY(gridY + i*itemHeight);
                    menuBox.getChildren().add(row);
                }


//                for (MenuItem item : menuItems) {
//                    Pane tempPane = new Pane();
//                    Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
//                    if (hovering == item) menuItem.setFill(Color.WHITE);
//                    else menuItem.setFill(new Color(0.95, 0.95, 0.95, 1));
//                    menuItem.setStroke(Color.BLACK);
//                    Label itemLabel = new Label(item.getText());
//                    itemLabel.setAlignment(Pos.CENTER);
//                    itemLabel.setPrefWidth(itemWidth);
//                    itemLabel.setPrefHeight(itemHeight); // Set explicit preferred size for the item label
//
//                    tempPane.getChildren().addAll(menuItem, itemLabel); // Add the item container to the VBox
//                    tempPane.setTranslateX(((LinearMenuItem) item).getX());
//                    tempPane.setTranslateY(((LinearMenuItem) item).getY());
//                    menuBox.getChildren().add(tempPane);
//                }

                this.getChildren().add(menuBox); // Add the VBox to the layout
                break;
            case SCROLL:
                menuModeLabel.setText("Scroll menu selected");
                this.getChildren().add(menuModeLabel);
                break;
            default:
                menuModeLabel.setText("No menu mode selected");
                this.getChildren().add(menuModeLabel);
        }
    }

    public void receiveNotification(String channel, Object message){
        switch (channel) {
            case "menuMode" -> {
                // when menu mode changes
                this.getChildren().clear();
                this.menuMode = (Controller.MenuMode) message;
                menuModeLabel.updateMenuModeLabel(this.menuMode);
            }
            case "menuItems" -> {
                this.getChildren().clear();
                // when menuItem list changes
                Menu menu = (Menu) message;
                this.menuItems = menu.getMenuItems();
                draw();
            }
            case "hovering" -> {
                this.getChildren().clear();
                this.hovering = (MenuItem) message;
                draw();
            }
        }
    }
}
