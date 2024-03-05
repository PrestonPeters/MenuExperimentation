package com.example.project481;

public class InteractionModel {
    PublishSubscribe pubsub;
    Controller.MenuMode menuMode;
    public InteractionModel(PublishSubscribe pubsub){
        this.pubsub = pubsub;
        pubsub.createChannel("menuMode");
    }

    public void setMenuMode(Controller.MenuMode mode){
        this.menuMode = mode;
        pubsub.publish("menuMode", mode);
    }
}
