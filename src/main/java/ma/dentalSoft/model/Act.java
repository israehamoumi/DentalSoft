package ma.dentalSoft.model;

public class Act {
    private Long id;
    private String nomActe;
    private String description;
    private double cout;

    // Constructeurs
    public Act() {
    }

    public Act(Long id, String nomActe, String description, double cout) {
        this.id = id;
        this.nomActe = nomActe;
        this.description = description;
        this.cout = cout;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomActe() {
        return nomActe;
    }

    public void setNomActe(String nomActe) {
        this.nomActe = nomActe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    @Override
    public String toString() {
        return nomActe + " (" + cout + " MAD)";
    }
}
