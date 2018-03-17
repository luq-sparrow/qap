/*
    Created by wroobell
*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GeneticAlg {

    private static int n;
    private static int[][] distance;
    private static int[][] flow;

    private static int generations = 100;

    private static void readInstance(String fileName) throws IOException {
        //This method is used to read all the instance data
        try (Scanner input = new Scanner(new File(fileName))) {
            n = input.nextInt();

            distance = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distance[i][j] = input.nextInt();
                }//for
            }//for

            flow = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    flow[i][j] = input.nextInt();
                }//for
            }//for
        }//try
    }

    private static void initializeAlgorithmVariables(int populationSize, int generationsQty, double probOfMutation, double probOfCrossing, int tournamentSize) throws IOException {
        readInstance("src/had16.txt"); // Change this line if you want to use different file.
        Specimen.setGenesLength(n);
        Specimen.setPofMutation(probOfMutation);
        Specimen.setDistMatrix(distance);
        Specimen.setFlowMatrix(flow);
        Utilities.setProbOfCrossing(probOfCrossing);
        Utilities.setN(n);
        Utilities.setTsize(tournamentSize);
        Population.setPopulationSize(populationSize);
        Utilities.setPopulationSize(populationSize);
        generations = generationsQty;
    }//initializeAlgorithVariables()

    public static void runGeneticAlg() throws IOException { // Here you can set all the parameters
        GeneticAlg.initializeAlgorithmVariables(100, 100, 0.16, 0.7, 50);

        Population p = new Population();
        for (int i = 0; i < generations; i++) { // If you want to use roulette selection
            Utilities.tourSelection(p);         // just comment this line
            //Utilities.rouletteWheel(p);       // and uncomment that one.
            showGenerationStats(i, p);
        }//for
        p.sortPopulation();
        p.getSpecimen(0).sop();
    }//runGeneticAlg()

    private static void showGenerationStats(int generation, Population p) {
        // this metod is used to show stats for generations.
        // You can use it to generate CSV data.
        // Just change the s.o.p layout.
        p.calculateFitness();
        p.sortPopulation();
        double avg = p.getPopulationFitness() / p.getSize();
        int best = p.getSpecimen(0).getFitness();
        int worst = p.getSpecimen(Population.getPopulationSize() - 1).getFitness();

        System.out.println("Generation: " + generation + "; Best: " + best + "; Avg: " + avg + "; Worst: " + worst);
    }//showGenerationStats()
}
