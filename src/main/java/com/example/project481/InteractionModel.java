package com.example.project481;

public class InteractionModel {
    PublishSubscribe pubsub;
    Controller.MenuMode menuMode;
    MenuItem hovering;
    ScrollBar scrollBar;

    Prompt promptHolder;

    public InteractionModel(PublishSubscribe pubsub) {
        this.pubsub = pubsub;
        pubsub.createChannel("menuMode");
        pubsub.createChannel("hovering");
        pubsub.createChannel("scrollBar");
        pubsub.createChannel("prompt");
        hovering = null;
        scrollBar = null;
    }

    public void setMenuMode(Controller.MenuMode mode) {
        this.menuMode = mode;
        pubsub.publish("menuMode", mode);
    }

    public void setHovering(MenuItem hovering) {
        this.hovering = hovering;
        pubsub.publish("hovering", hovering);
    }

    public MenuItem getHovering() { return this.hovering; }

    public Controller.MenuMode getMenuMode() { return menuMode; }

    public boolean hasScrollBar() { return scrollBar != null; }

    public void makeScrollBar(double x, double y, double width, double height) {
        scrollBar = new ScrollBar(x + width * 0.9, y + height * 0.1, width * 0.05, height * 0.8);
        pubsub.publish("scrollBar", scrollBar);
    }

    public ScrollBar getScrollBar() { return scrollBar; }

    public void moveScrollBar(double dY, double upperBound, double lowerBound, double itemHeight) {
        scrollBar.moveScrollBar(dY, upperBound, lowerBound, itemHeight);
        pubsub.publish("scrollBar", scrollBar);
    }

    public void resetScrollAndHovering() {
        scrollBar = null;
        hovering = null;
        pubsub.publish("scrollBar", null);
        pubsub.publish("hovering", null);
    }

    public void makePrompt() {
        promptHolder = new Prompt();
        pubsub.publish("prompt", promptHolder);
    }

    public Prompt getPrompt() { return promptHolder; }

    public void setNextPrompt() {
        promptHolder.setNextPrompt();
        pubsub.publish("prompt", promptHolder);
    }
}
