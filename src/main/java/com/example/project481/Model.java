package com.example.project481;

public class Model {
    private Menu menu;
    private final PublishSubscribe pubsub;
    public Model(PublishSubscribe pubsub){
        menu = new Menu();
        this.pubsub = pubsub;
        pubsub.createChannel("menuItems");

    }

    public void addMenuItem(String text){
        menu.addMenuItem(text);
        pubsub.publish("menuItems", menu);
    }

    public Menu getMenu() { return menu; }

    public MenuItem checkForHit(double x, double y) {
        for (MenuItem item : menu.getMenuItems())
            if (item.contains(x, y)) return item;

        return null;
    }

}
