package com.example.project481;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

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
}
