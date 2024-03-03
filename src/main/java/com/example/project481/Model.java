package com.example.project481;

public class Model {
    private Menu menu;

    public Model() {
        menu = new Menu();
    }

    public Menu getMenu() { return menu; }

    public MenuItem checkForHit(double x, double y) {
        for (MenuItem item : menu.getMenuItems())
            if (item.contains(x, y)) return item;

        return null;
    }
}
