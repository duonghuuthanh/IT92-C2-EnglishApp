module com.dht.c2englishapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dht.c2englishapp to javafx.fxml;
    exports com.dht.c2englishapp;
}
