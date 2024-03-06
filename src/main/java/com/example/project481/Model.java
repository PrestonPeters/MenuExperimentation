package com.example.project481;

// import java.util.ArrayList;

public class Model {
    private Menu menu;
    // ArrayList<MenuItem> menuItems;
    private final PublishSubscribe pubsub;
    public Model(PublishSubscribe pubsub){
        menu = new Menu();
        this.pubsub = pubsub;
        pubsub.createChannel("menuItems");

        // menuItems = new ArrayList<>();
    }

//    public void createMenuItem(String text){
//        menuItems.add(new MenuItem(text));
//        pubsub.publish("menuItems", menuItems);
//    }

    public Menu getMenu() { return menu; }

    public MenuItem checkForHit(double x, double y) {
        for (MenuItem item : menu.getMenuItems())
            if (item.contains(x, y)) return item;

        return null;
    }

}
