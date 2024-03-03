package com.example.project481;

import java.util.*;

public class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        for (int i = 0;  i < 8; i++)
            menuItems.add(new RadialMenuItem("Item " + i, i, 100, 400, 400));
    }

    public List<MenuItem> getMenuItems() { return menuItems; }
}
