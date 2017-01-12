package controller.algorithm;


import vo.Sudoku;

public class Solver {
    public final Float generations = 1000f;
    public static final double mutationRate = 0.4;
    public static final int tournamentSize = 10;
    public static final int populationSize = 50;
    public static final int elitism = 5;

    Sudoku pista = new Sudoku();

    public Solver() {
    }

    public void setPista(Sudoku pista) {
        this.pista = pista;
    }

    public Sudoku solve() {
        FitnessCalc.setPista(pista);
        Population myPop = new Population(populationSize, true);
        //while (true) {
        for (int generation = 0; generation < generations; generation++) {
            if((myPop.getFittest().getFitness() == FitnessCalc.getMaxFitness()))
                break;
            System.out.println("Generation: " + (generation+1) + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        if(myPop.getFittest().getFitness()==FitnessCalc.getMaxFitness())
            System.out.println("Solution found! " +myPop.getFittest().getFitness() );
        else
            System.out.println("Solution not found! " +myPop.getFittest().getFitness() );

        return myPop.getFittest().getSudoku();
    }
}
