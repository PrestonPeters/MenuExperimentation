package com.example.project481;

public class LinearMenuItem extends MenuItem {
    private double x;
    private double y;
    private double width;
    private double height;

    public LinearMenuItem(String text, double x, double y, double width, double height) {
        super(text);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
}
