/*
    Created by wroobell
*/
import java.util.ArrayList;
import java.util.List;

public class Population {

    private static int populationSize;
    private List<Specimen> population;
    private static int fitnessSum = 0;
    private int helperSum = 0;

    Population() {
        population = new ArrayList<>(populationSize);
        createPopulation();
    }//Population()

    public static int getPopulationSize() {
        return populationSize;
    }//getPopulationSize()

    public static void setPopulationSize(int populationSize) {
        Population.populationSize = populationSize;
    }//setPopulationSize(int populationSize)

    public void mutatePopulation() {
        for (int i = 0; i < populationSize; i++)
            this.population.get(i).mutateSpecimen();
    }//mutatePopulation()

    private void createPopulation() {
        for (int i = 0; i < populationSize; i++)
            population.add(new Specimen());
    }//createPopulation()

    public List<Specimen> getPopulation() {
        return population;
    }//getPopulation()

    public void setPopulation(List<Specimen> population) {
        this.population = population;
    }//setPopulation(List<Specimen> population)

    public Specimen getSpecimen(int i) {
        return this.population.get(i);
    }//getSpecimen(int i)

    public void addSpecimen(int i, Specimen s) {
        this.population.add(i, s);
    }//addSpecimen(int i, Specimen s);

    public void addSpecimen(Specimen s) {
        this.population.add(s);
    }//addSpecimen(Specimen s)

    public int getSize() {
        return this.population.size();
    }//getSize()

    public void sortPopulation() {
        this.population.sort(Specimen::compareTo);
    }//sortPopulation()

    public void calculateFitness() {
        for (Specimen s : population) {
            s.calculateFitness();
        }
    }//calculateFitenss()


    public int getPopulationFitness() {
        int fitnessSummary = 0;
        for (Specimen s : population) {
            fitnessSummary = fitnessSummary + s.getFitness();
        }
        return fitnessSummary;
    }//getPopulationFitness()

    public void calcHelperSum() {
        for (int i = 0; i < populationSize; i++) {
            helperSum = helperSum + (i + 1);
        }
    }//calcHelperSum

    public int getHelperSum() {
        return helperSum;
    }//getHelperSum
}
