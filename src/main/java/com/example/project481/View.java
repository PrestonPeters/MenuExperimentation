package com.example.project481;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class View extends StackPane implements Subscriber {
    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems;
    public View() {
        setFocusTraversable(true);
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
