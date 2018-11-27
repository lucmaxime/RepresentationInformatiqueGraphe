package Modele.Utils;

import Modele.Noeud;
import java.util.Comparator;

public class DijkstraNodeComparator implements Comparator<Noeud>{

  public int compare(Noeud arg0, Noeud arg1) {
    Noeud one =  arg0;
    Noeud two =  arg1;

    if (one.getDijkstraPoids() < two.getDijkstraPoids())
      return -1;
    else if (one.getDijkstraPoids() > two.getDijkstraPoids())
      return 1;
    else
      return 0;

  }
}
