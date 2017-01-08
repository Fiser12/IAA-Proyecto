package controller.algorithm;

import vo.Sudoku;

/**
 * Created by Fiser on 8/1/17.
 */
public class FitnessCalc {

    static Sudoku pista;
    public static void setPista(Sudoku sudoku)
    {
        pista = sudoku;
    }
    static Sudoku getPista()
    {
        return pista;
    }
    static int getFitness(Individual individual) {
         return  FitnessCalc.getMaxFitness()-individual.getSudoku().conflictCount();
    }
    static int getMaxFitness() {
        return 216;
    }
}
