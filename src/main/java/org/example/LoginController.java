package org.example;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static org.example.Main.listaUsuarios;
import static org.example.Main.Usuario;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Button btnAceptar;
    @FXML private Label lblMensaje;

    private String fxmlFile;
    private int contadorFallos = 3;
    private Usuario usuarioLogeado = null;

    @FXML
    public void manejarLogin(ActionEvent event) {
        String nombreUsuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            lblMensaje.setText("Error: No se ha escrito un usuario.");
            return;
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            lblMensaje.setText("Error: No se ha escrito una contraseña.");
            return;
        }

        // Comprobación de usuarios
        for (Usuario i : listaUsuarios) {
            if (i.nombre().equals(nombreUsuario) && i.contrasena().equals(contrasena)) {
                usuarioLogeado = i;
                break;
            }
        }

        // Si el usuario existe
        if (usuarioLogeado != null) {
            lblMensaje.setStyle("-fx-text-fill: #00C853;");
            lblMensaje.setText("Acceso concedido. Cargando menú...");
            cambiarEscena(event, usuarioLogeado.rol().equals("Administrador"));
        } else {
            // Si falla
            contadorFallos--;
            if (contadorFallos > 0) {
                lblMensaje.setText("Credenciales incorrectas. Te quedan " + contadorFallos + " intentos.");
                txtContrasena.clear(); // Limpiamos la contraseña
            } else {
                bloquearInterfaz();
            }
        }
    }

    private void bloquearInterfaz() {
        lblMensaje.setText("Demasiados fallos. Espere 1 minuto.");
        btnAceptar.setDisable(true);
        txtUsuario.setDisable(true);
        txtContrasena.setDisable(true);

        PauseTransition pausa = new PauseTransition(Duration.minutes(1));
        pausa.setOnFinished(e -> {
            contadorFallos = 3;
            btnAceptar.setDisable(false);
            txtUsuario.setDisable(false);
            txtContrasena.setDisable(false);
            txtContrasena.clear();
            lblMensaje.setText("Puede volver a intentarlo.");
        });
        pausa.play();
    }

    private void cambiarEscena(ActionEvent event, boolean isAdmin) {
        try {
            // Elige el archivo FXML dependiendo del rol
            if (isAdmin) {
                fxmlFile = "/org.example/Menu_admin_coloreado.fxml";
            }
            else {
                fxmlFile = "/org.example/Menu_ususario_coloreado.fxml";
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Obtenemos la ventana (Stage) actual a partir del botón pulsado
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la siguiente pantalla.");
            e.printStackTrace();
        }
    }
}