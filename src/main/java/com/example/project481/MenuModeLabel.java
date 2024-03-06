package com.example.project481;

import javafx.scene.control.Label;

public class MenuModeLabel extends Label {
    public MenuModeLabel() {
        super("No menu mode selected"); // Initial message
    }

    public void updateMenuModeLabel(Controller.MenuMode menuMode) {
        switch (menuMode) {
            case LINEAR -> setText("Linear menu selected");
            case RADIAL -> setText("Radial menu selected");
            case GRID -> setText("Grid menu selected");
            case SCROLL -> setText("Scroll menu selected");
            case NONE -> setText("No menu mode selected");
        }
    }
}
