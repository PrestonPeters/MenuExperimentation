package com.example.project481;

import java.util.ArrayList;

public class Model {
    ArrayList<MenuItem> menuItems;
    private final PublishSubscribe pubsub;
    public Model(PublishSubscribe pubsub){
        this.pubsub = pubsub;
        pubsub.createChannel("menuItems");

        menuItems = new ArrayList<>();
    }

    public void createMenuItem(String text){
        menuItems.add(new MenuItem(text));
        pubsub.publish("menuItems", menuItems);
    }
}
