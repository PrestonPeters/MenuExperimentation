package com.example.project481;

public class RadialMenuItem extends MenuItem {
    private boolean isBaseItem;             // Bool indicating whether this is the central item
    private int index;
    private int menuSize;                   // How many elements in the menu not including the base item
    private double baseItemRadius;
    private double radius;
    private double originX, originY;

    public RadialMenuItem(boolean isBaseItem, String text, int index, double radius, double baseItemRadius,
                          double originX, double originY, int menuSize) {
        super(text);
        this.isBaseItem = isBaseItem;
        this.index = index;
        this.radius = radius;
        this.baseItemRadius = baseItemRadius;
        this.originX = originX;
        this.originY = originY;
        this.menuSize = menuSize;
    }

    @Override
    public boolean contains(double x, double y) {
        if (isBaseItem) return baseItemContains(x, y);

        double xDifference = x - originX;
        double yDifference = y - originY;
        double distance = Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));

        if (distance > radius || distance <= baseItemRadius) return false;

        double angle = Math.atan2(yDifference, xDifference) * (180 / Math.PI);
        if (angle < 0) angle = 180 + (180 + angle);
        angle = 360 - angle;
        int partition = (int) Math.ceil(angle / (double) (360 / menuSize));
        return partition == index;
    }

    public boolean baseItemContains(double x, double y) {
        double xDifference = x - originX;
        double yDifference = y - originY;

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) <= baseItemRadius;
    }

    public boolean isBaseItem() { return isBaseItem; }

    public double getOriginX() { return originX; }

    public double getOriginY() { return originY; }

    public int getIndex() { return index; }

    public int getMenuSize() { return menuSize; }

    public double getBaseItemRadius() { return baseItemRadius; }

    public double getRadius() { return radius; }
}