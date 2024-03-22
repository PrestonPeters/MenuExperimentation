package com.example.project481;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Label;
import javafx.scene.text.*;

import static java.lang.Math.sqrt;

public class View extends StackPane implements Subscriber {
    Canvas canvas;
    GraphicsContext gc;

    MenuItem hovering;
    Controller.MenuMode menuMode;
    ArrayList<MenuItem> menuItems;
    ScrollBar scrollBar;
    private MenuModeLabel menuModeLabel;
    
    Prompt prompt;
    public Label promptLabel;
    
    public View() {
        setFocusTraversable(true);
        menuItems = new ArrayList<>();
        menuModeLabel = new MenuModeLabel(); // Create an instance of MenuModeLabel
        this.menuMode = Controller.MenuMode.NONE;
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        hovering = null;
        scrollBar = null;
        prompt = null;

        this.getChildren().add(canvas);
        draw();

        StackPane.setAlignment(menuModeLabel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(menuModeLabel, new Insets(10));
    }

    public void setUpEvents(Controller controller) {
        this.setOnKeyPressed(controller::handleKeyPressed);
        this.setOnKeyReleased(controller::handleKeyReleased);
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseReleased(controller::handleMouseReleased);
        this.setOnMouseMoved(controller::handleMouseMoved);
        this.setOnMouseDragged(controller::handleMouseDragged);
        this.setOnScroll(controller::handleScrollEvent);
    }

    public void draw(){
        if (prompt != null) {
    
            promptLabel = new Label("Prompt Here: " + prompt.getCurrentPrompt());
            promptLabel.setStyle("-fx-font-size: 24px;");
            this.getChildren().addAll(promptLabel);

            StackPane.setAlignment(promptLabel, Pos.TOP_CENTER);
            StackPane.setMargin(promptLabel, new Insets(50));
        }

        if (this.menuItems.isEmpty()) return;
        switch (menuMode) {
            case LINEAR:
            case SCROLL:
                menuModeLabel.setText((menuMode == Controller.MenuMode.LINEAR) ?
                        "Linear menu selected" : "Scroll menu selected");
                this.getChildren().addAll(menuModeLabel);

                double itemWidth = ((LinearMenuItem) menuItems.get(0)).getItemWidth();
                double itemHeight = ((LinearMenuItem) menuItems.get(0)).getItemHeight();

                Pane linearMenuBox = new Pane();
                setAlignment(linearMenuBox, Pos.TOP_LEFT);
                int iterator = 1;

                for (MenuItem item : menuItems) {
                    Pane tempPane = new Pane();
                    Rectangle menuItem = new Rectangle(itemWidth, itemHeight);
                    if (hovering == item) menuItem.setFill(Color.WHITE);
                    else menuItem.setFill(new Color(0.95, 0.95, 0.95, 1));
                    menuItem.setStroke(Color.BLACK);
                    Label itemLabel = new Label(item.getText());
                    itemLabel.setAlignment(Pos.CENTER);
                    itemLabel.setPrefWidth(itemWidth);
                    itemLabel.setPrefHeight(itemHeight); // Set explicit preferred size for the item label

                    tempPane.getChildren().addAll(menuItem, itemLabel); // Add the item container to the VBox

                    if (menuMode == Controller.MenuMode.SCROLL && menuItems.size() > 1) {
                        Label hotkeyLabel = new Label("#" + iterator);
                        hotkeyLabel.setFont(new Font(18));
                        hotkeyLabel.setAlignment(Pos.CENTER_LEFT);
                        hotkeyLabel.setPrefWidth(itemWidth);
                        hotkeyLabel.setPrefHeight(itemHeight);
                        hotkeyLabel.setTranslateX(5);
                        tempPane.getChildren().add(hotkeyLabel);
                    }

                    tempPane.setTranslateX(item.getX());
                    tempPane.setTranslateY(item.getY());
                    linearMenuBox.getChildren().add(tempPane);

                    iterator++;
                }

                this.getChildren().add(linearMenuBox);

                if (scrollBar != null) this.getChildren().add(scrollBar);
                break;

            case RADIAL:
                menuModeLabel.setText("Radial menu selected.");
                this.getChildren().addAll(menuModeLabel);

                // The baseItem that is to be added in the center circle is initialized here.
                Pane baseItemPane = new Pane();
                Pane masterPane = new Pane();

                // Now each wedge is built individually so they can properly respond to hit detection
                for (MenuItem item : menuItems) {
                    RadialMenuItem radialItem = (RadialMenuItem) item;

                    // If the current item is the base item, then initializes the base item circle,
                    // but does not add it to the hierarchy. This will be done after all the other
                    // wedges are drawn so that the baseItem appears above the other items in the
                    // z ordering.
                    if (radialItem.isBaseItem()) {
                        Circle baseItem = new Circle(0, 0, radialItem.getBaseItemRadius(),
                                (hovering == radialItem) ? Color.WHITE : new Color(0.95, 0.95, 0.95, 1));
                        baseItem.setStroke(Color.BLACK);
                        baseItem.setStrokeWidth(3);

                        Text baseItemLabel = new Text(radialItem.getText());
                        baseItemLabel.setTranslateX(-baseItemLabel.getLayoutBounds().getWidth() / 2);
                        baseItemLabel.setTranslateY(baseItemLabel.getLayoutBounds().getHeight() / 5);

                        baseItemPane.getChildren().addAll(baseItem, baseItemLabel);
                        baseItemPane.setTranslateX(radialItem.getX());
                        baseItemPane.setTranslateY(radialItem.getY());
                    }

                    // If the item is not a base item, draws its wedge and adds it to the hierarchy.
                    else {
                        double startAngle = (radialItem.getIndex() - 1) * (360.0 / radialItem.getMenuSize());
                        double angleSize = 360.0 / radialItem.getMenuSize();
                        double radius = radialItem.getRadius();
                        Arc wedge = new Arc(0, 0, radius, radius, startAngle, angleSize);
                        wedge.setType(ArcType.ROUND);
                        if (hovering == radialItem) wedge.setFill(Color.WHITE);
                        else wedge.setFill(new Color(0.925, 0.925, 0.925, 1));
                        wedge.setStroke(Color.BLACK);
                        wedge.setStrokeWidth(2);
                        Text wedgeText = new Text(radialItem.getText());

                        double textX = (radius * 2.0 / 3.0) * Math.sin(Math.toRadians(startAngle + angleSize / 2 + 90))
                                - wedgeText.getLayoutBounds().getWidth() / 2;
                        double textY = (radius * 2.0 / 3.0) * Math.cos(Math.toRadians(startAngle + angleSize / 2 + 90));

                        Pane temp = new Pane();
                        temp.getChildren().addAll(wedge, wedgeText);
                        temp.setTranslateX(radialItem.getX());
                        temp.setTranslateY(radialItem.getY());
                        wedgeText.setTranslateX(textX);
                        wedgeText.setTranslateY(textY);

                        masterPane.getChildren().add(temp);
                    }
                }

                masterPane.getChildren().add(baseItemPane);
                this.getChildren().add(masterPane);
                break;

            case GRID:
                menuModeLabel.setText("Grid menu selected");
                this.getChildren().addAll(menuModeLabel);
                itemWidth = ((GridMenuItem) menuItems.get(0)).getItemWidth();
                itemHeight = ((GridMenuItem) menuItems.get(0)).getItemHeight();

                int numCols = (int)Math.ceil(sqrt(menuItems.size()));
                int numRows = (int)Math.ceil((double) menuItems.size() / numCols);

                double gridWidth = numCols * itemWidth;
                double gridHeight = numRows * itemHeight;

                double gridX = (800 - gridWidth) / 2;
                double gridY = (800 - gridHeight) / 2;

                Pane gridMenuBox = new Pane();
                for (int i=0; i<numRows; i++) {
                    HBox row = new HBox();
                    for (int j=0; j<numCols; j++){
                        Rectangle rect = new Rectangle(itemWidth, itemHeight);
                        Label itemLabel = new Label("");
                        StackPane stackPane;

                        if (menuItems.size() > i*numCols+j) {
                            GridMenuItem item = (GridMenuItem) menuItems.get(i*numCols+j);
                            rect = new Rectangle(item.getX(), item.getY(), itemWidth, itemHeight);
                            rect.setFill(new Color(0.95, 0.95, 0.95, 1));
                            itemLabel = new Label(menuItems.get(i * numCols + j).getText());
                            if (hovering == menuItems.get(i*numCols + j)) {
                                rect.setFill(Color.WHITE);
                            }
                        } else {
                            // if there are not enough items to fill the grid, fill the remaining space with empty rectangles
                            rect.setFill(new Color(0.55, 0.55, 0.55, 1));
                        }
                        stackPane = new StackPane(rect, itemLabel);
                        rect.setStroke(Color.BLACK);
                        itemLabel.setAlignment(Pos.CENTER);
                        itemLabel.setPrefWidth(itemWidth);
                        itemLabel.setPrefHeight(itemHeight);
                        row.getChildren().add(stackPane);
                    }
                    if (menuItems.size() != 1) {
                        row.setTranslateY(gridY + i * itemHeight);
                        row.setTranslateX(gridX);
                    } else {
                        row.setTranslateX(menuItems.get(0).getX());
                        row.setTranslateY(menuItems.get(0).getY());
                    }
                    gridMenuBox.getChildren().add(row);
                }
                this.getChildren().add(gridMenuBox);
                break;

            default:
                menuModeLabel.setText("No menu mode selected");
                this.getChildren().addAll(menuModeLabel);
        }
    }

    public void receiveNotification(String channel, Object message) {
        switch (channel) {
            case "menuMode" -> {
                // when menu mode changes
                this.getChildren().clear();
                this.menuMode = (Controller.MenuMode) message;
                menuModeLabel.updateMenuModeLabel(this.menuMode);
            }
            case "menuItems" -> {
                this.getChildren().clear();
                // when menuItem list changes
                Menu menu = (Menu) message;
                this.menuItems = menu.getMenuItems();
                draw();
            }
            case "hovering" -> {
                this.getChildren().clear();
                this.hovering = (MenuItem) message;
                draw();
            }

            case "scrollBar" -> {
                this.getChildren().clear();
                this.scrollBar = (ScrollBar) message;
                draw();
            }

            case "prompt" -> {
                this.getChildren().clear();
                this.prompt = (Prompt) message;
                draw();
            }
        }
    }
}
