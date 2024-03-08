package com.example.project481;

import javafx.scene.layout.BorderPane;

public class MainUI extends BorderPane {
    public MainUI() {
        PublishSubscribe pubsub = new PublishSubscribe();
        Model model = new Model(pubsub);
        InteractionModel iModel = new InteractionModel(pubsub);
        View view = new View();
        Controller controller = new Controller(view);

        view.setUpEvents(controller);
        controller.setModel(model);
        controller.setInteractionModel(iModel);

        pubsub.addSubscriber("menuMode", view);
        pubsub.addSubscriber("menuItems", view);
        pubsub.addSubscriber("hovering", view);

        this.setCenter(view);
    }
}
