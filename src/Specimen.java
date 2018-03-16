import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Specimen implements Comparable<Specimen> {

    private int[] genes;
    private static int genesLength;
    private int fitness;
    private double assignmentFactor = 0;
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
    }

    Specimen(int[] locations) {
        this.genes = locations;
        fitness = 0;
        genesLength = genes.length;
        calculateFitness();
    }


//    void mutateSpecimen()
//    {
//        int index1 = Utilities.getRandom(this);
//        int index2 = Utilities.getRandom(this);
//        while(index1 == index2){
//            index2=Utilities.getRandom(this);
//        }
//        int temp = genes[index1];
//        genes[index1]=genes[index2];
//        genes[index2]=temp;
//    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public static int getGenesLength() {
        return genesLength;
    }

    public static void setGenesLength(int genesLength) {
        Specimen.genesLength = genesLength;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public static double getPofMutation() {
        return pofMutation;
    }

    public static void setPofMutation(double pofMutation) {
        Specimen.pofMutation = pofMutation;
    }

    public static int[][] getDistMatrix() {
        return distMatrix;
    }

    public static void setDistMatrix(int[][] distMatrix) {
        Specimen.distMatrix = distMatrix;
    }

    public static int[][] getFlowMatrix() {
        return flowMatrix;
    }

    public static void setFlowMatrix(int[][] flowMatrix) {
        Specimen.flowMatrix = flowMatrix;
    }

    private void initializeGenome() {
        for (int i = 0; i < genesLength; i++) {
            genes[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(genes));
    }

    public void calculateFitness() {
        this.fitness = 0;
        for (int i = 0; i < genesLength; i++)
            for (int j = (i + 1); j < (genesLength); j++) {
                this.fitness += distMatrix[i][j] * (flowMatrix[genes[i] - 1][genes[j] - 1] + flowMatrix[genes[j] - 1][genes[i] - 1]);
            }
    }

    public void mutateSpecimen() {
        double mutationChance;
        for (int i = 0; i < genes.length; i++) {
            mutationChance = Math.random();
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
    }

    public void sop() {
        System.out.println("Fitness nejlepszego osobnika to: " + fitness);
        System.out.println("Geny: ");
        StringBuilder g = new StringBuilder();
        for(int i = 0; i<genesLength; i++)
        {
            g.append(genes[i]).append(", ");
        }
        System.out.println(g);
    }

    public double getAssignmentFactor() {
        return assignmentFactor;
    }

    public void setAssignmentFactor(double assignmentFactor) {
        this.assignmentFactor = assignmentFactor;
    }

    public int getHelper() {
        return helper;
    }

    public void setHelper(int helper) {
        this.helper = helper;
    }
}
