package Programme;

import Modele.Graphe;

public class Main {
    public static void main(String[] args){

        Graphe g = new Graphe("G4");

        g.ajouterArc("X1","X2","u3",2);
        g.ajouterArc("X2","X1","u4",1);
        g.ajouterArc("X1","X2","u5",5);

        System.out.println("Voici tous les noeuds et Arc de mon Graphe");
        System.out.println(g.toString());

        //g.supprimerNoeud(g.rechercherNoeud("X2"));

        System.out.printf("Voic après suppression du Noeud X2");
        System.out.println(g.toString());


    }
}
