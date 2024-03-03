package com.example.project481;

import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class MainUI extends BorderPane {
    public MainUI() {
        PublishSubscribe pubsub = new PublishSubscribe();
        Model model = new Model();
        InteractionModel iModel = new InteractionModel();
        View view = new View();
        Controller controller = new Controller();

        view.setUpEvents(controller);
//        setOnMousePressed(controller::handleMousePressed);
//        setOnMouseMoved(controller::handleMouseMoved);
        controller.setModel(model);
        controller.setInteractionModel(iModel);

        // add subscribers

        setTop(view);
        setAlignment(view, Pos.TOP_LEFT);
    }
}
