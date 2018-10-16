package Modele;

import java.util.HashMap;
import java.util.Iterator;

public class Graphe {
    private String nom;
    private HashMap <String, Noeud> listeNoeuds;

    @Override
    public String toString(){
        String sortie = "";
        for (HashMap.Entry mapentry : listeNoeuds.entrySet()) {
            sortie = sortie +"Noeud :"+mapentry.getKey()+" Arcs sortants du Noeud :"+mapentry.getValue().toString()+"\n";
        }
        return sortie;
    }

    public Graphe(String nom) {
        this.nom = nom;
        this.listeNoeuds = new HashMap<>();
    }


    public String getNom() {
        return nom;
    }

    public HashMap<String, Noeud> getListeNoeuds() {
        return listeNoeuds;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListeNoeuds(HashMap<String, Noeud> listeNoeuds) {
        this.listeNoeuds = listeNoeuds;
    }


    public void ajouterNoeud(String nom){
      Noeud noeud = new Noeud(nom);
      this.listeNoeuds.put(nom, noeud);
    }

    public void supprimerNoeud(Noeud victim) {
            //TECHNIQUE AVEC HASHMAP QUI SOULEVE UNE CONCURENTMODIFICATIONEXCEPTION!
        for (HashMap.Entry mapentry : listeNoeuds.entrySet()) {
            Noeud noeud = (Noeud) mapentry.getValue();
            for (HashMap.Entry mapentry2 : noeud.getListeArcSortants().entrySet()){
                Arc arc = (Arc) mapentry2.getValue();
                if(arc.getNoeudDestination().getNom() == victim.getNom()){
                    noeud.getListeArcSortants().remove(arc.getNom());
                }
            }
        }
        this.listeNoeuds.remove(victim.getNom());
    }

    public Noeud rechercherNoeud(String nom){
        return this.listeNoeuds.get(nom);
    }

    public void ajouterArc(String source, String destination, String nom, Integer metrique){
      if (!this.listeNoeuds.containsKey(destination)){
          this.ajouterNoeud(destination);
      }
      if (!this.listeNoeuds.containsKey(source)){
        this.ajouterNoeud(source);
      }
        rechercherNoeud(source).ajouterArcSortant(nom,metrique,rechercherNoeud(destination));
    }
    public Arc rechercherArc(String nom){
        Arc arcTrouve = new Arc();
        for (HashMap.Entry mapentry : listeNoeuds.entrySet()) {
            Noeud noeud = (Noeud) mapentry.getValue();
            if (noeud.getListeArcSortants().containsKey(nom)) {
                arcTrouve = noeud.rechercherArcSortant(nom);
            }else{
                System.out.println("arc non trouvé!Arc vide retourné");
            }
        }
        return arcTrouve;
    }

    public void supprimerArc(String nom){

        for (HashMap.Entry mapentry : listeNoeuds.entrySet()) {
            Noeud noeud = (Noeud) mapentry.getValue();
            if (noeud.getListeArcSortants().containsKey(nom)) {
                noeud.supprimerArcSortant(nom);
            }else{
                System.out.println("Arc pas trouvé!");
            }
        }
    }
}

