package Logica;

import java.util.ArrayList;

public class Vertice {
	
	private int id;
	private String nombre;
	private ArrayList<Integer> vecinos;
	
	public Vertice(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vecinos = new ArrayList<Integer>();
	}
	
	public String obtenerNombre() {
		return nombre;
	}
	
	public int obtenerId() {
        return id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarVecino(int vecino) {
		vecinos.add(vecino);
	}
	
	public ArrayList<Integer> obtenerVecinos() {
		return vecinos;
	}
	
	// VISUALIZACION
	public void mostrarVecinos() {
		System.out.println("Vecinos de " + id + ": ");
        for (int vecino : vecinos) {
            System.out.println(vecino);
        }
	}
	
	public boolean equals(String nombre) {
		return nombre.equals(nombre);
	}
	
}
