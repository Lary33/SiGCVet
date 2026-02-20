package org.example;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    static Connection con = null;
    record Paciente(int id, String nombre, String especie, int edad, String propietario) {}
    static ArrayList<Paciente> listaPacientes = new ArrayList<>();

    public static void main(String[] args){

        System.out.println("SiGCVet conectado");
        testConnection();
        leerPacientes();
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

    public static void leerPacientes() {
        String sql = "SELECT * FROM pacientestest";
        try{
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("\n--- LISTADO DE PACIENTES (Usando Records) ---");
                while (rs.next()) {
                    Paciente p = new Paciente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("especie"),
                            rs.getInt("edad"),
                            rs.getString("propietario")
                    );
                    listaPacientes.add(p);
                    System.out.println(p);
                }
                rs.close();
                st.close();
            }
        } catch (Exception e){
            System.err.println("ERROR - No se pudo crear el Statement para lectura: " + e.getMessage());
        }
    }
}