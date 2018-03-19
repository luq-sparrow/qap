# QAP

My implementation of QAP problem solving algorithm for Artificial Intelligence Classes.

##How to use?

Just run the main file. All the setting are located in `GeneticAlg.java` class. 

I've created a method to initialize algorithm variables.

Just find the lines below: (`GeneticAlg.java` - line `52`)

`public static void runGeneticAlg() throws IOException { // Here you can set all the parameters
        GeneticAlg.initializeAlgorithmVariables(100, 100, 0.16, 0.7, 50);
        Population p = new Population();
        for (int i = 0; i < generations; i++) { // If you want to use roulette selection
            Utilities.tourSelection(p);         // just comment this line
            //Utilities.rouletteWheel(p);       // and uncomment that one.
            showGenerationStats(i, p);
        }//for
        p.sortPopulation();
        p.getSpecimen(0).sop();
    }//runGeneticAlg()`

You can change the parameters of the algorithm inside that call:

`GeneticAlg.initializeAlgorithmVariables(100, 100, 0.16, 0.7, 50);`

API:

`(population_size, generations, probOfMutation, probOfCrossing, tournament_size)`

I've also provided two selection methods - tournament and rouletteWheel.
I use tournament by default, but you can change it by just commenting `Utilities.tourSelection(p);` and uncomenting next line.

If you want to use other file (had 12,14,16,18,20) go to line `39` of `GeneticAlg.java` and change the specified file name.

## Features
 
- Order 1 Crossover
- Roulette Selection
- Tournament Selection (various tournament size)
- Providing Best, Average and Worst fitness for every generation
- Easy CSV data export and settings customization (soon)
