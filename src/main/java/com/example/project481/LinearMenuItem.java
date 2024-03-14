package com.example.project481;

public class LinearMenuItem extends MenuItem {
    private double x;
    private double y;

    public LinearMenuItem(String text, boolean isBaseItem, double x, double y){
        super(text, isBaseItem);
        this.x = x;
        this.y = y;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + 100 && y >= this.y && y <= this.y + 50;
    }

    // Increments the y value up by 25 to accommodate for the adding of new items
    public void moveUp() { y -= 25; }

    public double getX() { return x; }

    public double getY() { return y; }
}
