/*
    Created by wroobell
*/
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Specimen implements Comparable<Specimen> {

    private int[] genes;
    private static int genesLength;
    private int fitness;
    private int helper = 0;

    private static int[][] distMatrix;
    private static int[][] flowMatrix;

    private static double pofMutation = 0.3;
    private Random rand = new Random();

    Specimen() {
        genes = new int[genesLength];
        initializeGenome();
        fitness = 0;
        calculateFitness();
    }//Specimen()

    Specimen(int[] locations) {
        this.genes = locations;
        fitness = 0;
        genesLength = genes.length;
        calculateFitness();
    }//Specimen(int[] locations)

    public static void setGenesLength(int genesLength) {
        Specimen.genesLength = genesLength;
    }//setGenesLength()

    public static void setPofMutation(double pofMutation) {
        Specimen.pofMutation = pofMutation;
    }//setPofMutation()

    public static void setDistMatrix(int[][] distMatrix) {
        Specimen.distMatrix = distMatrix;
    }//setDistMatrix()

    public static void setFlowMatrix(int[][] flowMatrix) {
        Specimen.flowMatrix = flowMatrix;
    }//setFlowMatrix()

    public int[] getGenes() {
        return genes;
    }//getGenes()

    public void setGenes(int[] genes) {
        this.genes = genes;
    }//setGenes()

    public int getFitness() {
        return fitness;
    }//getFitness()

    private void initializeGenome() {
        for (int i = 0; i < genesLength; i++) {
            genes[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(genes));
    }//initializeGenome()

    public void calculateFitness() {
        this.fitness = 0;
        for (int i = 0; i < genesLength; i++)
            for (int j = (i + 1); j < (genesLength); j++) {
                this.fitness += distMatrix[i][j] * (flowMatrix[genes[i] - 1][genes[j] - 1] + flowMatrix[genes[j] - 1][genes[i] - 1]);
            }
    }//calculateFitness()

    public void mutateSpecimen() {
        double mutationChance;
        for (int i = 0; i < genes.length; i++) {
            mutationChance = rand.nextDouble();
            if (mutationChance < pofMutation) {
                swapRandomGene(i);
            }
        }
    }//mutateSpecimen()

    private void swapRandomGene(int geneIndex) {
        // get random swap idx
        int swapIndex = rand.nextInt(genesLength);
        // exact swap
        int tempGene = this.genes[geneIndex];
        this.genes[geneIndex] = this.genes[swapIndex];
        this.genes[swapIndex] = tempGene;
    }//swapRandomGene()

    @Override
    public int compareTo(Specimen o) {
        return Integer.compare(this.fitness, o.getFitness());
    }//Custom comparator compareTo

    public void sop() {
        System.out.println("Best specimen fitness: " + fitness);
        System.out.println("Genes: ");
        StringBuilder g = new StringBuilder();
        for(int i = 0; i<genesLength; i++) {
            g.append(genes[i]).append(", ");
        }
        System.out.println(g);
    }//sop()

    public int getHelper() {
        return helper;
    }//getHelper()

    public void setHelper(int helper) {
        this.helper = helper;
    }//setHelper()
}
