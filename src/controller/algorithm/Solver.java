package controller.algorithm;


import vo.Sudoku;

public class Solver {
    public final Float generations = 100f;
    public static final double uniformRate = 0.5;
    public static final double mutationRate = 0.015;
    public static final int tournamentSize = 5;
    public static final boolean elitism = true;

    public void setPista(Sudoku pista) {
        this.pista = pista;
    }

    Sudoku pista = new Sudoku();

    public Sudoku solve() {
        FitnessCalc.setPista(pista);
        Population myPop = new Population(50, true);
        int generationCount = 0;
        for (int generation = 0; generation < generations; generation++) {
            if(!(myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()))
                break;
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        return myPop.getFittest().getSudoku();
    }

}
