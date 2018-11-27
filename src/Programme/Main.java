package Programme;

import Modele.Arc;
import Modele.Arcs.EstAmi;
import Modele.Graphe;
import Modele.Noeud;
import Modele.Noeuds.Personne;
import Modele.Triplet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        Graphe g = new Graphe("G4");

        //On va donc ajouter un arc en instancciant directement dedans les noeuds spécifiques + arc spécifique
        Personne person1 = new Personne("V1",false);
        Personne person2 = new Personne("V2", false);
        Personne person3 = new Personne("V3", false);
        Personne person4 = new Personne("V4", false);
        Personne person5 = new Personne("V5", false);
        Personne person6 = new Personne("V6", false);
        Personne person7 = new Personne("V7", false);


        g.ajouterArc(new EstAmi("amitié1",2,person2,person1));
        g.ajouterArc(new EstAmi("amitié2",3,person4,person2));
        g.ajouterArc(new EstAmi("amitié3",1,person4,person1));
        g.ajouterArc(new EstAmi("amitié4",10,person5,person2));
        g.ajouterArc(new EstAmi("amitié5",2,person5,person4));
        g.ajouterArc(new EstAmi("amitié6",6,person7,person5));
        g.ajouterArc(new EstAmi("amitié7",4,person7,person4));
        g.ajouterArc(new EstAmi("amitié8",1,person6,person2));
        g.ajouterArc(new EstAmi("amitié9",8,person6,person4));
        g.ajouterArc(new EstAmi("amitié10",5,person6,person3));
        g.ajouterArc(new EstAmi("amitié11",4,person1,person3));

        //Test de dijikstra

        g.dijkstra(person1);


        System.out.println("Voici mon vppc du noeud person1");
        for (Triplet triplet : person1.getVpcc().values()){

          System.out.print(triplet.getNom()+" "+triplet.getDijikstraPoids());

          if (triplet.getDijikstraNoeudPrecedent() == null) {
            System.out.println(" Premier noeud null");
          } else {
            System.out.println(triplet.getDijikstraNoeudPrecedent().getNom());
          }
        }
    }
    //Fonction qui va nous retourner une liste d'amis 2ème niveau qui sont célibataire et qui regarde Netflix
    private List<Noeud> getmis2èmeNiveauCélibataireNetflix(Noeud noeudDépart, Graphe g){
        List<Noeud> listFinal = new ArrayList<>();
        List<Personne> listAmis2èmeNiveua = (List<Personne>) (Noeud) g.parcoursLargeur(noeudDépart, 2, EstAmi.class);

        for (Personne noeudCourrant : listAmis2èmeNiveua){
            if(noeudCourrant.isEnCouple()){
                for(Arc arcCourrant : noeudCourrant.getListeArcSortants().values()){
                    if(arcCourrant.getNoeudDestination().getNom().equals("Netflix")){
                        listFinal.add(noeudCourrant);
                    }

                }
            }
        }
        return listFinal;

    }
}
