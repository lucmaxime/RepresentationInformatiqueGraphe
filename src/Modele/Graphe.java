package Modele;

import Modele.Arcs.EstAmi;
import Modele.Noeuds.Personne;
import Modele.Utils.DijkstraNodeComparator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Graphe implements Serializable {

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

  public void ajouterNoeud(Noeud noeud) {
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

  public List<Noeud> parcoursLargeur(Noeud noeudDepart, int niveauMax, Class arcType) {
    this.reinitialisationMarqueGraphe();
    LinkedList<Noeud> file = new LinkedList<>();
    noeudDepart.setMarque(true);
    List<Noeud> parcours = new ArrayList<>();
    //Initialisation du niveau 0
    noeudDepart.setNiveau(0);
    //Tester le type de noeud?
    file.addFirst(noeudDepart);

    while (!file.isEmpty()) {
      //on trouve le dernier noeud de la file et l'ajoute dans le parcours
      Noeud noeudCourrant = file.removeLast();
      parcours.add(noeudCourrant);
      //On parcourt les arcs pour trouver les noeuds voisins et si le noeud n'est pas encore en mêmoire on le marque + ajout en mêmoire
      for (Arc arcCourrant : noeudCourrant.getListeArcSortants().values()) {
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

  public List<Noeud> parcoursProfondeur(Noeud noeudDepart) {
    this.reinitialisationMarqueGraphe();
    Stack<Noeud> pile = new Stack<>();
    noeudDepart.setMarque(true);
    List<Noeud> parcours = new ArrayList<>();

    pile.push(noeudDepart);
    while (!pile.isEmpty()) {
      //on trouve le dernier noeud de la pile et l'ajoute dans le parcours
      Noeud noeudCourrant = pile.pop();
      parcours.add(noeudCourrant);
      //On parcourt les arcs pour trouver les noeuds voisins et si le noeud n'est pas encore en mêmoire on le marque + ajout en mêmoire
      for (Arc arcCourrant : noeudCourrant.getListeArcSortants().values()) {
        Noeud noeudDestination = arcCourrant.getNoeudDestination();
        if (!noeudDestination.getMarque()) {
          noeudDestination.setMarque(true);
          pile.push(noeudDestination);
        }
      }
    }
    return parcours;
  }

  public List<Noeud> getAmisNiveau2CelibParHobby(Noeud noeudDepart, String hobby) {

    List<Noeud> listeAmisNiveau2 = this.parcoursLargeur(noeudDepart, 2, EstAmi.class);
    List<Noeud> listeResultat = new ArrayList<>();
    for (Noeud noeudCourrant : listeAmisNiveau2) {
      //Cast de l'objet
      Personne personneCourrant = (Personne) noeudCourrant;
      if (!personneCourrant.isEnCouple()) {
        for (Arc arcCourrant : personneCourrant.getListeArcSortants().values()) {
          if (arcCourrant.getNoeudDestination().getNom().equals(hobby)) {
            listeResultat.add(noeudCourrant);
          }
        }
      }
    }
    return listeResultat;
  }

  public boolean contientNoeud(List<Arc> listArcSortant, String nomNoeud) {
    for (Arc arcCourrant : listArcSortant) {
      if (arcCourrant.getNoeudDestination().getNom().equals(nomNoeud)) {
        return true;
      }
    }
    return false;
  }

  public void reinitialisationMarqueGraphe() {
    for (Noeud noeudCourrant : this.listeNoeuds.values()) {
      noeudCourrant.setMarque(false);
    }
  }

  public void ajouterArc(Arc arc) {
    if (!this.listeNoeuds.containsKey(arc.getNoeudDestination().getNom())) {
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
    for (HashMap.Entry<String, Noeud> mapentry : listeNoeuds.entrySet()) {
      Noeud noeud = mapentry.getValue();
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

  //Calcul du plus court chemin d'un Noeud source à un Noeud destination (En utilisant le dijkstra)

  public List<Triplet> VPCC(String source, String destination) {
    Noeud noeudSource = rechercherNoeud(source);
    Noeud noeudDestination = rechercherNoeud(destination);

    //On execute dijstra si le vpcc du noeudSource est vide
    if (noeudSource.getVpcc().size() == 0) {
      dijkstra(noeudSource);
    }

    //On vérifie que la destination est vraiment atteignable
    if (noeudSource.getVpcc().containsKey(noeudDestination.getNom())) {
      List<Triplet> listVPCC = new LinkedList<>();
      HashMap<String, Triplet> vpcc = noeudSource.getVpcc();

      //On récupère le triplet de notre noeud de destination et on le parcourt dans le sens inverse
      Triplet tripletCourrant = vpcc.get(noeudDestination.getNom());
      while (tripletCourrant.getDijikstraNoeudPrecedent() != null) {
        ((LinkedList<Triplet>) listVPCC).addFirst(tripletCourrant);
        tripletCourrant = vpcc.get(tripletCourrant.getDijikstraNoeudPrecedent().getNom());
      }
      return listVPCC;
    } else {
      return null;
    }

  }

  //Calcul du plus court chemin pour un noeud donné pour tout les autres noeuds du graphe(stocker dans sa variable vpcc sous forme de triplet)
  public void dijkstra(Noeud depart) {

    //Initialisation des variables dans notre graphe
    for (Noeud noeudCourrant : this.listeNoeuds.values()) {
      noeudCourrant.setDijkstraNoeudPrecedent(null);
      noeudCourrant.setDijkstraPoids(Integer.MAX_VALUE);
    }

    List<Noeud> memoire = new ArrayList<>();

    //Ajout du noeud de départ à la mêmoire pour commencer par lui
    memoire.add(depart);
    depart.setDijkstraPoids(0);

    while (!memoire.isEmpty()) {

      //On ordonne la liste selon le plus petit dijistrapoids en premier
      memoire.sort(new DijkstraNodeComparator());

      //On récupère(+supprime) ici le plus petit poid de la liste en mêmoire qui est maintenant en premier
      Noeud noeudCourant = memoire.get(0);
      memoire.remove(0);

      //On ajoute à notre chemin le plus court le triplet(infos) du noeud noeudCourant (Toujours avec l'ordre du plus petit en premier)
      depart.getVpcc().put(noeudCourant.getNom(),
          new Triplet(noeudCourant.getNom(), noeudCourant.getDijkstraPoids(),
              noeudCourant.getDijkstraNoeudPrecedent()));

      //Pour tous les arcs sortants
      for (Arc arcCourrant : noeudCourant.getListeArcSortants().values()) {
        Noeud noeudDestination = arcCourrant.getNoeudDestination();

        //On ajoute ici un noeud à la mêmoire si c'est un nouveau noeud!
        if (noeudDestination.getDijkstraPoids() == Integer.MAX_VALUE) {
          memoire.add(noeudDestination);
        }

        Integer poidsCourrant = noeudCourant.getDijkstraPoids() + arcCourrant.getMetrique();
        //on met ici à jours le noeud dans la mêmoire si le poids du chemin est plus petit !
        if (poidsCourrant < noeudDestination.getDijkstraPoids()) {
          noeudDestination.setDijkstraPoids(poidsCourrant);
          noeudDestination.setDijkstraNoeudPrecedent(noeudCourant);
        }
      }
    }
  }

  //Initialisation des degrés de chaque Noeuds du graphe
  public void calculDegres() {
    for (Noeud noeudCourrant : this.listeNoeuds.values()) {

      noeudCourrant.setDegreeEntrant(noeudCourrant.getListArcEntrants().size());

      noeudCourrant.setDegreeSortant(noeudCourrant.getListeArcSortants().size());

    }
  }

  public HashMap<Integer, List<Noeud>> triTopologique() throws IOException, ClassNotFoundException {

    HashMap<Integer, List<Noeud>> hashTriTopo = new HashMap<>();
    int numeroDegree = 0;


    //On fait une copie du graphe pour pouvoir l'altérer sans supprimer dans le vrai
    Graphe grapheMemoire = copy(this);

    while (!grapheMemoire.getListeNoeuds().isEmpty()) {

      List<Noeud> listNoeudDegreCourrant = new ArrayList<>();

      //Màj à chaque parcourt des degrées
      grapheMemoire.calculDegres();

      //On parcourt les noeuds afin de trouver le noeud qui n'a pas/plus d'arc entrant
      for (Noeud noeudCourant : grapheMemoire.listeNoeuds.values()) {

        if (noeudCourant.getDegreeEntrant() == 0) {
          listNoeudDegreCourrant.add(noeudCourant);
          //Dès que la liste est plus longue, il y'a un cycle!
          if(listNoeudDegreCourrant.size()>grapheMemoire.getListeNoeuds().size()){
            return null;
          }
        }
      }

      //On va maintenant supprimer tous les noeuds trouvé dans notre graphe en mêmoire pour pouvoir le reparcourir sans ces noeuds
      if(!listNoeudDegreCourrant.isEmpty()) {
        for (Noeud noeudAsupprimer : listNoeudDegreCourrant) {
          grapheMemoire.supprimerNoeud(noeudAsupprimer.getNom());
        }
      }
      //Enfin on ajoute à notre liste et on incrémente notre degré
      hashTriTopo.put(numeroDegree, listNoeudDegreCourrant);

      numeroDegree = numeroDegree + 1;

    }
    return hashTriTopo;
  }


  //Fonction qui permet de copier un graphe
  private static Graphe copy(Graphe g) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ObjectOutputStream cout = new ObjectOutputStream(bout);
    cout.writeObject(g);
    byte[] bytes = bout.toByteArray();

    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
    ObjectInputStream cin = new ObjectInputStream(bin);
    Graphe clone = (Graphe) cin.readObject();
    clone.setNom(g.getNom() + "_Copy");
    return clone;
  }
}

