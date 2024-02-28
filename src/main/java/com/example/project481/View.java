package com.example.project481;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class View extends StackPane implements Subscriber {
    private Label menuModeLabel;

    public View() {
        setFocusTraversable(true);
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

    public void draw(){}

    public void receiveNotification(String channel, Object message){}

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
}
