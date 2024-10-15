package GUI;

public class Linea{
    private int x1, y1, x2, y2;
    private double peso;

    public Linea(int x1, int y1, int x2, int y2, double peso) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.peso = peso;
    }
    
    public int obtenerX1() {
    	return x1;
    }
    
    public int obtenerX2() {
    	return x2;
    }
    
    public int obtenerY1() {
    	return y1;
    }
    
    public int obtenerY2() {
    	return y2;
    }
    
    public double obtenerPeso() {
    	return peso;
    }
}
