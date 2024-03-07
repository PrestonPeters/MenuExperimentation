package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.util.ArrayList;
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
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.getChildren().add(menuModeLabel); // Add the label to the view
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
        switch (menuMode){
            case LINEAR -> {}
            case RADIAL -> {
                // building the inside and outside circle for the menu
                // in the future we can make the outside circle disappear until click
                Circle baseItem = null;

                // Now each wedge is built individually so they can properly respond to hit detection
                for (MenuItem item : menuItems) {
                    RadialMenuItem radialItem = (RadialMenuItem) item;
                    if (radialItem.isBaseItem()) {
                        baseItem = new Circle(radialItem.getOriginX(), radialItem.getOriginY(),
                                radialItem.getBaseItemRadius(),
                                (hovering == radialItem) ? Color.WHITE :
                                        new Color(0.95, 0.95, 0.95, 1));
                        baseItem.setStroke(Color.BLACK);
                        baseItem.setStrokeWidth(3);
                    }

                    else {
                        Arc wedge = new Arc(0, 0, radialItem.getRadius(), radialItem.getRadius(),
                                (radialItem.getIndex() - 1) * (360.0 / radialItem.getMenuSize()),
                                360.0 / radialItem.getMenuSize());
                        wedge.setType(ArcType.ROUND);
                        if (hovering == radialItem) wedge.setFill(Color.WHITE);
                        else wedge.setFill(new Color(0.925, 0.925, 0.925, 1));
                        wedge.setStroke(Color.BLACK);
                        wedge.setStrokeWidth(2);

                        setTranslation(wedge, radialItem.getIndex(), radialItem.getMenuSize(), radialItem.getRadius());

                        this.getChildren().add(wedge);
                        setAlignment(wedge, Pos.TOP_LEFT);
                    }
                }

                this.getChildren().add(baseItem);
            }
            case GRID -> {}
            case SCROLL -> {}
            case NONE -> {}
        }
    }

    // This formula was derived by a lot of pen and paper calculations so I don't have
    // a strong algorithm for it. I will try to clean this up into a neat formula that
    // can use just a single for loop hopefully, but this will work until then.
    public void setTranslation(Arc wedge, int index, int menuSize, double radius) {
        switch (menuSize) {
            case 4:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(250);
                        break;
                    case 2:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(250);
                        break;
                    case 3:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400);
                        break;
                    case 4:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                }
                break;

            case 5:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(72)));
                        break;
                    case 2:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(54)));
                        wedge.setTranslateY(250);
                        break;
                    case 3:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(36)));
                        break;
                    case 4:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(54)));
                        wedge.setTranslateY(400);
                        break;
                    case 5:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                }
                break;

            case 6:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(60)));
                        break;
                    case 2:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(30)));
                        wedge.setTranslateY(250);
                        break;
                    case 3:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(60)));
                        break;
                    case 4:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400);
                        break;
                    case 5:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(30)));
                        wedge.setTranslateY(400);
                        break;
                    case 6:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                }
                break;

            case 7:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(360.0 / 7)));
                        break;
                    case 2:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians((360.0 / 7) * 2 - 90)));
                        wedge.setTranslateY(250);
                        break;
                    case 3:
                        wedge.setTranslateX(400 - radius * Math.cos(Math.toRadians(180 - (360.0 / 7) * 3)));
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians((360.0 / 7) * (3.0 / 2))));
                        break;
                    case 4:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians((360.0 / 7) / 2)));
                        break;
                    case 5:
                        wedge.setTranslateX(400 - radius * Math.cos(Math.toRadians((90 - ((360.0 / 7) * 3 - 90)))));
                        wedge.setTranslateY(400);
                        break;
                    case 6:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(270 - (360.0 / 7) * 5)));
                        wedge.setTranslateY(400);
                        break;
                    case 7:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                }
                break;

            case 8:
                switch (index) {
                    case 1:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(45)));
                        break;
                    case 2:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(250);
                        break;
                    case 3:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(45)));
                        wedge.setTranslateY(250);
                        break;
                    case 4:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400 - radius * Math.sin(Math.toRadians(45)));
                        break;
                    case 5:
                        wedge.setTranslateX(250);
                        wedge.setTranslateY(400);
                        break;
                    case 6:
                        wedge.setTranslateX(400 - radius * Math.sin(Math.toRadians(45)));
                        wedge.setTranslateY(400);
                        break;
                    case 7:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                        break;
                    case 8:
                        wedge.setTranslateX(400);
                        wedge.setTranslateY(400);
                }
        }
    }

    // Method to update the displayed menu mode in the label
    public void updateMenuMode(Controller.MenuMode menuMode) {
        this.getChildren().clear(); // Clear other menu items
        this.getChildren().add(menuModeLabel); // Add label back to the view

        switch (menuMode) {
            case LINEAR:
                menuModeLabel.setText("Linear menu selected");
                break;
            case RADIAL:
                menuModeLabel.setText("Radial menu selected");
                break;
            case GRID:
                menuModeLabel.setText("Grid menu selected");
                break;
            case SCROLL:
                menuModeLabel.setText("Scroll menu selected");
                break;
            default:
                menuModeLabel.setText("No menu mode selected");
                break;
        }
    }
    public void receiveNotification(String channel, Object message){
        switch (channel) {
            case "menuMode" -> {
                // when menu mode changes
                this.menuMode = (Controller.MenuMode) message;
                menuModeLabel.updateMenuModeLabel(this.menuMode);
                draw();
            }
            case "menuItems" -> {
                // when menuItem list changes
                Menu menu = (Menu) message;
                this.menuItems = menu.getMenuItems();
                draw();
            }
            case "hovering" -> {
                this.hovering = (MenuItem) message;
                draw();
            }
        }
    }
}
