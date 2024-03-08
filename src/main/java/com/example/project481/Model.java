package com.example.project481;

public class Model {
    private Menu menu;
    private final PublishSubscribe pubsub;
    public Model(PublishSubscribe pubsub){
        menu = new Menu(7);
        this.pubsub = pubsub;
        pubsub.createChannel("menuItems");
    }

    public void addMenuItem(String text, Controller.MenuMode menuMode){
        menu.addMenuItem(text, menuMode);
        pubsub.publish("menuItems", menu);
    }

    public Menu getMenu() { return menu; }

    public MenuItem checkForHit(double x, double y) {
        for (MenuItem item : menu.getMenuItems())
            if (item.contains(x, y)) return item;

        return null;
    }

    public void setMenuItems(Controller.MenuMode mode){
        menu.changeMenuMode(mode);
        pubsub.publish("menuItems", menu);
    }

}
