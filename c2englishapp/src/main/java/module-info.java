module com.dht.c2englishapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.dht.c2englishapp to javafx.fxml;
    exports com.dht.c2englishapp;
    exports com.dht.pojo;
}
