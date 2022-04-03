package main ;
import ilog.concert.*;
import ilog.cplex.*;

public class APP {



    public static void main(String[] args) {

        calcul();
    }
    public static void calcul (){
        try {
            int n=38;
            IloCplex simplexe = new IloCplex ();

            // déclaration des Variables de décision de type reel
            IloNumVar[][] place = new IloNumVar [n][n];
            for (int i=0;i<n;i++){
                place[i][0]= simplexe.numVar(0, Double.MAX_VALUE);
            }

            // declaration de la fonction objectif
            IloLinearNumExpr objectif = simplexe.linearNumExpr();

            // définition des coefficients de la fonction objectif
            for (int i=0;i<n;i++){
                for (int j=0;j<n;j++){
                    objectif.addTerm(1, place[i][j]);
                    objectif.addTerm(1, place[j][i]);
                }
            }

            // Définir le type d'optimisation de la fonction (max)
            simplexe.addMaximize(objectif);


            // les contraintes 1
            for(int i = 0 ; i < n; i++) {
                for(int j = 0; j < n; j++) {

                    IloLinearNumExpr contrainte = simplexe.linearNumExpr();
                    contrainte.addTerm(1, place[i][j]);
                    simplexe.addGe(contrainte, 1);

                }
            }
            // les contraintes2
            for(int j = 0; j < n; j++){
                for(int i = 0; i < n; i++){

                    IloLinearNumExpr contrainte_2 = simplexe.linearNumExpr();
                    contrainte_2.addTerm(1, place[j][i]);
                    simplexe.addGe(contrainte_2, 1);

                }
            }

            simplexe.solve(); // lancer resolution

            // Afficher des résultat
            System.out.println("Voici la valeur de la fonction objectif "+ simplexe.getObjValue());
            System.out.println(" Voici les valeurs des variables de décision: ") ;
            for (int i=0;i<n;i++)
                System.out.println( "X"+i+ " = "+ simplexe.getValue(place[i][0]));
            for (int j=0;j<n;j++)
                System.out.println( "X"+j+ " = "+ simplexe.getValue(place[j][0]));
        } catch (IloException e){
            System.out.print("Exception levée " + e);
        }
    }
}