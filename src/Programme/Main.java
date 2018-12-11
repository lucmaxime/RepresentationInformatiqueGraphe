package Programme;

import Modele.Arc;
import Modele.Arcs.EstAmi;
import Modele.Graphe;
import Modele.Noeud;
import Modele.Noeuds.Personne;
import Modele.Triplet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Graphe g = new Graphe("G4");

        //On va donc ajouter un arc en instancciant directement dedans les noeuds spécifiques + arc spécifique
        Personne person1 = new Personne("V1",false);
        Personne person2 = new Personne("V2", false);
        Personne person3 = new Personne("V3", false);
        Personne person4 = new Personne("V4", false);
        Personne person5 = new Personne("V5", false);
        Personne person6 = new Personne("V6", false);
        Personne person7 = new Personne("V7", false);
        Personne person8 = new Personne("V8", false);


        g.ajouterArc(new EstAmi("amitié1",2,person2,person1));
        g.ajouterArc(new EstAmi("amitié2",3,person5,person1));
        g.ajouterArc(new EstAmi("amitié3",1,person8,person1));
        g.ajouterArc(new EstAmi("amitié4",10,person4,person2));
        g.ajouterArc(new EstAmi("amitié5",2,person1,person3));
        g.ajouterArc(new EstAmi("amitié6",6,person5,person3));
        g.ajouterArc(new EstAmi("amitié7",4,person6,person3));
        g.ajouterArc(new EstAmi("amitié8",1,person7,person3));
        g.ajouterArc(new EstAmi("amitié9",8,person8,person3));
        g.ajouterArc(new EstAmi("amitié10",5,person8,person4));
        g.ajouterArc(new EstAmi("amitié11",4,person2,person5));
        g.ajouterArc(new EstAmi("amitié11",4,person6,person8));


        HashMap<Integer,List<Noeud>> triTopologique = g.triTopologique();

        for(Entry<Integer,List<Noeud>> entry : triTopologique.entrySet()){
          System.out.println("Rang numero :"+entry.getKey());
          for(Noeud noeudCourrant : entry.getValue()){
            System.out.println("Noeud :"+ noeudCourrant.getNom());
          }
        }
        /*

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

        //Test de VPCC
          System.out.println("Test de vpcc");
          List<Triplet> tripletList = g.VPCC("V1","V5" );

          for (Triplet triplet : tripletList){
            System.out.println(triplet.getNom()+" "+triplet.getDijikstraPoids()+" "+triplet.getDijikstraNoeudPrecedent().getNom());
          }
      */

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
