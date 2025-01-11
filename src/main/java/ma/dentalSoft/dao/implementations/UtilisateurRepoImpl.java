package ma.dentalSoft.dao.implementations;

import ma.dentalSoft.dao.UtilisateurRepo;
import ma.dentalSoft.model.Utilisateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepoImpl implements UtilisateurRepo {
    private static final String FILE_PATH = "src/main/resources/utilisateur.txt";

    @Override
    public void save(Utilisateur utilisateur) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(utilisateur.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur findById(int id) {
        // Read file and find by ID
        return null; // Replace with logic
    }

    @Override
    public List<Utilisateur> findAll() {
        // Read file and return all users
        return new ArrayList<>();
    }

    @Override
    public void update(Utilisateur utilisateur) {
        // Read, modify, and write file
    }

    @Override
    public void delete(int id) {
        // Remove entry from file
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue; // Skip header or empty lines
                String[] values = line.split(",");
                String name = values[1]; // Username is in the 2nd column
                String motDePasse = values[5]; // Password is in the 6th column

                if (name.equals(username) && motDePasse.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
