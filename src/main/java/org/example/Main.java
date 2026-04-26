package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Se trata del arranque de la aplicación de SiGCVet
 * Contiene toda la lógica del programa de SiGCVet para que se ejecute correctamente
 *
 * @author LaryD
 * @version 1.0
 */

public class Main extends Application{
    static Connection con = null;

    record Usuario(int idUsuario, String nombre, String contrasena, String rol) {}
    static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    record Cliente(String nia, String nombre, String email, String telefono, String direccion) {}
    static ArrayList<Cliente> listaClientes = new ArrayList<>();

    record Paciente(String chip, String nombre, String especie, String raza, int edad, String alergias, String observaciones, String niaCliente) {}
    static ArrayList<Paciente> listaPacientes = new ArrayList<>();

    record Cita(int idCita, String niaCliente, String chipPaciente, LocalDate fecha, LocalTime hora) {}
    static ArrayList<Cita> listaCitas = new ArrayList<>();

    record Producto(int idProducto, String nombre, String descripcion, float precio, int stock) {}
    static ArrayList<Producto> listaProductos = new ArrayList<>();

    record Consulta(int idConsulta, int idCita, String diagnostico, String tratamiento, String observaciones) {}
    static ArrayList<Consulta> listaConsultas = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("SiGCVet conectado");
        testConnection();
        leerUsuarios();
        cerrarConexion();

        launch(args);
    }

    public void start(Stage primaryStage) {
        testConnection();
        try {
            // 1. Cargamos el archivo FXML desde la carpeta de recursos
            // Nota: La ruta debe empezar por / y coincidir con la jerarquía de carpetas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example/Pantalla_loggin_coloreado.fxml"));
            Parent root = loader.load();
            // 2. Creamos la "Escena" (el contenido de la ventana)
            Scene scene = new Scene(root);
            // 3. Configuramos el "Escenario" (la ventana en sí)
            primaryStage.setTitle("SiGCVet - Loggin de usuarios");
            primaryStage.setScene(scene);
            // 4. Hacemos que la ventana sea visible
            primaryStage.show();
        } catch (Exception e) {
            // Es vital capturar errores aquí por si la ruta del FXML está mal
            e.printStackTrace();
        }
    };

    public static void testConnection() {
        String url = "jdbc:postgresql://ep-lively-sunset-aby2qoj7-pooler.eu-west-2.aws.neon.tech:5432/proyecto_alumno7?sslmode=require";
        String usuario = "neondb_owner";
        String password = "npg_3FCiZhx7VnBo";
        System.out.println("Intentando conectar a la base de datos...");
        try {
            con = DriverManager.getConnection(url, usuario, password);
            if (con != null && !con.isClosed()) {
                System.out.println("Conexión establecida con quim_test_db");
            }
        } catch (SQLException e) {
            System.err.println("ERROR de conexiÛn: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void cerrarConexion() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexión cerrada con quim_test_db");
            }
        } catch (SQLException e) {
            System.err.println("No se ha cerrado correctamente la base de datos" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void leerUsuarios() {
        listaUsuarios.clear();
        String sql = "SELECT * FROM usuario";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE USUARIOS ---");
                while (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("contrasena"),
                            rs.getString("rol")
                    );
                    listaUsuarios.add(u);
                    System.out.println(u);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura usuarios: " + e.getMessage());
        }
    }

    public static void leerClientes() {
        listaClientes.clear();
        String sql = "SELECT * FROM cliente";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE CLIENTES ---");
                while (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getString("nia"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getString("direccion")
                    );
                    listaClientes.add(c);
                    System.out.println(c);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura clientes: " + e.getMessage());
        }
    }

    public static void leerPacientes() {
        listaPacientes.clear();
        String sql = "SELECT * FROM paciente";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE PACIENTES ---");
                while (rs.next()) {
                    Paciente p = new Paciente(
                            rs.getString("chip"),
                            rs.getString("nombre"),
                            rs.getString("especie"),
                            rs.getString("raza"),
                            rs.getInt("edad"),
                            rs.getString("alergias"),
                            rs.getString("observaciones"),
                            rs.getString("nia_cliente")
                    );
                    listaPacientes.add(p);
                    System.out.println(p);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura pacientes: " + e.getMessage());
        }
    }

    public static void leerCitas() {
        listaCitas.clear();
        String sql = "SELECT * FROM cita";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE CITAS ---");
                while (rs.next()) {
                    Cita c = new Cita(
                            rs.getInt("id_cita"),
                            rs.getString("nia_cliente"),
                            rs.getString("chip_paciente"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getTime("hora").toLocalTime()
                    );
                    listaCitas.add(c);
                    System.out.println(c);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura citas: " + e.getMessage());
        }
    }

    public static void leerProductos() {
        listaProductos.clear();
        String sql = "SELECT * FROM producto";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getFloat("precio"),
                            rs.getInt("stock")
                    );
                    listaProductos.add(p);
                    System.out.println(p);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura productos: " + e.getMessage());
        }
    }

    public static void leerConsultas() {
        listaConsultas.clear();
        String sql = "SELECT * FROM consulta";

        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE CONSULTAS ---");
                while (rs.next()) {
                    Consulta c = new Consulta(
                            rs.getInt("id_consulta"),
                            rs.getInt("id_cita"),
                            rs.getString("diagnostico"),
                            rs.getString("tratamiento"),
                            rs.getString("observaciones")
                    );
                    listaConsultas.add(c);
                    System.out.println(c);
                }

                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR - Lectura consultas: " + e.getMessage());
        }
    }
}