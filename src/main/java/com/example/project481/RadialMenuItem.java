package com.example.project481;

public class RadialMenuItem extends MenuItem {
    private boolean isBaseItem;             // Bool indicating whether this is the central item
    private int index;
    private double baseItemRadius = 50;     // Currently hardcoded at 50
    private double radius;
    private double originX, originY;

    public RadialMenuItem(String text, int index, double radius, double originX, double originY) {
        super(text);
        this.index = index;
        this.radius = radius;
        this.originX = originX;
        this.originY = originY;
    }

    @Override
    public boolean contains(double x, double y) {
        if (isBaseItem) return baseItemContains(x, y);

        double xDifference = x - originX;
        double yDifference = y - originY;

        if (Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) > radius)
            return false;

        double angle = Math.atan2(yDifference, xDifference) * (180 / Math.PI);
        if (angle < 0) angle = 180 + (180 + angle);
        angle = 360 - angle;
        int partition = (int) Math.floor(angle / (double) (360 / 8));
        return partition == index;
    }

    public boolean baseItemContains(double x, double y) {
        double xDifference = x - originX;
        double yDifference = y - originY;

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) <= baseItemRadius;
    }
}