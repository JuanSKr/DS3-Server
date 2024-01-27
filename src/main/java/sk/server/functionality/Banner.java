package sk.server.functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Banner {

    public void printBanner() {
        try (InputStream in = getClass().getResourceAsStream("/banner.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("An error occurred while trying to print the banner: " + e.getMessage());
        }
    }
}
