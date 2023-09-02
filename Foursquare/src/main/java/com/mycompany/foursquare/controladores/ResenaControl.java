package com.mycompany.foursquare.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ResenaControl implements Initializable {

    @FXML
    private TextField busquedaTexto;

    @FXML
    private Label calleLugar;

    @FXML
    private Label ciudadLugar;

    @FXML
    private VBox contenedorResenas;

    @FXML
    private ImageView imagenSitio;

    @FXML
    private Label nombreSitio;

    @FXML
    private Label nombreUsuario;

    @FXML
    private Label paisLugar;

    @FXML
    private Label parroquiaLugar;

    @FXML
    private ImageView perfilUsuario;

    @FXML
    private Label postalLugar;

    @FXML
    private TextArea textoResena;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void btnInicio(ActionEvent event) {

    }

    @FXML
    void btnPublicar(ActionEvent event) {

    }

    @FXML
    void hacerBusqueda(ActionEvent event) {

    }

}
