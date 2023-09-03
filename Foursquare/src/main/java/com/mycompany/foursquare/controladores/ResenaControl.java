package com.mycompany.foursquare.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ResourceBundle;
import com.mycompany.foursquare.App;
import com.mycompany.foursquare.Lugar;
import com.mycompany.foursquare.Resena;
import com.mycompany.foursquare.Usuario;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class ResenaControl implements Initializable {

    //INFORMACION
    @FXML
    private Label lciudad;
    @FXML
    private Label lpais;
    @FXML
    private Label lcalle;
    @FXML
    private Label lparroquia;
    @FXML
    private Label lcodigopostal;

    @FXML
    private TextField busquedaTexto;

    @FXML
    private VBox contenedorResenas;

    @FXML
    private Label descripcionSitio;

    @FXML
    private Label eliminarLugarLbl;

    @FXML
    private ImageView imagenSitio;

    @FXML
    private Label nombreSitio;

    @FXML
    private Label nombreUsuario;

    @FXML
    private ImageView perfilUsuario;

    @FXML
    private TextArea textoResena;

    @FXML
    private Label editarLugarLbl;

    @FXML
    private Label editarUbicacionLbl;

    @FXML
    private LinkedList<Lugar> lugares = Lugar.getLugares();

    @FXML
    private Label lblEstado;

    // REEMPLAZAR CON EL USUARIO QUE USAMOS POR DEFECTO
    private int CURRENT_USER = 1;

    // REEMPLAZAR CON EL LUGAR QUE SE ELIGE EN LA VENTANA ANTERIOR
    private String nombrePrimerLugar = "Noe Sushi Bar";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Implementar el DML para obtener la información del sitio y el lugar

        // Las imagenes no funcionan :c
        // perfilUsuario.setImage(new
        // Image(getClass().getResourceAsStream("imagenes/perfil.png")));
        // imagenSitio.setImage(new
        // Image(getClass().getResourceAsStream("imagenes/edificio.png")));
        TextFields.bindAutoCompletion(busquedaTexto, lugares);
        nombreUsuario.setText(Usuario.getUsuario(CURRENT_USER).getNombre() + " " + Usuario.getUsuario(1).getApellido());
        hacerBusqueda(nombrePrimerLugar);

    }

    public void cargarResenas(int id_Sitio) {

        Resena.getResenas(id_Sitio).forEach(resena -> {
            contenedorResenas.getChildren().clear();
            Usuario u = Usuario.getUsuario(resena.getId_Usuario());
            Label usuario = new Label(u.getNombre() + " " + u.getApellido());
            Text descripcion = new Text(resena.getDescripcion());
            descripcion.setWrappingWidth(450);
            VBox comentario = new VBox(usuario, descripcion);
            comentario.setFillWidth(true);
            if (u.getId_Usuario() == CURRENT_USER) {
                ContextMenu menu = new ContextMenu();
                comentario.setOnContextMenuRequested(event -> {
                    MenuItem editar = new MenuItem("Editar");
                    editar.setOnAction(event1 -> {
                        final String descripcionResena = showNewDialog("Editar reseña", "Nueva descripción",
                                resena.getDescripcion());
                        Resena.updateResena(resena.getId_Resena(), descripcionResena);
                        try {
                            App.setRoot("resena");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    MenuItem eliminar = new MenuItem("Eliminar");
                    eliminar.setOnAction(event1 -> {
                        Resena.deleteResena(resena.getId_Resena());
                        try {
                            App.setRoot("resena");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    menu.getItems().addAll(editar, eliminar);
                    if (menu.isShowing()) {
                        menu.hide();
                    }
                    menu.show(comentario, event.getScreenX(), event.getScreenY());
                });
                comentario.setOnMouseClicked(e -> {
                    if (menu.isShowing()) {
                        menu.getItems().clear();
                        menu.hide();
                    }
                });
            }
            contenedorResenas.getChildren().add(comentario);
        });
    }

    @FXML
    void btnInicio(ActionEvent event) throws IOException {
        // Reemplazar con nombre de la ventana principal
        App.setRoot(null);
    }

    @FXML
    void btnPublicar(ActionEvent event) throws IOException {
        if (!textoResena.getText().isEmpty()) {
            Resena.addResena(textoResena.getText(), 1, CURRENT_USER);
            App.setRoot("resena");
        }
    }

    @FXML
    void hacerBusqueda(ActionEvent event) {
        String busqueda = busquedaTexto.getText();
        hacerBusqueda(busqueda);
    }

    public void hacerBusqueda(String texto) {
        Lugar lugar = existeLugar(texto);
        if (lugar != null) {
            System.out.println("Existe el lugar");
            cargarResenas(lugar.getId_Sitio());
            nombreSitio.setText(lugar.getNombre());
            lblEstado.setText(lugar.getDisponibilidad());

            String[] informacion = lugar.obtenerDetallesLugar();
            
            //Que pereza hacer que se entienda jajaj 
            
            lpais.setText(informacion[0]);
            lciudad.setText(informacion[1]);
            lparroquia.setText(informacion[2]);
            lcodigopostal.setText(informacion[3]);
            lcalle.setText(informacion[4]);

        } else {
            System.out.println("No existe ese lugar");
        }
    }

    ;

    @FXML
    void editarLugar(MouseEvent event) {
        final String titulo = "Editar lugar (Dejan en blanco para no editar)";
        String nombre = showNewDialog(titulo, "Nuevo Nombre");
        String descripcion = showNewDialog(titulo, "Nueva Descripción");
        // Implementar la edición del sitio
    }

    @FXML
    void editarUbicacion(MouseEvent event) {
        final String titulo = "Editar ubicación (Dejan en blanco para no editar)";
        String postal = showNewDialog(titulo, "Nuevo Código Postal");
        String calle = showNewDialog(titulo, "Calle Secundaria");
        String parroquia = showNewDialog(titulo, "Parroquia");
        String ciudad = showNewDialog(titulo, "Ciudad");
        String pais = showNewDialog(titulo, "País");
        // Implementar la edición del sitio
    }

    @FXML
    void eliminarLugar(MouseEvent event) {
        // Implementar la eliminación del sitio
    }

    private static String showNewDialog(String titulo, String mensaje, String viejaResena) {
        final DialogPane dialogPane = new DialogPane();
        final VBox vbox = new VBox(new Label(mensaje));
        final TextField textField = new TextField();
        final TextArea textArea = new TextArea();
        if (viejaResena.length() == 0) {
            vbox.getChildren().add(textField);
        } else {
            textArea.setText(viejaResena);
            textArea.setPrefRowCount(5);
            textArea.setPrefColumnCount(10);
            textArea.setWrapText(true);
            vbox.getChildren().add(textArea);
        }
        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialogPane.setMinSize(500, 200);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        dialogPane.setContent(vbox);
        final Dialog<ButtonType> dialog = new Dialog<>();
        final Label label = new Label(titulo);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 0, 0));
        dialogPane.setHeader(label);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
        return viejaResena.length() == 0 ? textField.getText() : textArea.getText();
    }

    private static String showNewDialog(String titulo, String mensaje) {
        return showNewDialog(titulo, mensaje, "");
    }

    public Lugar existeLugar(String nombreLugar) {
        for (Lugar lugar : lugares) {
            if (lugar.getNombre().equals(nombreLugar)) {
                return lugar;
            }
        }
        return null;
    }

}
