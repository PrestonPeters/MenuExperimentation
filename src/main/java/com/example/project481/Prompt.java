package com.example.project481;

public class Prompt {
    int promptIndex = 0;
    String[] promptList = {"Apple", "Banana", "Cranberry", "Blueberry", "Pear", "Orange", "Peach", "Apricot"};

    public Prompt() {
        
    }

    public String getCurrentPrompt() {
        return promptList[promptIndex];
    }

    public void setNextPrompt() {
        promptIndex++;
    }
}
