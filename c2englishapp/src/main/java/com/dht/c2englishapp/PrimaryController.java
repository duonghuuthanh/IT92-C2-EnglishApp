package com.dht.c2englishapp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    public void checkHandler(ActionEvent event) {
        int a = 10;
        int b = 20;
        int c = a + b;
    }
}
