package vo;

/**
 * Created by Fiser on 25/2/15.
 */
public class Sudoku implements Cloneable{
    private Celda [][] tabla;

    private int conflictos;
    public Sudoku()
    {
        tabla = new Celda[9][9];
    }
    public Celda getCelda(int i, int j)
    {
        return tabla[i][j];
    }
    public void setCelda(Celda c, int i, int j)
    {
        tabla[i][j] = c;
    }
    public Celda[] getFila(int i)
    {
        return tabla[i];
    }
    public Celda[] getColumna(int j)
    {
        Celda[] col = new Celda[9];
        for(int i = 0; i<9; i++)
        {
            col[i] = tabla[i][j];
        }
        return col;
    }
    public Celda[] getBloque(int i, int j)
    {
        int b = 0;
        Celda[] bloque = new Celda[9];
        i = i -(i%3);
        j = j -(j%3);

        for(int n = i; n<i+3; n++)
        {
            for(int m = j; m<j+3; m++)
            {
                bloque[b] = tabla[n][m];
                b++;
            }
        }
        return bloque;
    }
    public String[][] getTabla()
    {
        int b = 0;
        String[][] tabla = new String[9][9];

        for(int i = 0; i<9; i++)
        {
            for(int j = 0; j<9; j++)
            {
                if(this.tabla[i][j].getNumero()!=0)
                    tabla[i][j] = this.tabla[i][j].toString();
            }
        }
        return tabla;
    }
    public void copy (Sudoku tabla) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tabla[i][j] = tabla.getCelda(i, j);
            }
        }
    }
    public void clear () {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tabla[i][j] = new Celda(0);
            }
        }
    }
    public boolean contains(int i, int j, int n) {
        Celda[] columna = getColumna(j);
        Celda[] fila = getFila(i);
        Celda[] bloque = getBloque(i, j);

        if(contains(columna, n) || contains(fila, n) || contains(bloque, n)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean containsZero() {
        boolean contains = false;
        for(int i = 0; i<9; i++){
            if(contains(getFila(i), 0)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public void printBoard() {
        System.out.println("-------------------");
        for (int i = 0; i < 9; i++) {
            System.out.print("|");
            for (int j = 0; j < 9; j++) {
                if (tabla[i][j] == null || tabla[i][j].equals(0)) {
                    System.out.print("*");
                } else {
                    System.out.print(tabla[i][j]);
                }
                if (j % 3 == 2) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            if (i % 3 == 2) {
                System.out.println("\n-------------------");
            } else {
                System.out.println();
            }
        }
    }


    private boolean contains (Celda[] array, int n) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(n)) {
                return true;
            }
        }
        return false;
    }
    public Sudoku clone() {
        Sudoku clonado = new Sudoku();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Celda cell = new Celda(getCelda(i,j).getNumero(), getCelda(i,j).isInicial());
                clonado.setCelda(cell, i, j);
            }
        }
        return clonado;
    }

    public int conflictCount() {
        conflictos = 0;
        for(int i = 0; i<9; i++)
        {
            conflictos = conflictos + numeroDeRepetidos(getFila(i));
            conflictos = conflictos + numeroDeRepetidos(getColumna(i));
        }
        conflictos = conflictos + numeroDeRepetidos(getBloque(0,0));
        conflictos = conflictos + numeroDeRepetidos(getBloque(0,3));
        conflictos = conflictos + numeroDeRepetidos(getBloque(0,6));
        conflictos = conflictos + numeroDeRepetidos(getBloque(3,0));
        conflictos = conflictos + numeroDeRepetidos(getBloque(3,3));
        conflictos = conflictos + numeroDeRepetidos(getBloque(3,6));
        conflictos = conflictos + numeroDeRepetidos(getBloque(6,0));
        conflictos = conflictos + numeroDeRepetidos(getBloque(6,3));
        conflictos = conflictos + numeroDeRepetidos(getBloque(6,6));
        return conflictos;
    }
    private int numeroDeRepetidos(Celda [] lista)
    {
        int duplicates = 0;
        for (int i=0;i<9;i++)
            for (int j=i+1;j<9;j++)
            if (i!=j && lista[i].getNumero() == lista[j].getNumero() && lista[j].getNumero()!=0)
                    duplicates++;
        return duplicates;

    }
    public boolean resuelto()
    {
        if(conflictCount()==0&&!containsZero())
            return true;
        else return false;
    }

}
