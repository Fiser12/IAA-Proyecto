package vo;

/**
 * Created by Fiser on 25/2/15.
 */
public class Celda {
    private int numero;
    private boolean inicial;
    private boolean error;

    public Celda(int num)
    {
        numero = num;
    }
    public Celda(int num, boolean inicial)
    {
        numero = num;
        this.inicial = inicial;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String toString(){
        return ""+this.numero;
    }
    public boolean equals(int numero)
    {
        return numero == this.numero;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
