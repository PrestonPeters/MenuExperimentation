package com.example.project481;

import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class ScrollBar extends Pane {
    double x;
    double y;
    double width;
    double height;

    public ScrollBar(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        double radius = width / 2;
        Rectangle rectangle = new Rectangle(x, y + radius, width, height - radius * 2);
        Circle topEnd = new Circle(x + radius, y + radius, radius);
        Circle bottomEnd = new Circle(x + radius, y - radius + height, radius);

        getChildren().addAll(rectangle, topEnd, bottomEnd);
    }

    public double getMiddleX() { return x + width / 2; }
    public double getMiddleY() { return y + height / 2; }
}