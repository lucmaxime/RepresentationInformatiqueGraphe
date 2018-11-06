package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Aliment;

public class Cuisine extends Arc {

  public Cuisine(String nom, Integer metrique, Noeud noeudDestination, Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if (!(noeudDestination instanceof Aliment)) {
      throw new IllegalArgumentException("Arc cook ne pointe pas sur un aliment!");
    }
  }
}
