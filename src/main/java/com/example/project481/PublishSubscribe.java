package com.example.project481;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublishSubscribe {

    Map<String, List<Subscriber>> channelSubscribers = new HashMap<>();
    public PublishSubscribe() {
    }

    public void createChannel(String channel) {
        if (!channelSubscribers.containsKey(channel)) {
            channelSubscribers.put(channel, new ArrayList<>());
        }
    }

    public void addSubscriber(String channel, Subscriber s) {
        if (channelSubscribers.containsKey(channel)) {
            channelSubscribers.get(channel).add(s);
        }
    }

    public void publish(String channel, Object message) {
        if (channelSubscribers.containsKey(channel)) {
            for (Subscriber s : channelSubscribers.get(channel)) {
                s.receiveNotification(channel, message);
            }
        }
    }
}
