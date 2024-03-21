package com.example.project481;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import java.util.ArrayList;

public class Controller {
    InteractionModel iModel;
    Model model;
    MenuMode menuMode;
    KeyState keyState;
    DragState dragState;
    public enum MenuMode { NONE, LINEAR, RADIAL, GRID, SCROLL };
    public enum KeyState { NO_CTRL, CTRL_HELD };
    public enum DragState { IDLE, ON_BASE_ITEM, DRAGGING };
    double xOffset, yOffset;

    public Controller() {
        menuMode = MenuMode.NONE;
        keyState = KeyState.NO_CTRL;
        dragState = DragState.IDLE;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void setInteractionModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void handleMouseMoved(MouseEvent e) {
        if (menuMode != MenuMode.SCROLL) iModel.setHovering(model.checkForHit(e.getX(), e.getY()));
    }

    public void handleMouseDragged(MouseEvent e) {
        if (dragState == DragState.ON_BASE_ITEM || dragState == DragState.DRAGGING) {
            dragState = DragState.DRAGGING;
            model.getMenu().moveRadialMenuItems(e.getX() + xOffset, e.getY() + yOffset);
            model.publishMenuItems();
        }

        else iModel.setHovering(model.checkForHit(e.getX(), e.getY()));
    }

    public void handleMousePressed(MouseEvent e) {
        MenuItem result = model.checkForHit(e.getX(), e.getY());

        if (menuMode == MenuMode.RADIAL && result != null && result.isBaseItem()) {
            dragState = DragState.ON_BASE_ITEM;
            xOffset = model.getMenu().getMenuItems().get(0).getX() - e.getX();
            yOffset = model.getMenu().getMenuItems().get(0).getY() - e.getY();
        }
    }

    public void handleMouseReleased(MouseEvent e) {
        MenuItem result;
        if (menuMode == MenuMode.SCROLL) {
            if (keyState == KeyState.CTRL_HELD) {
                if (!model.getMenu().isOpen()) {
                    model.toggleMenuOpen();
                    LinearMenuItem item = (LinearMenuItem) model.getMenu().getMenuItems().get(0);
                    iModel.makeScrollBar(item.getX(), item.getY(), item.getItemWidth(), item.getItemHeight());
                    ScrollBar scrollBar = iModel.getScrollBar();
                    iModel.setHovering(model.checkForHit(scrollBar.getMiddleX(), scrollBar.getMiddleY()));
                }

                else {
                    ScrollBar scrollBar = iModel.getScrollBar();
                    result = model.checkForHit(scrollBar.getMiddleX(), scrollBar.getMiddleY());
                    System.out.println(menuMode + " " + result.getText());
                    model.toggleMenuOpen();
                    iModel.resetScrollAndHovering();
                }
            }
            return;
        }

        result = model.checkForHit(e.getX(), e.getY());
        if (menuMode == MenuMode.RADIAL && dragState == DragState.DRAGGING)
            dragState = DragState.IDLE;

        else if (result != null) {
            dragState = DragState.IDLE;
            System.out.println(menuMode + " " + result.getText());
            if (result.isBaseItem()) {
                if (!model.getMenu().hasPreviousMenu()) model.toggleMenuOpen();
                else model.setMenu(model.getMenu().getPreviousMenu());
            }

            else if (result.hasSubMenu()) model.setMenu(result.getSubMenu());

            else {
                while (model.getMenu().hasPreviousMenu())
                    model.setMenu(model.getMenu().getPreviousMenu());
                model.closeMenu();
            }
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        switch (keyState) {
            case NO_CTRL:
                if (event.getCode() == KeyCode.CONTROL) {
                    keyState = KeyState.CTRL_HELD;
                    if (menuMode == MenuMode.SCROLL) {
                        ScrollBar scrollBar = iModel.getScrollBar();
                        if (scrollBar == null) iModel.setHovering(model.getItemAtIndex(0));
                        else iModel.setHovering(model.checkForHit(scrollBar.getMiddleX(), scrollBar.getMiddleY()));
                    }
                }

                else if (menuMode == MenuMode.SCROLL && model.getMenu().isOpen()) {
                    if (event.getCode().isDigitKey() && event.getCode() != KeyCode.DIGIT0) {
                        MenuItem result = model.getItemAtIndex(event.getCode().getCode() - 49);
                        iModel.setHovering(result);
                    }
                }
                dragState = DragState.IDLE;
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
                    model.closeMenu();
                    iModel.resetScrollAndHovering();
                    if (menuMode == MenuMode.SCROLL) iModel.setHovering(model.getItemAtIndex(0));
                }

                dragState = DragState.IDLE;
        }
    }

    // Responsible for updating the keyState. Currently, the only keyStates are
    // NO_CTRL if the Control key is not held down and CTRL_HELD otherwise.
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            keyState = KeyState.NO_CTRL;
            if (menuMode == MenuMode.SCROLL) iModel.setHovering(null);
        }

        else if (menuMode == MenuMode.SCROLL && model.getMenu().isOpen()) {
            if (event.getCode().isDigitKey() && event.getCode() != KeyCode.DIGIT0) {
                MenuItem result = model.getItemAtIndex(event.getCode().getCode() - 49);
                System.out.println(menuMode + " " + result.getText());
                model.toggleMenuOpen();
                iModel.resetScrollAndHovering();
            }

            else {
                ScrollBar scrollBar = iModel.getScrollBar();
                iModel.setHovering(model.checkForHit(scrollBar.getMiddleX(), scrollBar.getMiddleY()));
            }
        }
    }

    public void handleScrollEvent(ScrollEvent event) {
        if (menuMode == MenuMode.SCROLL && model.getMenu().isOpen()) {
            ScrollBar scrollBar = iModel.getScrollBar();
            LinearMenuItem firstItem = (LinearMenuItem) model.getMenu().getMenuItems().get(0);
            int lastIndex = model.getMenu().getMenuItems().size() - 1;
            LinearMenuItem lastItem = (LinearMenuItem) model.getMenu().getMenuItems().get(lastIndex);
            iModel.moveScrollBar(event.getDeltaY(), firstItem.getY(),
                    lastItem.getY() + lastItem.getItemHeight(), firstItem.getItemHeight());
            iModel.setHovering(model.checkForHit(scrollBar.getMiddleX(), scrollBar.getMiddleY()));
        }
    }
}