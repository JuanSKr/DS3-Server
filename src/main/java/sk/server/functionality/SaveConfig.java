package sk.server.functionality;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class SaveConfig {
    public void saveConfiguration() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la IP del servidor: ");
        String server = scanner.nextLine();
        System.out.println("------------------------------------------");

        System.out.print("Introduce el usuario de FTP: ");
        String user = scanner.nextLine();
        System.out.println("------------------------------------------");

        System.out.print("Introduce la contraseña: ");
        String pass = scanner.nextLine();
        System.out.println("------------------------------------------");

        System.out.print("Introduce la ruta del archivo local (Cliente): ");
        String localFilePath = scanner.nextLine();
        System.out.println("------------------------------------------");

        System.out.print("Introduce el nombre del directorio del FTP (Servidor): ");
        String serverFolder = scanner.nextLine();
        System.out.println("------------------------------------------");

        Properties prop = new Properties();
        prop.setProperty("server", server);
        prop.setProperty("user", user);
        prop.setProperty("pass", pass);
        prop.setProperty("localFilePath", localFilePath);
        prop.setProperty("server_folder", serverFolder);

        try {
            prop.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            System.err.println("An error occurred while saving the configuration: " + e.getMessage());
        }
    }

    public Properties loadConfiguration() {
        Properties prop = new Properties();

        try (FileInputStream in = new FileInputStream("config.properties")) {
            prop.load(in);
        } catch (IOException e) {
            System.err.println("An error occurred while loading the configuration: " + e.getMessage());
        }

        return prop;
    }
}
