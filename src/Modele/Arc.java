package Modele;

public class Arc {

    private String nom;
    private Integer metrique;
    private Noeud noeudDestination;
    private Noeud noeudSource;

    public Arc() {
    }

    public Arc(String nom, Integer metrique, Noeud noeudDestination, Noeud noeudSource) {
    this.nom = nom;
    this.metrique = metrique;
    this.noeudDestination = noeudDestination;
    this.noeudSource = noeudSource;
  }

    public Noeud getNoeudSource() {
    return noeudSource;
  }

    public void setNoeudSource(Noeud noeudSource) {
      this.noeudSource = noeudSource;
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
      return ("("+this.nom+","+this.noeudDestination.getNom()+","+this.noeudSource.getNom()+","+this.metrique+")");
    }
}
