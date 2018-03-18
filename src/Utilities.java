/*
    Created by wroobell
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

abstract class Utilities {

    private static Random rand = new Random();

    private static int tsize = 50;
    private static int populationSize = 100;
    private static double probOfCrossing = 0.3;

    private static int n = 0;

    public static void setProbOfCrossing(double probOfCrossing) {
        Utilities.probOfCrossing = probOfCrossing;
    }//setProbOfCrossing(double probOfCrossing)

    public static void setTsize(int tsize) {
        Utilities.tsize = tsize;
    }//setTsize(int tsize)

    public static void setN(int n) {
        Utilities.n = n;
    }//setN(int n)

    public static void setPopulationSize(int p) {
        Utilities.populationSize = p;
    }//setPopulationSize(int p)

    // Crossover Order I
    // According to tutorial:
    // http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/Order1CrossoverOperator.aspx
    // Provides crossing w/o need of fixing specimens.
    private static Specimen crossoverOrderI(Specimen parentSpec1, Specimen parentSpec2) {

        int[] parent1 = parentSpec1.getGenes();
        int[] parent2 = parentSpec2.getGenes();

        int[] child = new int[n];

        int beginning = randomNumber(n);
        int end = randomNumber(n);
        int InsertionsLeft = n - (end - beginning);

        while (beginning >= end) {
            beginning = randomNumber(n);
            end = randomNumber(n);
        } // while(beginning >= end)

//        System.out.println(beginning + "  " + end);
//        for(int i = beginning; i < end; i++)
//        {
//            child[i] = parent1[i];
//        }
        System.arraycopy(parent1, beginning, child, beginning, end - beginning);

        int insertIndex = end;
        int index = end;

        for (int j = 0; j < n && InsertionsLeft >= 0; j++, index++) {

            if (index >= n) {
                index = 0;
            }//if(index >= n)

            if (insertIndex >= n) {
                insertIndex = 0;
            }//if(insertIndex >= n)

            if (!arrayContains(child, parent2[index])) {
                InsertionsLeft--;
                child[insertIndex++] = parent2[index];
            }//if (!arrayContains(child, parent2[index]))
        } //for(int j = 0; j < n && InsertionsLeft >= 0; j++, index++)


        return new Specimen(child);
    } //crossoverOrderI(Specimen parentSpec1, Specimen parentSpec2)


    private static boolean arrayContains(int[] array, int num) {
        return IntStream.of(array).anyMatch(x -> x == num);
    } // arrayContains(int[] array, int num)

//    private static boolean arrayContains(int[] array, int number)
//    {
//        for (int i = 0; i < array.length; i++) {
//            if(array[i] == number)
//                return true;
//        }
//
//        return false;
//
//    } // arrayContains

    private static int randomNumber(int top) {
        return rand.nextInt(top);
    } //randomNumber(int top)

    public static void tourSelection(Population p) {
        List<Specimen> tempPopulation = new ArrayList<>(tsize);
        List<Specimen> nexGen = new ArrayList<>(populationSize);

        int newGenerationCount = 0;
        while (newGenerationCount < populationSize) {
            int randIdx;
            for (int i = 0; i < tsize; i++) {
                randIdx = rand.nextInt(populationSize);
                tempPopulation.add(new Specimen(p.getSpecimen(randIdx).getGenes()));
            }//for
            tempPopulation.sort(Specimen::compareTo);
            nexGen.add(tempPopulation.get(0));
            newGenerationCount = newGenerationCount + 1;
            newGenerationCount = doCrossingOrMutate(nexGen, newGenerationCount);
            tempPopulation.clear();
        }//while
        p.setPopulation(nexGen);
    }//tourSelection(Population p)

    public static void rouletteWheel(Population p) {
        List<Specimen> nexGen = new ArrayList<>(populationSize);
        p.calculateFitness();
        p.sortPopulation();
        for (int i = 0; i < populationSize; i++) {
            p.getSpecimen(i).setHelper(populationSize - i);
        }//for
        p.calcHelperSum();
        int totalSum = p.getHelperSum();
        int nextGenCounter = 0;
        while (nextGenCounter < populationSize) {
            int random = rand.nextInt(totalSum);
            int partialSum = 0;
            for (int j = 0; j < populationSize; j++) {
                partialSum += p.getSpecimen(j).getHelper();
                if (partialSum >= random) {
                    nexGen.add(new Specimen(p.getSpecimen(j).getGenes()));
                    nextGenCounter++;
                    break;
                }//if
            }//for
            if (nextGenCounter > 1) {
                nextGenCounter = doCrossingOrMutate(nexGen, nextGenCounter);
            }//if
        }//while
        p.setPopulation(nexGen);
    }//rouletteWheel(Population p)

    private static int doCrossingOrMutate(List<Specimen> nexGen, int newGenerationCount) {
        double localProbOfCross;
        if (newGenerationCount % 2 == 0) {
            localProbOfCross = rand.nextDouble();
            if (localProbOfCross < probOfCrossing) {
                nexGen.add(crossoverOrderI(nexGen.get(newGenerationCount - 1), nexGen.get(newGenerationCount - 2)));
                nexGen.add(crossoverOrderI(nexGen.get(newGenerationCount - 2), nexGen.get(newGenerationCount - 1)));
                newGenerationCount = newGenerationCount + 2;
                nexGen.get(newGenerationCount - 1).mutateSpecimen();
                nexGen.get(newGenerationCount - 2).mutateSpecimen();

            }//if
        }//if
        return newGenerationCount;
    }//doCrossingOrMutate(List<Specimen> nexGen, int newGenerationCount)
}
