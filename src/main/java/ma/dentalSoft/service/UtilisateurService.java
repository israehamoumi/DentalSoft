package ma.dentalSoft.service;

import ma.dentalSoft.model.Utilisateur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UtilisateurService {

    public Utilisateur validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/utilisateur.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String fileUsername = parts[1];
                    String filePassword = parts[5];
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return new Utilisateur(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4],
                                parts[5]
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no match is found
    }
}
