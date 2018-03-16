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

    public static double getProbOfCrossing() {
        return probOfCrossing;
    }

    public static void setProbOfCrossing(double probOfCrossing) {
        Utilities.probOfCrossing = probOfCrossing;
    }

    public static int getTsize() {
        return tsize;
    }

    public static void setTsize(int tsize) {
        Utilities.tsize = tsize;
    }


    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        Utilities.n = n;
    }

    public static int getPopulationSize() {
        return populationSize;
    }

    public static void setPopulationSize(int p) {
        Utilities.populationSize = p;
    }
    // metoda Crossover Order I
    // zgodnie z tutorialem:
    // http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/Order1CrossoverOperator.aspx
    // zapewnia krzyżowanie osobników bez potrzeby ich naprawiania.

//    static Specimen CrossoverOrderI (Specimen parent1, Specimen parent2, int n)
//    {
//        // gets 2 parents and the n (length)
//        int[] tempP1 = parent1.genes;
//        int[] tempP2 = parent2.genes;
//        //randomly get 2 cut points
//        int cutPoint1 = getRandom(n);
//        int cutPoint2 = getRandom(n);
//        while (cutPoint1 == cutPoint2) { // make sure that cp1 < cp2
//            cutPoint1 = getRandom(n);
//            cutPoint2 = getRandom(n);
//        } //while (cutPoint1 == cutPoint2
//
//        // create the child
//        Specimen child = new Specimen(n);
//        int[] tempChild = child.genes;
//        int[] tempChild2 = child.genes;
//        // copy genes between cutPoint1 and cutPoint2 form parent1 to child
//        for(int i = cutPoint1; i<= cutPoint2; i++) {
//            tempChild[i] = tempP1[i];
//            tempChild2[i] = tempP2[i];
//        }
//
//        // select elements from Parent1 which are not in the child yet
//        int[] notInAChild = new int[n-(cutPoint2-cutPoint1) -1];
//        int j = 0;
//        for(int i = 0; i < n; i++) {
//            if(!arrayContains(tempChild, tempP1[i])){
//                notInAChild[j] = tempP1[i];
//                j++;
//            }
//        }
//
//        // rotate Parent2
//        // numbers of places is the same as the number of elements after cutPoint2
//        rotateArray(tempP2, n-cutPoint2-1);
//
//        //now order the elements in notInAChild according to their order in Parent2
//        int[] notInAChildYet = new int[n-(cutPoint2-cutPoint1)-1];
//        j=0;
//        for(int i=0; i < n; i++) {
//            if(arrayContains(notInAChild,tempP2[i])){
//                notInAChildYet[j] = tempP2[i];
//                j++;
//            }
//        }
//
//        //now copy remaining elements (in p1) into the child
//        //according to their order in p2.... starting after cutPoint2!
//        j=0;
//        for(int i = 0; i < notInAChildYet.length; i++) {
//            int currentIndex = (cutPoint2 + i + 1) % n; //current index
//            tempChild[currentIndex] = notInAChildYet[currentIndex];
//        }
//        child.setGenes(tempChild);
//        return child;
//    }
//
//    private static void rotateArray(int[] tempP2, int i) {
//    }

    // metoda Crossover Order I
    // zgodnie z tutorialem:
    // http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/Order1CrossoverOperator.aspx
    // zapewnia krzyżowanie osobników bez potrzeby ich naprawiania.
    private static Specimen crossoverOrderI(Specimen parentSpec1, Specimen parentSpec2) {

        int[] parent1 = parentSpec1.getGenes();
        int[] parent2 = parentSpec2.getGenes();

        Specimen childSpec = new Specimen();
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
    } //crossoverOrderI


    private static boolean arrayContains(int[] array, int num) {
        return IntStream.of(array).anyMatch(x -> x == num);
    } // arrayContains()

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
    } //randomNumber()

    public static void tourSelection(Population p) {
        List<Specimen> tempPopulation = new ArrayList<>(populationSize);
        List<Specimen> nexGen = new ArrayList<>(populationSize);

        int newGenerationCount = 0;
        while (newGenerationCount < populationSize) {
            int randIdx;
            for (int i = 0; i < tsize; i++) {
                randIdx = rand.nextInt(populationSize);
                tempPopulation.add(new Specimen(p.getSpecimen(randIdx).getGenes()));
            }
            tempPopulation.sort(Specimen::compareTo);
            nexGen.add(tempPopulation.get(0));
            newGenerationCount = newGenerationCount + 1;
            newGenerationCount = doCrossingOrMutate(nexGen, newGenerationCount);
            tempPopulation.clear();
        }
        p.setPopulation(nexGen);
    }

    public static void rouletteWheel(Population p) {
        List<Specimen> nexGen = new ArrayList<>(populationSize);
        p.sortPopulation();
        for (int i = 0; i < populationSize; i++) {
            p.getSpecimen(i).setHelper(i + 1);
        }
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
                }
            }
            if (nextGenCounter > 1) {
                nextGenCounter = doCrossingOrMutate(nexGen, nextGenCounter);
            }
        }
        p.setPopulation(nexGen);
    }

    private static int doCrossingOrMutate(List<Specimen> nexGen, int newGenerationCount) {
        double localProbOfCross;
        if (newGenerationCount % 2 == 0) {
            localProbOfCross = rand.nextDouble();
            if (localProbOfCross < probOfCrossing) {
                Specimen c1 = crossoverOrderI(nexGen.get(newGenerationCount - 1), nexGen.get(newGenerationCount - 2));
                Specimen c2 = crossoverOrderI(nexGen.get(newGenerationCount - 2), nexGen.get(newGenerationCount - 1));
                nexGen.add(c1);
                nexGen.add(c2);
                newGenerationCount = newGenerationCount + 2;
            } else {
                nexGen.get(newGenerationCount - 1).mutateSpecimen();
                nexGen.get(newGenerationCount - 2).mutateSpecimen();
            }
        }
        return newGenerationCount;
    }
}
