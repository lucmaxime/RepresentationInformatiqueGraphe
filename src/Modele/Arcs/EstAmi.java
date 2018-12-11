package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Personne;
import java.io.Serializable;

public class EstAmi extends Arc implements Serializable {

  public EstAmi(String nom, Integer metrique, Noeud noeudDestination,
      Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if(!(noeudDestination instanceof Personne)){
      throw new IllegalArgumentException("Lien d'amitié ne pointe pas sur une personne");
    }
  }
}
