package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    static Connection con = null;
    record Paciente(int id, String nombre, String especie, int edad, String propietario) {}
    static ArrayList<Paciente> listaPacientes = new ArrayList<>();

    public static void main(String[] args){

        System.out.println("SiGCVet conectado");
        testConnection();
        cerrarConexion();
    }

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
                System.out.println("Conexión establecida con quim_test_db");
            }
        } catch (SQLException e) {
            System.err.println("No se ha cerrado correctamente la base de datos" + e.getMessage());
            e.printStackTrace();
        }
    }
}