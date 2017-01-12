package controller.algorithm;

import java.util.Random;

import static controller.algorithm.Solver.*;

/**
 * Created by Fiser on 8/1/17.
 */
public class Algorithm {

    /* GA parameters */
    static Random generator = new Random();

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(Solver.populationSize, false);

        // Keep our best individual
        for(int i = 0; i<elitism; i++) {
            newPopulation.saveIndividual(i, pop.getFittest(elitism).get(i).clone());
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitism; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }
        // Mutate population
        for (int i = elitism; i < newPopulation.size(); i++) {
            newPopulation.getIndividual(i).mutate();
        }
        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {

        if(indiv1.getSudoku() == indiv2.getSudoku()) {
            return indiv1;
        }

        Individual child = indiv1.clone();

        for (int i = generator.nextInt(8)+1; i < 9; i++) {
            for(int j = 0; j<9; j++) {
                child.getSudoku().setCelda(indiv2.getSudoku().getCelda(i / 9, i % 9), i / 9, i % 9);
            }
        }
        return child;
    }


    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size()-1)+1;
            tournament.saveIndividual(i, pop.getIndividual(randomId).clone());
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}