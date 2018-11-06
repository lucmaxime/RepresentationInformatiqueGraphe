package Modele;

import Modele.Arcs.EstAmi;
import Modele.Noeuds.Personne;
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

  public void ajouterNoeud(Noeud noeud){
    this.listeNoeuds.put(noeud.getNom(), noeud);
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

  public List<Noeud> parcoursLargeur(Noeud noeudDepart, int niveauMax, Class arcType){
    this.reinitialisationMarqueGraphe();
    LinkedList<Noeud> file = new LinkedList<>();
    noeudDepart.setMarque(true);
    List<Noeud> parcours = new ArrayList<>();
    //Initialisation du niveau 0
    noeudDepart.setNiveau(0);
    //Tester le type de noeud?
    file.addFirst(noeudDepart);

    while(!file.isEmpty()){
      //on trouve le dernier noeud de la file et l'ajoute dans le parcours
      Noeud noeudCourrant = file.removeLast();
      parcours.add(noeudCourrant);
      //On parcourt les arcs pour trouver les noeuds voisins et si le noeud n'est pas encore en mêmoire on le marque + ajout en mêmoire
      for(Arc arcCourrant : noeudCourrant.getListeArcSortants().values()) {
        //Tester le type d'arc
        if (arcCourrant.getClass() == arcType) {
          Noeud noeudDestination = arcCourrant.getNoeudDestination();
          if (!noeudDestination.getMarque()) {
            //on lui attribue son level par rapport au noeudCourrant
            noeudDestination.setNiveau(noeudCourrant.getNiveau() + 1);
            noeudDestination.setMarque(true);
            if (noeudDestination.getNiveau() <= niveauMax) {
              file.addFirst(noeudDestination);
            }
          }
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

  public List<Noeud> getAmisNiveau2CelibParHobby(Noeud noeudDepart, String hobby){

    List<Noeud> listeAmisNiveau2 = this.parcoursLargeur(noeudDepart, 2, EstAmi.class);
    List<Noeud> listeResultat = new ArrayList<>();
    for(Noeud noeudCourrant : listeAmisNiveau2){
      //Cast de l'objet
      Personne personneCourrant = (Personne) noeudCourrant;
      if(!personneCourrant.isEnCouple()){
        for(Arc arcCourrant : personneCourrant.getListeArcSortants().values()){
          if(arcCourrant.getNoeudDestination().getNom().equals(hobby)){
            listeResultat.add(noeudCourrant);
          }
        }
      }
    }
    return listeResultat;
  }

  public boolean contientNoeud(List<Arc> listArcSortant, String nomNoeud){
    for(Arc arcCourrant : listArcSortant){
      if(arcCourrant.getNoeudDestination().getNom().equals(nomNoeud)){
        return true;
      }
    }
    return false;
  }

  public void reinitialisationMarqueGraphe(){
    for (Noeud noeudCourrant : this.listeNoeuds.values()){
      noeudCourrant.setMarque(false);
    }
  }

  public void ajouterArc(Arc arc) {
    if (!this.listeNoeuds.containsKey(arc.getNoeudDestination().getNom())){
      this.ajouterNoeud(arc.getNoeudDestination());
    }
    if (!this.listeNoeuds.containsKey(arc.getNoeudSource().getNom())) {
      this.ajouterNoeud(arc.getNoeudSource());
    }
    //On rajoute ici dans le noeud source + destination de l'arc afin d'avoir une recherche plus opérationnel lors de la suppression
    arc.getNoeudSource().ajouterArcAuNoeud(arc);
    arc.getNoeudDestination().ajouterArcAuNoeud(arc);
  }

  public Arc rechercherArc(String nom) {
    for (HashMap.Entry<String,Noeud> mapentry : listeNoeuds.entrySet()) {
      Noeud noeud =  mapentry.getValue();
      if (noeud.getListeArcSortants().containsKey(nom)) {
        return noeud.rechercherArcSortant(nom);
      }
    }
      throw new RuntimeException();
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

