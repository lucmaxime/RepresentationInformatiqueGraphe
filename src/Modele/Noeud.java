package Modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public abstract class Noeud implements Serializable {

  private String nom;
  private Boolean marque;
  private Integer niveau;
  private Integer DijkstraPoids;
  private Noeud DijkstraNoeudPrecedent;
  private Integer degreeEntrant;
  private Integer degreeSortant;
  private Integer ordoTot;
  private Integer ordoTard;

  private HashMap<String, Triplet> vpcc;
  private HashMap<String, Arc> listeArcSortants;
  private HashMap<String, Arc> listArcEntrants;

  public Noeud(String nom) {
    this.nom = nom;
    this.listeArcSortants = new HashMap<>();
    this.listArcEntrants = new HashMap<>();
    this.vpcc = new HashMap<>();
    this.marque = false;
    this.niveau = 0;
  }

  public Integer getOrdoTot() {
    return ordoTot;
  }

  public void setOrdoTot(Integer ordoTot) {
    this.ordoTot = ordoTot;
  }

  public Integer getOrdoTard() {
    return ordoTard;
  }

  public void setOrdoTard(Integer ordoTard) {
    this.ordoTard = ordoTard;
  }

  public HashMap<String, Triplet> getVpcc() {
    return vpcc;
  }

  public void setVpcc(HashMap<String, Triplet> vpcc) {
    this.vpcc = vpcc;
  }

  public Noeud getDijkstraNoeudPrecedent() {
    return DijkstraNoeudPrecedent;
  }

  public void setDijkstraNoeudPrecedent(Noeud dijkstraNoeudPrecedent) {
    DijkstraNoeudPrecedent = dijkstraNoeudPrecedent;
  }

  public Integer getDijkstraPoids() {
    return DijkstraPoids;
  }

  public void setDijkstraPoids(Integer dijkstraPoids) {
    DijkstraPoids = dijkstraPoids;
  }

  public Integer getNiveau() {
    return niveau;
  }

  public Integer getDegreeEntrant() {
    return degreeEntrant;
  }

  public void setDegreeEntrant(Integer degreeEntrant) {
    this.degreeEntrant = degreeEntrant;
  }

  public Integer getDegreeSortant() {
    return degreeSortant;
  }

  public void setDegreeSortant(Integer degreeSortant) {
    this.degreeSortant = degreeSortant;
  }

  public void setNiveau(Integer niveau) {
    this.niveau = niveau;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Boolean getMarque() {
    return marque;
  }

  public void setMarque(Boolean marque) {
    this.marque = marque;
  }

  public HashMap<String, Arc> getListArcEntrants() {
    return listArcEntrants;
  }

  public void setListArcEntrants(HashMap<String, Arc> listArcEntrants) {
    this.listArcEntrants = listArcEntrants;
  }

  public HashMap<String, Arc> getListeArcSortants() {
    return listeArcSortants;
  }

  public void setListeArcSortants(HashMap<String, Arc> listeArcSortants) {
    this.listeArcSortants = listeArcSortants;
  }

  //Suivant si on le lance sur un noeud qui est un noeud de destination ou source on va mettre arc entrant ou sortant
  public void ajouterArcAuNoeud(Arc arc) {
    if (this == arc.getNoeudSource()) {
      this.ajouterArcSortant(arc);
    } else if (this == arc.getNoeudDestination()) {
      this.ajouterArcEntrant(arc);
    }
  }

  private void ajouterArcSortant(Arc arc) {
    //Prendre en compte la généricité dans la construction
    this.listeArcSortants.put(arc.getNom(), arc);
  }

  private void ajouterArcEntrant(Arc arc) {
    this.listArcEntrants.put(arc.getNom(), arc);
  }

  public Arc rechercherArcSortant(String nom) {
    return this.listeArcSortants.get(nom);
  }

  public Arc rechercherArcEntrant(String nom) {
    return this.listArcEntrants.get(nom);
  }


  public void supprimerArcSortant(String nom) {
    this.listeArcSortants.remove(nom);
  }

  public void supprimerArcEntrant(String nom) {
    this.listArcEntrants.remove(nom);
  }

  @Override
  public String toString() {
    String sortie = "(";

    for (Arc arcCourrant : listeArcSortants.values()) {
      sortie = sortie + arcCourrant.toString();
    }
    sortie = sortie + ")";
    return sortie;
  }
}
