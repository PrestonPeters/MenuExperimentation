package com.example.project481;

public class GridMenuItem extends MenuItem {
    private double x;
    private double y;
    private double itemWidth;
    private double itemHeight;

    public GridMenuItem(String text, boolean isBaseItem, double x, double y, double itemWidth, double itemHeight){
        super(text, isBaseItem);
        this.x = x;
        this.y = y;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + itemWidth && y >= this.y && y <= this.y + itemHeight;
    }

    public void moveUp() { y -= 25; }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getItemWidth() { return itemWidth; }

    public double getItemHeight() { return itemHeight; }

}
