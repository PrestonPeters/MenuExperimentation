package com.example.project481;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> menuItems;

    public Menu(int numMenuItems) {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Item 0"));
        for (int i = 1;  i <= numMenuItems; i++)
            menuItems.add(new MenuItem("Item " + i));
//        menuItems = new ArrayList<>();
//        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 4));
//        for (int i = 1;  i <= numMenuItems; i++)
//            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));
    }

    public ArrayList<MenuItem> getMenuItems() { return menuItems; }

    public void addMenuItem(String text) {
        menuItems.add(new MenuItem(text));
    }

    public void changeMenuMode(Controller.MenuMode mode){
        switch (mode){
            case LINEAR:
                for (MenuItem item : menuItems)
                    item = new LinearMenuItem(item.getText(), 0, 0, 0, 0);
                break;
            case RADIAL:
                for (int i = 1;  i < menuItems.size(); i++)
                    menuItems.set(i, new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));
                break;
        }
    }
}
