package Programme;

import Modele.Graphe;
import Modele.Noeud;
import java.util.List;

public class Main {
    public static void main(String[] args){

        Graphe g = new Graphe("G4");

        g.ajouterArc("A","B","u1",2);
        g.ajouterArc("A","C","u2",1);
        g.ajouterArc("B","C","u3",5);
        g.ajouterArc("C", "D", "u4", 6);
        g.ajouterArc("B", "D", "u5", 7);
        g.ajouterArc("B", "E", "u6", 7);
        g.ajouterArc("E", "D", "u7", 7);
        //on ajoute un Arc  qu'on va supprimer
        g.ajouterArc("E", "F", "u9", 3);


        System.out.println("Voici tous les noeuds et Arc de mon Graphe");
        System.out.println(g.toString());

        System.out.println("Voici après suppression du Noeud F \n");
        g.supprimerNoeud("F");
        System.out.println(g.toString());

        System.out.println("Test d'un parcours de noeuds en profondeur\n");
        List<Noeud> listNoeudProfondeur = g.parcoursProfondeur(g.rechercherNoeud("A"));
        for (Noeud noeudCourrant : listNoeudProfondeur){
          System.out.println(noeudCourrant.getNom()+" : "+noeudCourrant.toString());
        }

        System.out.println("Test d'un parcours de noeuds en largeur\n");
        List<Noeud> listNoeudLargeur = g.parcoursLargeur(g.rechercherNoeud("A"),1);
        for (Noeud noeudCourrant : listNoeudLargeur){
          System.out.println(noeudCourrant.getNom()+" : "+noeudCourrant.toString());
        }

    }
}
