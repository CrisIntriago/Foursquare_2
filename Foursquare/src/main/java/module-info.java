module com.mycompany.foursquare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.foursquare to javafx.fxml;
    exports com.mycompany.foursquare;
}
