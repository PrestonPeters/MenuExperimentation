package com.example.project481;

public class MenuItem {
    private String text;
    private Menu subMenu;

    public MenuItem(String text) {
        this.text = text;
        this.subMenu = null;
    }

    public String getText() { return text; }

    public boolean hasSubMenu() { return subMenu != null; }

    public void setSubMenu(Menu subMenu) { this.subMenu = subMenu; }

    public boolean contains(double x, double y) {
        // STUB
        return false;
    }
}
