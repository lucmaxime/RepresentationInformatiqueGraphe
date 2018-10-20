package Programme;

import Modele.Graphe;

public class Main {
    public static void main(String[] args){

        Graphe g = new Graphe("G4");

        g.ajouterArc("X1","X2","u3",2);
        g.ajouterArc("X2","X1","u4",1);
        g.ajouterArc("X1","X2","u5",5);
        g.ajouterArc("X3", "X1", "u6", 6);
        g.ajouterArc("X1", "X4", "u9", 7);

        System.out.println("Voici tous les noeuds et Arc de mon Graphe");
        System.out.println(g.toString());

        //Nullpointerexception si je mets avec X1??? (Marche car aucun arc n'a de destination X3)
        g.supprimerNoeud("X3");

        System.out.printf("Voici après suppression du Noeud X3 \n");
        System.out.println(g.toString());

    }
}
