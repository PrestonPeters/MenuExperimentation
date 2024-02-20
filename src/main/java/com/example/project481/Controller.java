package com.example.project481;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    InteractionModel iModel;
    Model model;

    public Controller(){}

    public void setModel(Model model){
        this.model = model;
    }

    public void setInteractionModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void handleButtonClicked(){}

    public void handleMouseMoved(){}

    public void handleMousePressed(){}

}