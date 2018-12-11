package Modele.Noeuds;

import Modele.Noeud;
import java.io.Serializable;

public class Personne extends Noeud implements Serializable {
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
