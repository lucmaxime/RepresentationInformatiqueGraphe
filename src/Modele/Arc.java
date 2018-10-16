package Modele;

public class Arc {

    private String nom;
    private Integer metrique;
    private Noeud noeudDestination;

    public Arc() {
    }

    public Arc(String nom, Integer metrique, Noeud noeudDestination) {
        this.nom = nom;
        this.metrique = metrique;
        this.noeudDestination = noeudDestination;
    }

    public String getNom() {
        return nom;
    }

    public Noeud getNoeudDestination() {
        return noeudDestination;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNoeudDestination(Noeud noeudDestination) {
        this.noeudDestination = noeudDestination;
    }

    public Integer getMetrique() {
        return metrique;
    }

    public void setMetrique(Integer metrique) {
        this.metrique = metrique;
    }

    @Override
    public String toString(){
        return ("("+this.nom+","+this.noeudDestination.getNom()+","+this.metrique+")");
    }
}