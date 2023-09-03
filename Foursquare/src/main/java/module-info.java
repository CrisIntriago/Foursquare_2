module com.mycompany.foursquare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    exports com.mycompany.foursquare.controladores to javafx.fxml;

    opens com.mycompany.foursquare to javafx.fxml;
    opens com.mycompany.foursquare.controladores to javafx.fxml;

    exports com.mycompany.foursquare;
}
