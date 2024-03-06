package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class View extends StackPane implements Subscriber {
    Canvas canvas;
    GraphicsContext gc;
    private Label menuModeLabel;

    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems;
    // private MenuModeLabel menuModeLabel;
    public View() {
        setFocusTraversable(true);
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.getChildren().add(menuModeLabel); // Add the label to the view
        StackPane.setAlignment(menuModeLabel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(menuModeLabel, new Insets(10));
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        draw();
        menuModeLabel = new Label("No menu mode selected"); // Initial message
        this.getChildren().add(menuModeLabel); // Add label to the view

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
            case RADIAL -> {}
            case GRID -> {}
            case SCROLL -> {}
            case NONE -> {}
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

                // building the inside and outside circle for the menu
                // in the future we can make the outside circle disappear until click
                Circle center = new Circle(150, 150, 50, Color.WHITE);
                center.setStroke(Color.BLACK);
                center.setStrokeWidth(2);

                Circle outsideCircle = new Circle(150, 150, 150, Color.WHITE);
                outsideCircle.setStroke(Color.BLACK);
                outsideCircle.setStrokeWidth(1);

                // label for inside main circle
                Label centerLabel = new Label("Base Item");
                centerLabel.setAlignment(Pos.CENTER);

                Line line1 = new Line(300, 0, 300, 300);
                Line line2 = new Line(0, 150, 300, 150);

                this.getChildren().addAll(outsideCircle, line1, line2, center, centerLabel);

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
        switch (channel){
            case "menuMode" -> {
                // when menu mode changes
                this.menuMode = (Controller.MenuMode) message;
                menuModeLabel.updateMenuModeLabel(this.menuMode);
                draw();
            }
            case "menuItems" -> {
                // when menuItem list changes
                this.menuItems = (ArrayList<MenuItem>) message;
                draw();
            }
        }
    }
}
