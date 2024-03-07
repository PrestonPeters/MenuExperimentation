package com.example.project481;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> menuItems;

    public Menu() { menuItems = new ArrayList<>(); }

    public void makeRadialMenu() {
        menuItems.clear();

        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 4));
        for (int i = 1;  i < 5; i++)
            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));

//        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 5));
//        for (int i = 1;  i < 6; i++)
//            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 5));

//        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 6));
//        for (int i = 1;  i < 7; i++)
//            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 6));

//        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 7));
//        for (int i = 1;  i < 8; i++)
//            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 7));

//        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 8));
//        for (int i = 1;  i < 9; i++)
//            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 8));
    }

    public ArrayList<MenuItem> getMenuItems() { return menuItems; }

    public void addMenuItem(String text) {
        menuItems.add(new MenuItem(text));
    }
}
