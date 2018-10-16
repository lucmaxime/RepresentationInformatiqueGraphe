package Modele;

import java.util.HashMap;

public class Noeud {

    private String nom;

    private HashMap<String, Arc> listeArcSortants;

    public Noeud(String nom) {
        this.nom = nom;
        this.listeArcSortants = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public HashMap<String, Arc> getListeArcSortants() {
        return listeArcSortants;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListeArcSortants(HashMap<String, Arc> listeArcSortants) {
        this.listeArcSortants = listeArcSortants;
    }


    public void ajouterArcSortant(String nom, Integer metrique, Noeud destination){
        Arc arc = new Arc(nom, metrique, destination);
        this.listeArcSortants.put(nom, arc);
    }

    public Arc rechercherArcSortant(String nom){
        return listeArcSortants.get(nom);
    }

    public void supprimerArcSortant(String nom){
        listeArcSortants.remove(nom);
    }

    @Override
    public String toString(){
        String sortie = "(";
        for (HashMap.Entry mapentry : listeArcSortants.entrySet()) {
            sortie = sortie +","+mapentry.getValue().toString();
        }
        sortie = sortie +")";
        return sortie;
    }
}
