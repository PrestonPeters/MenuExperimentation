package com.example.project481;

import javafx.scene.layout.BorderPane;

public class MainUI extends BorderPane {
    public MainUI() {
        PublishSubscribe pubsub = new PublishSubscribe();
        Model model = new Model();
        InteractionModel iModel = new InteractionModel();
        View view = new View();
        Controller controller = new Controller(view);

        view.setUpEvents(controller);
        controller.setModel(model);
        controller.setInteractionModel(iModel);

        // add subscribers

        this.setCenter(view);
    }
}
