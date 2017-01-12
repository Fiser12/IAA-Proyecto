package controller.algorithm;

import vo.Celda;
import vo.Sudoku;

import java.util.*;

import static controller.algorithm.Solver.mutationRate;

public class Individual {
    public Individual() {
            Sudoku s2 = FitnessCalc.getPista();
            if (s2.conflictCount() == 0 && !s2.containsZero()) {
                this.sudoku = s2.clone();
            } else {
                this.sudoku = this.generateIndividual(s2);
            }
        getFitness();
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

    /**
     * Generamos individuos en cuyas filas no haya conflicto
     * @param pista
     * @return
     */
    public Sudoku generateIndividual(Sudoku pista) {
        Sudoku newChromosome = pista.clone();
        for(int r = 0; r < 9; r++) {
            LinkedList<Integer> exclusion = new LinkedList<>();
            for(int c = 0; c < 9; c++)
            {
                if(newChromosome.getCelda(r, c).isInicial()) {
                    exclusion.add(new Integer(newChromosome.getCelda(r, c).getNumero()));
                }
            }
            for(int c = 0; c < 9; c++) {
                if (!newChromosome.getCelda(r, c).isInicial()) {
                    int i;
                    do {
                        i = generator.nextInt(9) + 1;
                    } while (exclusion.contains(new Integer(i)));
                    exclusion.add(new Integer(i));
                    newChromosome.setCelda(new Celda(i), r, c);
                }
            }
            exclusion.clear();
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
            int numeroViejo = sudoku.getCelda(index/9, index%9).getNumero();
            Celda[] fila = sudoku.getFila(index/9);
            boolean noValido = false;
            for(int i = 0; i<9; i++){
                if(fila[i].getNumero()==value){
                    if(!fila[i].isInicial())
                        sudoku.setCelda(new Celda(numeroViejo), index / 9, i);
                    else
                        noValido = true;
                }
            }
            if(!noValido) {
                sudoku.setCelda(new Celda(value), index / 9, index % 9);
                fitness = 0;
            }
        }
    }

    /* Public methods */
    public int size() {
        return 81;
    }
    public void mutate(){
        for (int i = 0; i < this.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                int gene = generator.nextInt(9) + 1;
                this.setGene(i, gene);
            }
        }
    }
    public int getFitness() {
        fitness = FitnessCalc.getFitness(this);
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
