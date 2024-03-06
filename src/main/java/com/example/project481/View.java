package com.example.project481;

import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class View extends StackPane implements Subscriber {
    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems;
    private MenuModeLabel menuModeLabel;
    public View() {
        setFocusTraversable(true);
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.getChildren().add(menuModeLabel); // Add the label to the view
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
