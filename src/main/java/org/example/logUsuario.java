package org.example;
import java.util.Scanner;

import static org.example.Main.listaUsuarios;

public class logUsuario {
    static Scanner sc = new Scanner(System.in);
    static String nombreUsuario;
    static String contrasenaDeUsuario;

    public static void solicitarUsuario(){
        System.out.println("Escriba el nombre del usuario");
        nombreUsuario = sc.nextLine();
    }

    public static void solicitarContrasena(){
        System.out.println("Escriba la contraseña del usuario");
        contrasenaDeUsuario = sc.nextLine();
    }

    public static void comprobarUsuario(){
        if (nombreUsuario == null || nombreUsuario == "") {
            System.out.println("Error al logear debido a que no se ha escrito un usuario");
            return;
        }
        if (contrasenaDeUsuario == null || contrasenaDeUsuario == "") {
            System.out.println("Error al logear debido a que no se ha escrito una contraseña para el usuario");
            return;
        }

        for (int i = 0; i < listaUsuarios.size(); i++){
            if (listaUsuarios.get(i).nombre().equals(nombreUsuario)){
                if (listaUsuarios.get(i).contrasena().equals(contrasenaDeUsuario)){
                    System.out.println("Se ha accedido al usuario con normalidad");
                    return;
                }
            }
        }
        System.out.println("El usuario o la contraseña que ha escrito no existe");
    }
}
