package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Graphe {

  private String nom;
  private HashMap<String, Noeud> listeNoeuds;

  @Override
  public String toString() {
    String sortie = "";
    for (Noeud noeudCourrant : listeNoeuds.values()) {
      sortie =
          sortie + "Noeud :" + noeudCourrant.getNom() + " Arcs sortants du Noeud :" + noeudCourrant
              .toString() + "\n";
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


  public void ajouterNoeud(String nom) {
    Noeud noeud = new Noeud(nom);
    this.listeNoeuds.put(nom, noeud);
  }

  public Noeud rechercherNoeud(String nom) {
    return this.listeNoeuds.get(nom);
  }

  public void supprimerNoeud(String nom) {
    //TECHNIQUE OPTIMISE AVEC REDONDANCE DE VARIABLE POUR AMELIORER LA PERFORMANCE

    //On va trouver le noeud
    Noeud victim = rechercherNoeud(nom);

    //On récupère la liste d'arcs entrants du noeud victime
    HashMap<String, Arc> listArcEntrants = victim.getListArcEntrants();
    if (!listArcEntrants.isEmpty()) {
      //Parcours de la liste et on trouve chaque noeud concerné
      for (Arc arc : listArcEntrants.values()) {
        //On retrouve son noeud source et on supprime l'arc correspondant redondant dans ce noeud
        Noeud noeudSource = arc.getNoeudSource();
        noeudSource.supprimerArcSortant(arc.getNom());
      }
    }
    //On fait pareil pour la listeArcSortant
    HashMap<String, Arc> listArcSortants = victim.getListeArcSortants();
    if (!listArcSortants.isEmpty()) {
      //Parcours de la liste et on trouve chaque noeud concerné
      for (Arc arc : listArcSortants.values()) {
        //On retrouve son noeud destination et on supprime l'arc correspondant redondant dans ce noeud
        Noeud noeudDestination = arc.getNoeudDestination();
        noeudDestination.supprimerArcEntrant(arc.getNom());
      }
    }
    //On supprime le noeud de base
    this.listeNoeuds.remove(nom);
  }

  public List<Noeud> parcoursLargeur(Noeud noeudDepart){
    this.reinitialisationMarqueGraphe();
    LinkedList<Noeud> file = new LinkedList<>();
    noeudDepart.setMarque(true);
    List<Noeud> parcours = new ArrayList<>();
    file.addFirst(noeudDepart);

    while(!file.isEmpty()){
      //on trouve le dernier noeud de la file et l'ajoute dans le parcours
      Noeud noeudCourrant = file.removeLast();
      parcours.add(noeudCourrant);
      //On parcourt les arcs pour trouver les noeuds voisins et si le noeud n'est pas encore en mêmoire on le marque + ajout en mêmoire
      for(Arc arcCourrant : noeudCourrant.getListeArcSortants().values()){
        Noeud noeudDestination = arcCourrant.getNoeudDestination();
        if (!noeudDestination.getMarque()){
          noeudDestination.setMarque(true);
          file.addFirst(noeudDestination);
        }
      }
    }
    return parcours;
  }

  public List<Noeud> parcoursProfondeur(Noeud noeudDepart){
    this.reinitialisationMarqueGraphe();
    Stack<Noeud> pile = new Stack<>();
    noeudDepart.setMarque(true);
    List<Noeud> parcours = new ArrayList<>();

    pile.push(noeudDepart);

    while(!pile.isEmpty()){
      //on trouve le dernier noeud de la pile et l'ajoute dans le parcours
      Noeud noeudCourrant = pile.pop();
      parcours.add(noeudCourrant);
      //On parcourt les arcs pour trouver les noeuds voisins et si le noeud n'est pas encore en mêmoire on le marque + ajout en mêmoire
      for(Arc arcCourrant : noeudCourrant.getListeArcSortants().values()){
        Noeud noeudDestination = arcCourrant.getNoeudDestination();
        if (!noeudDestination.getMarque()){
          noeudDestination.setMarque(true);
          pile.push(noeudDestination);
        }
      }
    }
    return parcours;
  }

  public void reinitialisationMarqueGraphe(){
    for (Noeud noeudCourrant : this.listeNoeuds.values()){
      noeudCourrant.setMarque(false);
    }
  }

  public void ajouterArc(String source, String destination, String nom, Integer metrique) {
    if (!this.listeNoeuds.containsKey(destination)) {
      this.ajouterNoeud(destination);
    }
    if (!this.listeNoeuds.containsKey(source)) {
      this.ajouterNoeud(source);
    }
    //On rajoute ici dans le noeud source + destination de l'arc afin d'avoir une recherche plus opérationnel lors de la suppression
    rechercherNoeud(source)
        .ajouterArcSortant(nom, metrique, rechercherNoeud(destination), rechercherNoeud(source));
    rechercherNoeud(destination)
        .ajouterArcEntrant(nom, metrique, rechercherNoeud(destination), rechercherNoeud(source));
  }

  public Arc rechercherArc(String nom) {
    Arc arcTrouve = new Arc();
    for (HashMap.Entry mapentry : listeNoeuds.entrySet()) {
      Noeud noeud = (Noeud) mapentry.getValue();
      if (noeud.getListeArcSortants().containsKey(nom)) {
        arcTrouve = noeud.rechercherArcSortant(nom);
      } else {
        System.out.println("arc non trouvé!Arc vide retourné");
      }
    }
    return arcTrouve;
  }

  public void supprimerArc(String nom) {
    //Pas utilisé
    for (Noeud noeudCourrant : this.listeNoeuds.values()) {
      if (noeudCourrant.getListeArcSortants().containsKey(nom)) {
        noeudCourrant.supprimerArcSortant(nom);
      }
      if (noeudCourrant.getListArcEntrants().containsKey(nom)) {
        noeudCourrant.supprimerArcEntrant(nom);
      }
    }
  }
}

