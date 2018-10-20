package Modele;

import java.util.HashMap;

public class Noeud {

    private String nom;

    private HashMap<String, Arc> listeArcSortants;
    private HashMap<String, Arc> listArcEntrants;

    public Noeud(String nom) {
      this.nom = nom;
      this.listeArcSortants = new HashMap<>();
      this.listArcEntrants = new HashMap<>();
    }

    public String getNom() {
      return nom;
    }

    public void setNom(String nom) {
    this.nom = nom;
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


    public void ajouterArcSortant(String nom, Integer metrique, Noeud destination, Noeud source){
      Arc arc = new Arc(nom, metrique, destination, source);
      this.listeArcSortants.put(nom, arc);
    }

    public void ajouterArcEntrant(String nom, Integer metrique, Noeud destination, Noeud source){
      Arc arc = new Arc(nom, metrique, destination, source);
      this.listArcEntrants.put(nom, arc);
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
      for (HashMap.Entry mapentry : listeArcSortants.entrySet()) {
        sortie = sortie + ","+mapentry.getValue().toString();
     }
      sortie = sortie +")";
      return sortie;
    }
}
