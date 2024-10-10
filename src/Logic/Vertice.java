package Logic;

import java.util.*;

public class Vertice {
	
	private String nombre;
	private ArrayList<Integer> listaVecinos;
	
	public Vertice(String nombre) {
		this.nombre = nombre;
		this.listaVecinos = new ArrayList<Integer>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
