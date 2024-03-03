package com.example.project481;

public class MenuItem {
    private String text;
    private Menu subMenu;

    public MenuItem(String text) { this.text = text; }

    public String getText() { return text; }

    public void setSubMenu(Menu subMenu) { this.subMenu = subMenu; }

    public boolean contains(double x, double y) {
        // STUB
        return true;
    }
}
