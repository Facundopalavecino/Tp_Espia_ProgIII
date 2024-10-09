package Logic;

public class parDeEspias implements Comparable<parDeEspias> {
    private Espia espia1;
    private Espia espia2;
    private double probabilidadIntercepcion;

    public parDeEspias(Espia espia1, Espia espia2, double probabilidadIntercepcion) {
        this.espia1 = espia1;
        this.espia2 = espia2;
        this.probabilidadIntercepcion = probabilidadIntercepcion;
    }

    public Espia getEspia1() {
        return espia1;
    }

    public Espia getEspia2() {
        return espia2;
    }

    public double getProbabilidadIntercepcion() {
        return probabilidadIntercepcion;
    }

    @Override
    public int compareTo(parDeEspias otra) {
        return Double.compare(this.probabilidadIntercepcion, otra.probabilidadIntercepcion);
    }

    @Override
    public String toString() {
        return espia1.getNombre() + " - " + espia2.getNombre() + " : " + probabilidadIntercepcion;
    }
}
