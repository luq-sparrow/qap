import java.util.ArrayList;
import java.util.List;

public class Population {

    private static int populationSize;
    private List<Specimen> population;

    Population() {
        population = new ArrayList<>(populationSize);
        createPopulation();
    }

    public void mutatePopulation() {
        for (int i = 0; i < populationSize; i++)
            this.population.get(i).mutateSpecimen();
    }

    private void createPopulation() {
        for (int i = 0; i < populationSize; i++)
            population.add(new Specimen());
    }

    public static int getPopulationSize() {
        return populationSize;
    }

    public static void setPopulationSize(int populationSize) {
        Population.populationSize = populationSize;
    }

    public List<Specimen> getPopulation() {
        return population;
    }

    public void setPopulation(List<Specimen> population) {
        this.population = population;
    }

    public Specimen getSpecimen(int i) {
        return this.population.get(i);
    }

    public void addSpecimen(int i, Specimen s) {
        this.population.add(i, s);
    }

    public void addSpecimen(Specimen s) {
        this.population.add(s);
    }

    public int getSize() {
        return this.population.size();
    }

    public void sortPopulation() {
        this.population.sort(Specimen::compareTo);
    }

    public void calculateFitness() {
        for (Specimen s : population) {
            s.calculateFitness();
        }
    }

    public int getPopulationFitness() {
        int fitnessSummary = 0;
        for (Specimen s : population) {
            fitnessSummary = fitnessSummary + s.getFitness();
        }
        return fitnessSummary;
    }
}
