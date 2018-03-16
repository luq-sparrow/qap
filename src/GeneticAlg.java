import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GeneticAlg {

    private static int n;
    private static int[][] distance;
    private static int[][] flow;

    private static int generations = 100;

    private static void readInstance(String fileName) throws IOException {
        try (Scanner input = new Scanner(new File(fileName))) {
            n = input.nextInt();

            distance = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distance[i][j] = input.nextInt();
                }
            }

            flow = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    flow[i][j] = input.nextInt();
                }
            }
        }
    }

    private static void initializeAlgorithmVariables(int populationSize, int generationsQty, double probOfMutation, double probOfCrossing, int tournamentSize) throws IOException {
        readInstance("src/had14.txt");
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
    }

    public static void main() throws IOException {
//        GeneticAlg.initializeAlgorithmVariables(500, 200, 0.5, 0.7, 100);
        GeneticAlg.initializeAlgorithmVariables(100, 100, 0.01, 0.7, 5);
        Population p = new Population();
        for (int i = 0; i < generations; i++) {
            Utilities.tourSelection(p);
            //Utilities.rouletteWheel(p);
            showGenerationStats(i, p);
        }
        p.sortPopulation();
        p.getSpecimen(0).sop();
    }

    private static void showGenerationStats(int generation, Population p) {
        p.sortPopulation();
        double avg = p.getPopulationFitness() / p.getSize();
        int best = p.getSpecimen(0).getFitness();
        int worst = p.getSpecimen(Population.getPopulationSize() - 1).getFitness();

        System.out.println(generation + ", " + best + ", " + avg + ", " + worst);
    }
}
