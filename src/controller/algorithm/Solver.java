package controller.algorithm;

import vo.Sudoku;

public class Solver {
    public static double mutationRate = 0.5;
    public static int tournamentSize = 10;
    public static int populationSize = 50;
    public static int elitism = 5;

    private Sudoku pista = new Sudoku();

    public Solver() {
    }

    public void setPista(Sudoku pista) {
        this.pista = pista;
    }

    public Sudoku solve() {
        FitnessCalc.setPista(pista);
        Population myPop = new Population(populationSize, true);
        int generation = 0;
        boolean fin = false;
        int lastFitness = 0;
        while (!fin) {
            if((myPop.getFittest().getFitness() == FitnessCalc.getMaxFitness()))
                fin = true;
            if(myPop.getFittest().getFitness()!=lastFitness) {
                lastFitness = myPop.getFittest().getFitness();
                System.out.println("Generation: " + (generation+1) + " Fittest: " + myPop.getFittest().getFitness());
            }
            generation++;
            myPop = Algorithm.evolvePopulation(myPop);
        }
        if(myPop.getFittest().getFitness()==FitnessCalc.getMaxFitness())
            System.out.println("Solution found! " +myPop.getFittest().getFitness() );
        else
            System.out.println("Solution not found! " +myPop.getFittest().getFitness() );

        return myPop.getFittest().getSudoku();
    }
}
