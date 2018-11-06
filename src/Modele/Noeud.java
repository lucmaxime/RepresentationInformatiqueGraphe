package Modele;

import java.util.HashMap;

public abstract class  Noeud {

    private String nom;
    private Boolean marque;
    private Integer niveau;


  private HashMap<String, Arc> listeArcSortants;
    private HashMap<String, Arc> listArcEntrants;

    public Noeud(String nom) {
      this.nom = nom;
      this.listeArcSortants = new HashMap<>();
      this.listArcEntrants = new HashMap<>();
      this.marque = false;
      this.niveau = 0;
    }
    public Integer getNiveau() {
      return niveau;
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
    public void ajouterArcAuNoeud(Arc arc){
      if(this == arc.getNoeudSource()) {
        this.ajouterArcSortant(arc);
      }else if (this == arc.getNoeudDestination()) {
        this.ajouterArcEntrant(arc);
      }
    }
    private void ajouterArcSortant(Arc arc){
      //Prendre en compte la généricité dans la construction
      this.listeArcSortants.put(arc.getNom(), arc);
    }

    private void ajouterArcEntrant(Arc arc){
      this.listArcEntrants.put(arc.getNom(), arc);
    }

    public Arc rechercherArcSortant(String nom){
      return this.listeArcSortants.get(nom);
    }

    public Arc rechercherArcEntrant(String nom){
      return this.listArcEntrants.get(nom);
    }


    public void supprimerArcSortant(String nom){
      this.listeArcSortants.remove(nom);
    }

    public void supprimerArcEntrant(String nom){
      this.listArcEntrants.remove(nom);
    }

    @Override
    public String toString(){
      String sortie = "(";

      for (Arc arcCourrant : listeArcSortants.values()) {
        sortie = sortie + arcCourrant.toString();
     }
      sortie = sortie+")";
      return sortie;
    }
}
