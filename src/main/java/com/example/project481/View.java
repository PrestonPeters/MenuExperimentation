package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class View extends StackPane implements Subscriber {
    Canvas canvas;
    GraphicsContext gc;
    public View() {
        setFocusTraversable(true);
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        draw();
    }

    public void setUpEvents(Controller controller) {
        this.setOnKeyPressed(controller::handleKeyPressed);
        this.setOnKeyReleased(controller::handleKeyReleased);
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseMoved(controller::handleMouseMoved);
    }

    public void draw() {
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            gc.strokeArc(300, 300, 200, 200, i * 360, (i + 1) * 360, ArcType.ROUND);
            double angleRadians = Math.toRadians((i + 1) * (360.0 / 8.0));
            double x = 400 + 100 * Math.cos(angleRadians);
            double y = 400 + 100 * Math.sin(angleRadians);

            gc.strokeLine(400, 400, x, y);
        }
    }

    public void receiveNotification(String channel, Object message){}
}
