package com.example.project481;

import javafx.scene.layout.StackPane;

public class View extends StackPane implements Subscriber {
    public View() {
        setFocusTraversable(true);
    }

    public void setUpEvents(Controller controller) {
        this.setOnKeyPressed(controller::handleKeyPressed);
        this.setOnKeyReleased(controller::handleKeyReleased);
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseMoved(controller::handleMouseMoved);
    }

    public void draw(){}

    public void receiveNotification(String channel, Object message){}
}
