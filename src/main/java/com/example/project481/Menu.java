package com.example.project481;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.sqrt;

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
        if (!isOpen){
            // first try to return the Base Item; use first item only as fallback
            for (MenuItem item : menuItems) {
                    if (item.isBaseItem()) {
                        item.setText("Open");
                        return new ArrayList<>(Collections.singletonList(item));
                    }
            }
            menuItems.get(0).setText("Open");
            return new ArrayList<>(Collections.singletonList(menuItems.get(0)));
        }
        return menuItems;
    }

    public void addMenuItem(String text, Controller.MenuMode menuMode) {
        switch (menuMode) {
            case LINEAR:
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
            case SCROLL:
        }
    }

    public void changeMenuMode(Controller.MenuMode mode){
        switch (mode) {
            case LINEAR:
                int minVBoxHeight = (400 - menuItems.size()/2 * 50)-25; // where the top of the menu will be *change if size is not 800x800
                int y = minVBoxHeight; // where each item will be placed within the menu
                for (int i = 0;  i < menuItems.size(); i++) {
                    menuItems.set(i, new LinearMenuItem("Item " + i, (i == 0), 350, y, 100, 50));
                    if (i==0){
                        menuItems.get(i).setText("Close");
                    }
                    y += 50;
                }
                break;

            case RADIAL:
                menuItems.set(0, new RadialMenuItem(true, "Close", 0, 150, 50, 400, 400, menuItems.size() - 1));
                for (int i = 1;  i < menuItems.size(); i++)
                    menuItems.set(i, new RadialMenuItem(false, "Item " + i, i, 150, 50, 400, 400, menuItems.size() - 1));
                break;

            case GRID:
                // numCols and numRows *should* be the same to create a square grid
                int numCols = (int)Math.ceil(sqrt(menuItems.size()));
                int numRows = (int)Math.ceil(menuItems.size() / numCols);

                int minBoxWidth = 400 - (numCols * 100) / 2; // where the left of the menu will be
                int minBoxHeight = (400 - (numRows * 50) / 2)-50; // where the top of the menu will be

                y = minBoxHeight;
                for (int i=0; i<numRows; i++) {
                    int x = minBoxWidth;
                    y += 50;
                    for (int j=0; j<numCols; j++) {
                        if (i*numCols + j < menuItems.size()) {
                            if (i*numCols + j == menuItems.size()/2) {
                                // if in the middle of the grid, set the item to be the base item
                                menuItems.set(i*numCols+j, new GridMenuItem("Close", true, x, y, 100, 50));
                            } else menuItems.set(i*numCols + j, new GridMenuItem("Item " + (i*numCols + j+1), false, x, y, 100, 50));
                            x += 100;
                        }
                    }
                }
                break;
        }
    }

    public void moveRadialMenuItems(double originX, double originY) {
        for (MenuItem item : menuItems)
            ((RadialMenuItem) item).setOriginXAndY(originX, originY);
    }

    public boolean isOpen() { return isOpen; }

    public void open() {
        isOpen = true;
        for (MenuItem item : menuItems) {
            if (item.isBaseItem()) {
                item.setText("Close");
            }
        }
    }

    public void close() {
        isOpen = false;
        for (MenuItem item : menuItems) {
            if (item.isBaseItem()) {
                item.setText("Open");
            }
        }
    }
}
