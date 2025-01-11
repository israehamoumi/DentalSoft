package ma.dentalSoft.model;

public class Utilisateur {
    private int id;
    private String name;
    private String role;
    private String email;
    private String telephone;
    private String motDePasse;

    public Utilisateur(int id, String name, String role, String email, String telephone, String motDePasse) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
    }

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

    public String getMotDePasse() {
        return motDePasse;
    }
}
