package sk.server;

import sk.server.functionality.Banner;
import sk.server.functionality.FTPService;
import sk.server.functionality.SaveConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Banner banner = new Banner();
        SaveConfig config = new SaveConfig();
        File configFile = new File("config.properties");

        banner.printBanner();

        if (!configFile.exists()) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Bienvenido a DS3-Server. Primero debes realizar la configuración.");
            System.out.println("-------------------------------------------------------------------");
            config.saveConfiguration();
        }

        Properties prop = config.loadConfiguration();

        String server = prop.getProperty("server");
        int port = 21;

        String user = prop.getProperty("user");
        String pass = prop.getProperty("pass");

        String localFilePath = prop.getProperty("localFilePath");
        String serverFolder = prop.getProperty("server_folder");
        String remoteFilePath = "ftp://" + user + "@" + server + "/" + serverFolder + "/DS30000.sl2";
        String remoteUploadPath = serverFolder + "/DS30000.sl2";

        FTPService ftpService = new FTPService(server, port, user, pass);

        Scanner scanner = new Scanner(System.in);
        System.out.println("___________________________________");
        System.out.println("     __  __ _____ _   _ _   _ ");
        System.out.println("    |  \\/  | ____| \\ | | | | |");
        System.out.println("    | |\\/| |  _| |  \\| | | | |");
        System.out.println("    | |  | | |___| |\\  | |_| |");
        System.out.println("    |_|  |_|_____|_| \\_|\\___/ ");
        System.out.println("-----------------------------------");
        System.out.println("1. Subir archivo al servidor");
        System.out.println("2. Descargar archivo del servidor");
        System.out.println("3. Eliminar configuración");
        System.out.println("-----------------------------------");
        System.out.print("Elige una opción: ");
        String option = scanner.next();

        switch (option) {
            case "1", "Subir", "Upload":
                ftpService.uploadFile(localFilePath, remoteUploadPath);
                System.out.println("Archivo subido correctamente!");
                break;

            case "2", "Descargar", "Bajar", "Download":
                ftpService.downloadFile(remoteFilePath, localFilePath);
                System.out.println("Archivo descargado correctamente!");
                break;

            case "3", "Eliminar", "Restore":
                if (configFile.exists()) {
                    try {
                        Files.delete(configFile.toPath());
                        System.out.println("La configuración ha sido eliminada. Reinicia el programa para ingresar una nueva configuración.");
                    } catch (IOException e) {
                        System.out.println("Se ha producido un error al intentar eliminar la configuración. Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("El archivo de configuración no existe.");
                }
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }

        ftpService.disconnect();
    }
}