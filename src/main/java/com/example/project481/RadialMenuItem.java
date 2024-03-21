package com.example.project481;

public class RadialMenuItem extends MenuItem {
    private int index;
    private int menuSize;
    private double baseItemRadius;
    private double radius;

    public RadialMenuItem(boolean isBaseItem, String text, int index, double radius, double baseItemRadius,
                          double originX, double originY, int menuSize) {
        super(text, isBaseItem);
        this.index = index;
        this.radius = radius;
        this.baseItemRadius = baseItemRadius;
        this.menuSize = menuSize;
        this.x = originX;
        this.y = originY;
    }

    @Override
    public boolean contains(double x, double y) {
        if (isBaseItem()) return baseItemContains(x, y);

        double xDifference = x - getX();
        double yDifference = y - getY();
        double distance = Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));

        if (distance > radius || distance <= baseItemRadius) return false;

        double angle = Math.atan2(yDifference, xDifference) * (180 / Math.PI);
        if (angle < 0) angle = 180 + (180 + angle);
        angle = 360 - angle;
        int partition = (int) Math.ceil(angle / (double) (360 / menuSize));
        return partition == index;
    }

    public boolean baseItemContains(double x, double y) {
        double xDifference = x - getX();
        double yDifference = y - getY();

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) <= baseItemRadius;
    }

    public void setOriginXAndY(double originX, double originY) {
        this.x = originX;
        this.y = originY;
    }

    public int getIndex() { return index; }

    public int getMenuSize() { return menuSize; }

    public void incrementMenuSize() { menuSize++; }

    public double getBaseItemRadius() { return baseItemRadius; }

    public double getRadius() { return radius; }
}