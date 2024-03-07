package com.example.project481;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> menuItems;

    public Menu(int numMenuItems) {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Item 0"));
        for (int i = 1;  i <= numMenuItems; i++)
            menuItems.add(new MenuItem("Item " + i));
    }

    public ArrayList<MenuItem> getMenuItems() { return menuItems; }

    public void addMenuItem(String text) {
        menuItems.add(new MenuItem(text));
    }

    public void changeMenuMode(Controller.MenuMode mode){
        switch (mode){
            case LINEAR:
                int minVBoxHeight = (400 - menuItems.size()/2 * 50)-25; // where the top of the menu will be *change if size is not 800x800
                int y = minVBoxHeight; // where each item will be placed within the menu
                for (int i = 0;  i < menuItems.size(); i++) {
                    menuItems.set(i, new LinearMenuItem("Item " + i, 350, y));
                    y+=50;
                };

                break;
            case RADIAL:
                for (int i = 1;  i < menuItems.size(); i++)
                    menuItems.set(i, new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));
                break;
        }
    }
}
