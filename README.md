# QAP

My implementation of QAP problem solving algorithm for Artificial Inteligence Classes.

##How to use?

Just run the main file. All the setting are located in `GeneticAlg.java` class. 

I've created a method to initialize algorithm variables.

Just find that line (`GeneticAlg.java` - line 53)

`GeneticAlg.initializeAlgorithmVariables(100, 100, 0.16, 0.7, 50);`

And change the parameters. API:

`(population_size, generations, probOfMutation, probOfCrossing, tournament_size)`

I've also prvided two selection methods - tournament and rouletteWheel. 

Just comment/uncomment line 56 or 57 `GeneticAlg.java` - depends on desired selection method.

If you want to use other file (had 12,14,16,18,20) go to line 39 `GeneticAlg.java` and change the specified file name.

## Features
 
- Order 1 Crossover
- Roulette Selection
- Tournament Selection (various tournament size)
- Providing Best, Average and Worst fitness for every generation
- Easy CSV data export and settings customization
