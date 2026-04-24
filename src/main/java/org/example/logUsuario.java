package org.example;
import java.util.Scanner;

import static org.example.Main.listaUsuarios;

public class logUsuario {
    static Scanner sc = new Scanner(System.in);
    static String nombreUsuario;
    static String contrasenaDeUsuario;
    static int contadorFallos = 3;

    public static void solicitarUsuario(){
        System.out.println("Escriba el nombre del usuario");
        nombreUsuario = sc.nextLine();
    }

    public static void solicitarContrasena(){
        System.out.println("Escriba la contraseña del usuario");
        contrasenaDeUsuario = sc.nextLine();
    }

    public static void comprobarUsuario() throws InterruptedException {
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
        contadorFallos--;
        System.out.println("El usuario o la contraseña que ha escrito no existe. Tiene " + contadorFallos + " intentos para logearse");
        if (contadorFallos == 0) {
            contadorFallos = 3;
            System.out.println("Ha acumulado tantos fallos que tendrá que esperar 1 minuto para volver a introducir el usuario y su contraseña");
            Thread.sleep(60000);
        }
        solicitarUsuario();
        solicitarContrasena();
        comprobarUsuario();
    }
}
