package Programme;

import Modele.Arcs.Cuisine;
import Modele.Arcs.EstAmi;
import Modele.Arcs.Ecoute;
import Modele.Graphe;
import Modele.Noeud;
import Modele.Noeuds.Aliment;
import Modele.Noeuds.Conference;
import Modele.Noeuds.Personne;
import java.util.List;

public class Main {
    public static void main(String[] args){

        Graphe g = new Graphe("G4");

        //On va donc ajouter un arc en instancciant directement dedans les noeuds spécifiques + arc spécifique
        Personne person1 = new Personne("Arnaud",false);
        Personne person2 = new Personne("Maxime", false);
        Personne person3 = new Personne("Alessandro", false);


        g.ajouterArc(new EstAmi("amitié1",2,person1,person2));
        g.ajouterArc(new EstAmi("amitié2",3,person3,person2));
        g.ajouterArc(new EstAmi("amitié3",3,person3,person1));

        //Ici on test l'ajout d'un arc avec un noeud source qui n'est pas une personne
        //g.ajouterArc(new Cuisine("Cuisine1",4,new Personne("Moi", true),new Aliment("chocolat")));

        //Ici on test l'ajout d'un arc avec des noeuds incohérent
        //g.ajouterArc(new Ecoute("Ecoute1",5,new Conference("RechercheOperationnelle"),new Personne ("Maxime",false)));

        //on ajoute un Arc  qu'on va supprimer
        g.ajouterArc(new EstAmi("amitié4",3,new Personne("Jean",true),new Personne("Michel",true )));

        System.out.println("Voici tous les noeuds et Arc de mon Graphe");
        System.out.println(g.toString());

        /*
        System.out.println("Voici après suppression de l'arc amitié4 et des deux amis \n");
        g.supprimerArc("amitié4");
        g.supprimerNoeud("Jean");
        g.supprimerNoeud("Michel");
        System.out.println(g.toString());


       System.out.println("Test d'un parcours de noeuds en profondeur\n");
        List<Noeud> listNoeudProfondeur = g.parcoursProfondeur(g.rechercherNoeud("A"));
        for (Noeud noeudCourrant : listNoeudProfondeur){
          System.out.println(noeudCourrant.getNom()+" : "+noeudCourrant.toString());
        }
*/
        System.out.println("Test d'un parcours de noeuds en largeur suivant un type d'arc\n");
        List<Noeud> listNoeudLargeur = g.parcoursLargeur(g.rechercherNoeud("Maxime"),1, EstAmi.class);
        for (Noeud noeudCourrant : listNoeudLargeur){
          System.out.println(noeudCourrant.getNom()+" : "+noeudCourrant.toString());
        }
    }
}
