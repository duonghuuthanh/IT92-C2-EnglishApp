module com.dht.bmiapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dht.bmiapp to javafx.fxml;
    exports com.dht.bmiapp;
}
