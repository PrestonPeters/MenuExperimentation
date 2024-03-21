package com.example.project481;

public class MenuItem {
    private boolean isBaseItem;
    private String text;
    private Menu previousMenu;
    private Menu subMenu;
    protected double x, y;

    public MenuItem(String text, boolean isBaseItem) {
        this.isBaseItem = isBaseItem;
        this.text = text;
        this.previousMenu = null;
        this.subMenu = null;
    }

    public String getText() { return text; }

    public double getX() { return x; }

    public double getY() { return y; }

    public boolean hasSubMenu() { return subMenu != null; }

    public void setSubMenu(Menu subMenu) { this.subMenu = subMenu; }

    public boolean hasPreviousMenu() { return previousMenu != null; }

    public void setPreviousMenu(Menu previousMenu) { this.previousMenu = previousMenu; }

    public boolean isBaseItem() { return isBaseItem; }
    public boolean contains(double x, double y) {
        // STUB TO BE OVERRIDDEN
        return false;
    }

    public void setText(String text){
        this.text = text;
    }
}
