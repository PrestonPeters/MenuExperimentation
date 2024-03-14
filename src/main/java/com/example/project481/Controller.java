package com.example.project481;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
    InteractionModel iModel;
    Model model;
    MenuMode menuMode;
    KeyState keyState;
    public enum MenuMode { NONE, LINEAR, RADIAL, GRID, SCROLL };
    public enum KeyState { NO_CTRL, CTRL_HELD };
    private View view;

    public Controller(View view) {
        menuMode = MenuMode.NONE;
        keyState = KeyState.NO_CTRL;
        this.view = view;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void setInteractionModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void handleMouseMoved(MouseEvent e) {
        iModel.setHovering(model.checkForHit(e.getX(), e.getY()));
    }

    public void handleMousePressed(MouseEvent e) {
        MenuItem result = model.checkForHit(e.getX(), e.getY());
        if (result != null) {
            System.out.println(menuMode + " " + result.getText());
            if (result.isBaseItem() && !model.getMenu().isOpen()) model.getMenu().open();
            else if (result.isBaseItem() && model.getMenu().isOpen()) model.getMenu().close();
            model.publishMenuItems();
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        switch (keyState) {
            case NO_CTRL:
                if (event.getCode() == KeyCode.CONTROL) keyState = KeyState.CTRL_HELD;

                else if (menuMode == MenuMode.SCROLL) {
                    if (event.getCode().isDigitKey() &&
                            event.getCode() != KeyCode.DIGIT0) {
                        // STUB - IN HERE, SELECT THE MENU OPTION CORRESPONDING
                        // TO THE DIGIT THAT WAS INPUT. 1 CORRESPONDS TO INDEX 0
                        // AND SO ON ALL THE WAY THROUGH UNTIL 9
                        int keyCodeToIndex = event.getCode().getCode() - 49;
                        System.out.println(keyCodeToIndex);
                    }
                }
                break;

            case CTRL_HELD:
                if (event.getCode() == KeyCode.DIGIT1 ||
                        event.getCode() == KeyCode.DIGIT2 ||
                        event.getCode() == KeyCode.DIGIT3 ||
                        event.getCode() == KeyCode.DIGIT4) {

                    int integerPressed = event.getCode().getCode() - 48;

                    if (integerPressed == 1) {
                        menuMode = MenuMode.LINEAR;
                    } else if (integerPressed == 2) {
                        menuMode = MenuMode.RADIAL;
                    } else if (integerPressed == 3) {
                        menuMode = MenuMode.GRID;
                    } else if (integerPressed == 4) {
                        menuMode = MenuMode.SCROLL;
                    } else {
                        // If the same key is pressed and the current menu mode matches it,
                        // set menu mode to NONE
                        menuMode = MenuMode.NONE;
                    }

                    iModel.setMenuMode(menuMode);
                    model.setMenuItems(menuMode);
                }
        }
    }

    // Responsible for updating the keyState. Currently, the only keyStates are
    // NO_CTRL if the Control key is not held down and CTRL_HELD otherwise.
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) keyState = KeyState.NO_CTRL;
    }

    public void handleScrollEvent() {

    }
}