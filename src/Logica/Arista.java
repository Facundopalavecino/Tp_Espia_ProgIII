package Logica;

public class Arista implements Comparable<Arista>{
	private int id;
	private String conexion;
	private Vertice origen;
	private Vertice destino;
	private double peso;

	public Arista(int id, Vertice origen, Vertice destino, double peso) {
		this.id = id;
		this.conexion = origen.obtenerId() + "-" + destino.obtenerId();
		this.origen = origen;
		this.destino = destino;
		if(peso >= 0 && peso <= 100) {
			this.peso = peso / 100.0;			
		}else {
			throw new IllegalArgumentException("El peso debe estar entre 0 y 100");
		}
		
		origen.agregarVecino(destino.obtenerId());
		destino.agregarVecino(origen.obtenerId());
	}
	
	public Arista(){
		this.origen = null;
		this.destino = null;
		this.peso = 0;
	}
	
	public double obtenerPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public Vertice obtenerOrigen() {
		return origen;
	}
	
	public Vertice obtenerDestino() {
		return destino;
	}
	
	public boolean existeConexion(int origen, int destino) {
		return conexion.equals(origen + "-" + destino);	
	}

	public String obtenerConexion() {
		return conexion;
	}
	
    // Implementación de la interfaz Comparable
    @Override
    public int compareTo(Arista otraArista) {
        return Double.compare(this.peso, otraArista.peso);
    }
}
