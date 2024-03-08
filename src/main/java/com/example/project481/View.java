package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.util.ArrayList;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Label;

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
        this.setOnMouseMoved(controller::handleMouseMoved);
    }

    public void draw(){
        switch (menuMode) {
            case LINEAR:
                menuModeLabel.setText("Linear menu selected");
                this.getChildren().add(menuModeLabel);

                // Example: Displaying linear menu items as rectangles
                int itemWidth = 100;
                int itemHeight = 50;
                int spacing = 0;

                VBox menuVBox = new VBox(spacing); // Create a VBox to stack the menu items
                menuVBox.setAlignment(Pos.CENTER); // Center align the items within the VBox

                for (MenuItem item : menuItems) {
                    Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
                    menuItem.setFill(Color.WHITE);
                    menuItem.setStroke(Color.BLACK);
                    Label itemLabel = new Label(item.getText());
                    itemLabel.setAlignment(Pos.CENTER);
                    itemLabel.setPrefWidth(itemWidth);
                    itemLabel.setPrefHeight(itemHeight); // Set explicit preferred size for the item label

                    StackPane itemContainer = new StackPane(menuItem, itemLabel); // Create a StackPane to encapsulate the rectangle and label

                    menuVBox.getChildren().add(itemContainer); // Add the item container to the VBox
                }

                this.getChildren().add(menuVBox); // Add the VBox to the layout
                break;

            case RADIAL:
                menuModeLabel.setText("Radial menu selected.");
                this.getChildren().add(menuModeLabel);

                // The baseItem that is to be added in the center circle is initialized here.
                Circle baseItem = null;

                // Now each wedge is built individually so they can properly respond to hit detection
                for (MenuItem item : menuItems) {
                    RadialMenuItem radialItem = (RadialMenuItem) item;

                    // If the current item is the base item, then initializes the base item circle,
                    // but does not add it to the hierarchy. This will be done after all the other
                    // wedges are drawn so that the baseItem appears above the other items in the
                    // z ordering.
                    if (radialItem.isBaseItem()) {
                        baseItem = new Circle(radialItem.getOriginX(), radialItem.getOriginY(),
                                radialItem.getBaseItemRadius(),
                                (hovering == radialItem) ? Color.WHITE :
                                        new Color(0.95, 0.95, 0.95, 1));
                        baseItem.setStroke(Color.BLACK);
                        baseItem.setStrokeWidth(3);
                    }

                    // If the item is not a base item, draws its wedge and adds it to the hierarchy.
                    else {
                        Arc wedge = new Arc(0, 0, radialItem.getRadius(), radialItem.getRadius(),
                                (radialItem.getIndex() - 1) * (360.0 / radialItem.getMenuSize()),
                                360.0 / radialItem.getMenuSize());
                        wedge.setType(ArcType.ROUND);
                        if (hovering == radialItem) wedge.setFill(Color.WHITE);
                        else wedge.setFill(new Color(0.925, 0.925, 0.925, 1));
                        wedge.setStroke(Color.BLACK);
                        wedge.setStrokeWidth(2);

                        setTranslation(wedge, radialItem.getIndex(), radialItem.getMenuSize(),
                                radialItem.getRadius(), radialItem.getOriginX(), radialItem.getOriginY());

                        this.getChildren().add(wedge);
                        setAlignment(wedge, Pos.TOP_LEFT);
                    }
                }
                this.getChildren().add(baseItem);
                break;

            case GRID:
                menuModeLabel.setText("Grid menu selected");
                this.getChildren().add(menuModeLabel);
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

    // This formula was derived by a lot of pen and paper calculations, so I don't have
    // a strong algorithm for it. I will try to clean this up into a neat formula that
    // can use just a single for loop hopefully, but this will work until then.
    public void setTranslation(Arc wedge, int index, int menuSize,
                               double radius, double originX, double originY) {
        switch (menuSize) {
            case 3:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(30.0)));
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 2:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(60.0)));
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(30.0)));
                        wedge.setTranslateY(originY);
                }
                break;

            case 4:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 2:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY);
                        break;
                    case 4:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                }
                break;

            case 5:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(72)));
                        break;
                    case 2:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(54)));
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(36)));
                        break;
                    case 4:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(54)));
                        wedge.setTranslateY(originY);
                        break;
                    case 5:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                }
                break;

            case 6:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(60)));
                        break;
                    case 2:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(30)));
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(60)));
                        break;
                    case 4:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY);
                        break;
                    case 5:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(30)));
                        wedge.setTranslateY(originY);
                        break;
                    case 6:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                }
                break;

            case 7:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(360.0 / 7)));
                        break;
                    case 2:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians((360.0 / 7) * 2 - 90)));
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius * Math.cos(Math.toRadians(180 - (360.0 / 7) * 3)));
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians((360.0 / 7) * (3.0 / 2))));
                        break;
                    case 4:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians((360.0 / 7) / 2)));
                        break;
                    case 5:
                        wedge.setTranslateX(originX - radius * Math.cos(Math.toRadians((90 - ((360.0 / 7) * 3 - 90)))));
                        wedge.setTranslateY(originY);
                        break;
                    case 6:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(270 - (360.0 / 7) * 5)));
                        wedge.setTranslateY(originY);
                        break;
                    case 7:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                }
                break;

            case 8:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(45)));
                        break;
                    case 2:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 3:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(45)));
                        wedge.setTranslateY(originY - radius);
                        break;
                    case 4:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY - radius * Math.sin(Math.toRadians(45)));
                        break;
                    case 5:
                        wedge.setTranslateX(originX - radius);
                        wedge.setTranslateY(originY);
                        break;
                    case 6:
                        wedge.setTranslateX(originX - radius * Math.sin(Math.toRadians(45)));
                        wedge.setTranslateY(originY);
                        break;
                    case 7:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                        break;
                    case 8:
                        wedge.setTranslateX(originX);
                        wedge.setTranslateY(originY);
                }
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
