package com.mycompany.foursquare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

import static com.mycompany.foursquare.ConexionMySQL.getConector;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static Connection conexion = null;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("resena"));
        stage.setScene(scene);
        stage.setTitle("Foursquare");
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void init() throws Exception {
        conexion = getConector("localhost", "3306", "foursquare", "root", "root");
    }

    @Override
    public void stop() throws Exception {
        conexion.close();
    }

    public static void main(String[] args) {
        launch();
    }

}