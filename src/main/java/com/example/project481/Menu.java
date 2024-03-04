package com.example.project481;

import java.util.*;

public class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        menuItems.add(new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, 4));
        for (int i = 1;  i < 5; i++)
            menuItems.add(new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, 4));
    }

    public List<MenuItem> getMenuItems() { return menuItems; }
}
