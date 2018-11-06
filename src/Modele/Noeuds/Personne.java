package Modele.Noeuds;

import Modele.Noeud;

public class Personne extends Noeud {
  private boolean enCouple;

  public Personne(String nom, boolean enCouple) {
    super(nom);
    this.enCouple = enCouple;
  }

  public boolean isEnCouple() {
    return enCouple;
  }

  public void setEnCouple(boolean enCouple) {
    this.enCouple = enCouple;
  }

}
