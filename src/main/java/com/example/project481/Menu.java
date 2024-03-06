package com.example.project481;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 4));
        for (int i = 1;  i < 5; i++)
            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));
    }

    public ArrayList<MenuItem> getMenuItems() { return menuItems; }

    public void addMenuItem(String text) {
        menuItems.add(new MenuItem(text));
    }
}
