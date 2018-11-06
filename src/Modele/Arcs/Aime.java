package Modele.Arcs;

import Modele.Arc;
import Modele.Noeud;
import Modele.Noeuds.Conference;
import Modele.Noeuds.Personne;

public class Aime extends Arc {

  public Aime(String nom, Integer metrique, Noeud noeudDestination, Noeud noeudSource) {
    super(nom, metrique, noeudDestination, noeudSource);
    if(!(noeudDestination instanceof Conference)){
      throw new IllegalArgumentException("Lien de like ne pointe pas sur une conference");
    }
  }
}
