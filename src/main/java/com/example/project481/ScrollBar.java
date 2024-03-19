package com.example.project481;

import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class ScrollBar extends Pane {
    public ScrollBar(double x, double y, double width, double height) {
        double radius = width / 2;
        Rectangle rectangle = new Rectangle(x, y + radius, width, height - radius * 2);
        Circle topEnd = new Circle(x + radius, y + radius, radius);
        Circle bottomEnd = new Circle(x + radius, y - radius + height, radius);

        getChildren().addAll(rectangle, topEnd, bottomEnd);
    }
}