package com.example.project481;

import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class ScrollBar extends Pane {
    double x;
    double y;
    double width;
    double height;
    double radius;
    Rectangle middle;
    Circle topEnd;
    Circle bottomEnd;

    public ScrollBar(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = width / 2;

        this.middle = new Rectangle(x, y + radius, width, height - radius * 2);
        this.topEnd = new Circle(x + radius, y + radius, radius);
        this.bottomEnd = new Circle(x + radius, y - radius + height, radius);

        getChildren().addAll(middle, topEnd, bottomEnd);
    }

    public double getMiddleX() { return x + width / 2; }
    public double getMiddleY() { return y + height / 2; }

    public void moveScrollBar(double dY, double upperBound, double lowerBound) {
        if (dY > 0) setY(Math.min(lowerBound - height, y + dY));
        else setY(Math.max(upperBound, y + dY));
    }

    public void setY(double y) {
        this.y = y;

        middle.setY(y + radius);
        topEnd.setCenterY(y + radius);
        bottomEnd.setCenterY(y - radius + height);
    }
}