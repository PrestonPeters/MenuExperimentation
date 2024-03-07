package com.example.project481;

public class LinearMenuItem extends MenuItem {
    private double x;
    private double y;

    public LinearMenuItem(String text, double x, double y){
        super(text);
        this.x = x;
        this.y = y;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + 100 && y >= this.y && y <= this.y + 50;
    }
}
