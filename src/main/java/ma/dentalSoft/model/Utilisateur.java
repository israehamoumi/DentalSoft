package ma.dentalSoft.model;

public class Utilisateur {
    private int id;
    private String name;
    private String role;
    private String email;
    private String telephone;
    private String motDePasse;

    // Constructor
    public Utilisateur(int id, String name, String role, String email, String telephone, String motDePasse) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
    }

    // Default Constructor
    public Utilisateur() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // toString() Method
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
