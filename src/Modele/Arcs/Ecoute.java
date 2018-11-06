package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Musique;
import Modele.Noeuds.Personne;

public class Ecoute extends Arc {

  public Ecoute(String nom, Integer metrique, Noeud noeudDestination,
      Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if(!(noeudDestination instanceof Musique)){
      throw new IllegalArgumentException("Lien d'écoute ne pointe pas sur une musique");
    }
  }
}
