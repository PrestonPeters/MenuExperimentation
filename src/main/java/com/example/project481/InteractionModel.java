package com.example.project481;

public class InteractionModel {
    PublishSubscribe pubsub;
    Controller.MenuMode menuMode;
    MenuItem hovering;
    public InteractionModel(PublishSubscribe pubsub){
        this.pubsub = pubsub;
        pubsub.createChannel("menuMode");
        pubsub.createChannel("hovering");
        hovering = null;
    }

    public void setMenuMode(Controller.MenuMode mode){
        this.menuMode = mode;
        pubsub.publish("menuMode", mode);
        hovering = null;
    }

    public void setHovering(MenuItem hovering) {
        this.hovering = hovering;
        pubsub.publish("hovering", hovering);
    }

    public MenuItem getHovering() { return this.hovering; }
}
