package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Cinema;
import Modele.Noeuds.Personne;

public class Regarde extends Arc {

  public Regarde(String nom, Integer metrique, Noeud noeudDestination,
      Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if(!(noeudDestination instanceof Cinema)){
      throw new IllegalArgumentException("Lien de regarde ne pointe pas sur un cinema");
    }
  }
}
