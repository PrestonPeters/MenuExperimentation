package com.example.project481;

public class MenuItem {
    private boolean isBaseItem;
    private String text;
    private Menu subMenu;

    public MenuItem(String text, boolean isBaseItem) {
        this.isBaseItem = isBaseItem;
        this.text = text;
        this.subMenu = null;
    }

    public String getText() { return text; }

    public boolean hasSubMenu() { return subMenu != null; }

    public void setSubMenu(Menu subMenu) { this.subMenu = subMenu; }

    public boolean isBaseItem() { return isBaseItem; }
    public boolean contains(double x, double y) {
        // STUB
        return false;
    }

    public void setText(String text){
        if (!this.text.equals("Item 0")) {
            this.text = text;
        }
    }
}
