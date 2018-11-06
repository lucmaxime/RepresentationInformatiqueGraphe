package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Personne;

public class EstAmi extends Arc {

  public EstAmi(String nom, Integer metrique, Noeud noeudDestination,
      Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if(!(noeudDestination instanceof Personne)){
      throw new IllegalArgumentException("Lien d'amitié ne pointe pas sur une personne");
    }
  }
}
