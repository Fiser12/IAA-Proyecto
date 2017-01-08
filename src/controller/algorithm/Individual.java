package controller.algorithm;

import vo.Celda;
import vo.Sudoku;

import java.util.Random;

/**
 * Created by Fiser on 8/1/17.
 */
public class Individual {
    public Individual() {
            Sudoku s2 = FitnessCalc.getPista();
            if (s2.conflictCount() == 0 && !s2.containsZero()) {
                this.sudoku = s2.clone();
            } else {
                this.sudoku = this.generateIndividual(s2);
            }
    }
    public Individual(Sudoku nuevo)
    {
        this.sudoku = nuevo;
    }
    public Sudoku getSudoku() {
        return sudoku;
    }
    static Random generator = new Random();
    private Sudoku sudoku;
    private int fitness = 0;
    public Sudoku generateIndividual(Sudoku pista) {
        Sudoku newChromosome = pista.clone();
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(!newChromosome.getCelda(r, c).isInicial()) {
                    newChromosome.setCelda(new Celda(generator.nextInt(9) + 1), r, c);
                }
            }
        }
        return newChromosome;
    }

    /* Getters and setters */
    public int getGene(int index) {
        return sudoku.getCelda(index/9, index%9).getNumero();
    }
    public Individual clone() {
        return new Individual(this.getSudoku().clone());
    }

    public void setGene(int index, int value) {
        if(!sudoku.getCelda(index/9, index%9).isInicial()) {
            sudoku.setCelda(new Celda(value), index / 9, index % 9);
            fitness = 0;
        }
    }

    /* Public methods */
    public int size() {
        return 81;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
