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
                        Arc wedge = new Arc(radialItem.getOriginX(), radialItem.getOriginY(),
                                radialItem.getRadius(), radialItem.getRadius(),
                                (radialItem.getIndex() - 1) * (360 / (double) radialItem.getMenuSize()),
                                360 / (double) radialItem.getMenuSize());
                        wedge.setType(ArcType.ROUND);
                        if (hovering == radialItem) wedge.setFill(Color.WHITE);
                        else wedge.setFill(new Color(0.925, 0.925, 0.925, 1));
                        wedge.setStroke(Color.BLACK);
                        wedge.setStrokeWidth(2);

                        // Menu Size = 4
                        // 1 = 400, 250
                        // 2 = 250, 250
                        // 3 = 250, 400
                        // 4 = 400, 400

                        // Menu Size = 5
                        // 1 = UNFINISHED
                        // 2 = UNFINISHED
                        // 3 = UNFINISHED
                        // 4 = UNFINISHED
                        // 5 = UNFINISHED

                        // Menu Size = 6
                        // 1 = UNFINISHED
                        // 2 = UNFINISHED
                        // 3 = UNFINISHED
                        // 4 = UNFINISHED
                        // 5 = UNFINISHED
                        // 6 = UNFINISHED

                        // Menu Size = 7
                        // 1 = UNFINISHED
                        // 2 = UNFINISHED
                        // 3 = UNFINISHED
                        // 4 = UNFINISHED
                        // 5 = UNFINISHED
                        // 6 = UNFINISHED
                        // 7 = UNFINISHED

                        // Menu Size = 8
                        // 1 = UNFINISHED
                        // 2 = UNFINISHED
                        // 3 = UNFINISHED
                        // 4 = UNFINISHED
                        // 5 = UNFINISHED
                        // 6 = UNFINISHED
                        // 7 = UNFINISHED
                        // 8 = UNFINISHED

                        setTranslation(wedge, radialItem.getIndex(), radialItem.getMenuSize());

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

    public void setTranslation(Arc wedge, int index, int menuSize) {
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
                        break;
                }
                break;
            default:
                System.out.println("Not yet complete.");
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
