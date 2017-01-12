package controller.algorithm;

import java.util.ArrayList;

public class Population {

    Individual[] individuals;
    Individual fittest;
    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual(FitnessCalc.getPista());
                saveIndividual(i, newIndividual);
            }
            getFittest();
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    public ArrayList<Individual> getFittest(int number ) {
        ArrayList<Individual> fittestArray = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();

        while(fittestArray.size()!=number)
        {
            fittest = individuals[0];
            for (int i = 1; i < size(); i++) {
                if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                    if(!index.contains(new Integer(i))) {
                        fittest = getIndividual(i);
                        index.add(new Integer(i));
                    }
                }
            }
            fittestArray.add(fittest);
        }
        return fittestArray;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
