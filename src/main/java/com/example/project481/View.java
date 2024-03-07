package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Label;

public class View extends StackPane implements Subscriber {
    Canvas canvas;
    GraphicsContext gc;

    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    private MenuModeLabel menuModeLabel;
    public View() {
        setFocusTraversable(true);
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.getChildren().add(menuModeLabel); // Add the label to the view
        this.menuMode = Controller.MenuMode.NONE;
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        draw();

        StackPane.setAlignment(menuModeLabel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(menuModeLabel, new Insets(10));
    }

    public void setUpEvents(Controller controller) {
        this.setOnKeyPressed(controller::handleKeyPressed);
        this.setOnKeyReleased(controller::handleKeyReleased);
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseMoved(controller::handleMouseMoved);
    }

    public void draw(){
        switch (menuMode){
            case LINEAR -> {}
            case RADIAL -> {}
            case GRID -> {}
            case SCROLL -> {}
            case NONE -> {}
        }
    }

    // Method to update the displayed menu mode in the label
    public void updateMenuMode(Controller.MenuMode menuMode) {
        this.getChildren().clear(); // Clear other menu items
        this.getChildren().add(menuModeLabel); // Add label back to the view


        switch (menuMode) {
            case LINEAR:
                menuModeLabel.setText("Linear menu selected");
                menuItems.clear(); // Clear the menuItems array before populating it
                menuItems.addAll(Arrays.asList(new MenuItem("Item 1"), new MenuItem("Item 2"), new MenuItem("Item 3"), new MenuItem("Item 4")));

                // Example: Displaying linear menu items as rectangles
                int itemWidth = 100;
                int itemHeight = 50;
                int spacing = 0;

                VBox menuVBox = new VBox(spacing); // Create a VBox to stack the menu items
                menuVBox.setAlignment(Pos.CENTER); // Center align the items within the VBox

                for (MenuItem item : menuItems) {
                    Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
                    menuItem.setFill(Color.WHITE);
                    menuItem.setStroke(Color.BLACK);
                    Label itemLabel = new Label(item.getText());
                    itemLabel.setAlignment(Pos.CENTER);
                    itemLabel.setPrefWidth(itemWidth);
                    itemLabel.setPrefHeight(itemHeight); // Set explicit preferred size for the item label

                    StackPane itemContainer = new StackPane(menuItem, itemLabel); // Create a StackPane to encapsulate the rectangle and label
                    itemContainer.setOnMouseClicked(event -> {
                        // Handle the click event for the respective menu item
                        System.out.println("Clicked on item: " + item.getText());
                    });

                    menuVBox.getChildren().add(itemContainer); // Add the item container to the VBox
                }

                this.getChildren().add(menuVBox); // Add the VBox to the layout
                break;
            case RADIAL:
                menuModeLabel.setText("Radial menu selected");

                // building the inside and outside circle for the menu
                // in the future we can make the outside circle disappear until click
                Circle center = new Circle(150, 150, 50, Color.WHITE);
                center.setStroke(Color.BLACK);
                center.setStrokeWidth(2);

                Circle outsideCircle = new Circle(150, 150, 150, Color.WHITE);
                outsideCircle.setStroke(Color.BLACK);
                outsideCircle.setStrokeWidth(1);

                // label for inside main circle
                Label centerLabel = new Label("Base Item");
                centerLabel.setAlignment(Pos.CENTER);

                Line line1 = new Line(300, 0, 300, 300);
                Line line2 = new Line(0, 150, 300, 150);

                this.getChildren().addAll(outsideCircle, line1, line2, center, centerLabel);

                break;
            case GRID:
                menuModeLabel.setText("Grid menu selected");
                break;
            case SCROLL:
                menuModeLabel.setText("Scroll menu selected");
                break;
            default:
                menuModeLabel.setText("No menu mode selected");
                break;
        }
    }
    public void receiveNotification(String channel, Object message){
        switch (channel){
            case "menuMode" -> {
                // when menu mode changes
                this.menuMode = (Controller.MenuMode) message;
                menuModeLabel.updateMenuModeLabel(this.menuMode);
                draw();
            }
            case "menuItems" -> {
                // when menuItem list changes
                Menu menu = (Menu) message;
                this.menuItems = menu.getMenuItems();
                draw();
            }
        }
    }
}
