package ma.dentalSoft.model;

public class Utilisateur {
    private int id;
    private String name;
    private String role;
    private String email;
    private String telephone;
    private String password;

    // Constructeur avec 6 arguments
    public Utilisateur(int id, String name, String role, String email, String telephone, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }

    // Getter et Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int i) {
    }

    public void setName(String text) {
    }

    public void setRole(String text) {
    }

    public void setEmail(String text) {
    }

    public void setTelephone(String text) {
    }

    public void setMotDePasse(String text) {
    }
}
