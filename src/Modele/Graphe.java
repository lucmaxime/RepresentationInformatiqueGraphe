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
            sortie = sortie + "Noeud :"+mapentry.getKey()+" Arcs sortants du Noeud :"+mapentry.getValue().toString()+"\n";
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

    public Noeud rechercherNoeud(String nom){
    return this.listeNoeuds.get(nom);
  }

    public void supprimerNoeud(String nom) {
      //TECHNIQUE OPTIMISE AVEC REDONDANCE DE VARIABLE POUR AMELIORER LA PERFORMANCE

      //On va trouver le noeud
      Noeud victim = rechercherNoeud(nom);

      //On récupère la liste d'arcs entrants
      HashMap <String, Arc> listArcEntrants = victim.getListArcEntrants();

      if (!listArcEntrants.isEmpty()) {
        //Parcours de la liste et on trouve chaque noeud concerné
        for (HashMap.Entry mapentry : listArcEntrants.entrySet()) {

          //On trouve un arc entrant et on le stock + son nom
          Arc arcEntrant = (Arc) mapentry.getValue();
          String arcNom = arcEntrant.getNom();

          //On retrouve son noeud source et on supprime l'arc correspondant redondant dans ce noeud
          Noeud noeudSource = arcEntrant.getNoeudSource();
          Noeud noeudSupp = this.listeNoeuds.get(noeudSource.getNom());

          noeudSupp.supprimerArcSortant(arcNom);

        }
      }
      //On fait pareil pour la listeArcSortant
      HashMap <String, Arc> listArcSortants = victim.getListeArcSortants();

      if (!listArcSortants.isEmpty()) {
        //Parcours de la liste et on trouve chaque noeud concerné
        for (HashMap.Entry mapentry : listArcSortants.entrySet()) {

          //On trouve un arc sortant et on le stock + son nom
          Arc arcSortant = (Arc) mapentry.getValue();
          String arcNom = arcSortant.getNom();

          //On retrouve son noeud destination et on supprime l'arc correspondant redondant dans ce noeud
          Noeud noeud = arcSortant.getNoeudDestination();
          Noeud noeudSupp = this.listeNoeuds.get(noeud.getNom());
          noeudSupp.supprimerArcEntrant(arcNom);
        }
      }
      //On supprime le noeud
      this.listeNoeuds.remove(nom);


    }


    public void ajouterArc(String source, String destination, String nom, Integer metrique){
      if (!this.listeNoeuds.containsKey(destination)){
          this.ajouterNoeud(destination);
      }
      if (!this.listeNoeuds.containsKey(source)){
        this.ajouterNoeud(source);
      }
        //On rajoute ici dans le noeud source + destination de l'arc afin d'avoir une recherche plus opérationnel lors de la suppression
        rechercherNoeud(source).ajouterArcSortant(nom,metrique,rechercherNoeud(destination), rechercherNoeud(source));
        rechercherNoeud(destination).ajouterArcEntrant(nom,metrique,rechercherNoeud(destination), rechercherNoeud(source));
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

