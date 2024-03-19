package com.example.project481;

import java.util.ArrayList;
import java.util.Collections;

public class Menu {
    private ArrayList<MenuItem> menuItems;
    private boolean isOpen;

    public Menu(int numMenuItems) {
        menuItems = new ArrayList<>();
        for (int i = 0;  i <= numMenuItems; i++)
            menuItems.add(new MenuItem("Item " + i, (i == 0)));
        isOpen = false;
    }

    public ArrayList<MenuItem> getMenuItems() {
        if (!isOpen) return new ArrayList<>(Collections.singletonList(menuItems.get(0)));
        return menuItems;
    }

    public void addMenuItem(String text, Controller.MenuMode menuMode) {
        switch (menuMode) {
            case LINEAR:
            case SCROLL:
                for (MenuItem item : menuItems) ((LinearMenuItem) item).moveUp();
                double lastY = ((LinearMenuItem) menuItems.get(menuItems.size() - 1)).getY();
                menuItems.add(new LinearMenuItem(text, (menuItems.isEmpty()), 350, lastY + 50, 100, 50));
                break;
            case RADIAL:
                for (MenuItem item : menuItems) ((RadialMenuItem) item).incrementMenuSize();
                menuItems.add(new RadialMenuItem(false, text, menuItems.size(),
                        150, 50, 400, 400, menuItems.size()));
                break;
            case GRID:
                break;
        }
    }

    public void changeMenuMode(Controller.MenuMode mode){
        switch (mode) {
            case LINEAR:
            case SCROLL:
                int minVBoxHeight = (400 - menuItems.size()/2 * 50)-25; // where the top of the menu will be *change if size is not 800x800
                int y = minVBoxHeight; // where each item will be placed within the menu
                for (int i = 0;  i < menuItems.size(); i++) {
                    menuItems.set(i, new LinearMenuItem("Item " + i, (i == 0), 350, y, 100, 50));
                    y += 50;
                }
                break;

            case RADIAL:
                menuItems.set(0, new RadialMenuItem(true, "Item 0", 0, 150, 50, 400, 400, menuItems.size() - 1));
                for (int i = 1;  i < menuItems.size(); i++)
                    menuItems.set(i, new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, menuItems.size() - 1));
                break;
        }
    }

    public void moveRadialMenuItems(double originX, double originY) {
        for (MenuItem item : menuItems)
            ((RadialMenuItem) item).setOriginXAndY(originX, originY);
    }

    public boolean isOpen() { return isOpen; }

    public void open() { isOpen = true; }

    public void close() { isOpen = false; }

    public void toggleOpen() { isOpen = !isOpen; }
}
