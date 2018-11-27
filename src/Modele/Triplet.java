package Modele;

public class Triplet {

  private String nom;
  private Integer dijikstraPoids;
  private Noeud dijikstraNoeudPrecedent;

  public Triplet(String nom, Integer dijikstraPoids, Noeud dijikstraNoeudPrecedent) {
    this.nom = nom;
    this.dijikstraPoids = dijikstraPoids;
    this.dijikstraNoeudPrecedent = dijikstraNoeudPrecedent;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Integer getDijikstraPoids() {
    return dijikstraPoids;
  }

  public void setDijikstraPoids(Integer dijikstraPoids) {
    this.dijikstraPoids = dijikstraPoids;
  }

  public Noeud getDijikstraNoeudPrecedent() {
    return dijikstraNoeudPrecedent;
  }

  public void setDijikstraNoeudPrecedent(Noeud dijikstraNoeudPrecedent) {
    this.dijikstraNoeudPrecedent = dijikstraNoeudPrecedent;
  }
}
