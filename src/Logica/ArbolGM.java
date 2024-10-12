package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ArbolGM {
	
	private Grafo grafo;
	private ArrayList<Arista> aristasGeneradas;
	private int padres[]; 
			
	public ArbolGM(Grafo grafo) {
		this.grafo = grafo;
		this.aristasGeneradas = new ArrayList<Arista>();
		this.padres = new int[grafo.totalVertices()];
	}
	
	public int find(int vertice) {
		 while(padres[vertice] != vertice) {
			 vertice = padres[vertice];
		 }
		 	return vertice;
	}
	
	public void union(int origen, int destino) {
		int raizOrigen = find(origen);
		int raizDestino = find(destino);
		
		if (raizOrigen != raizDestino) {
	        padres[raizOrigen] = raizDestino;
	    }
	}	
	
	public boolean creaCircuito(int origen, int destino) {
		return find(origen) == find(destino);
	}
	
	public void algoritmoKruskal() {		
	    for (int j = 0; j < grafo.totalVertices(); j++) {
	        padres[j] = j;
	    }
	    
	    // Obtenemos las aristas del grafo y las ordenamos por peso
	    ArrayList<Arista> listaAristas = new ArrayList<>(grafo.obtenerAristas().values());
	    Collections.sort(listaAristas, Comparator.comparingDouble(Arista::obtenerPeso));
	 
	    int aristasSeleccionadas = 0;
	    
	    for(Arista arista : listaAristas) {
	    	int origen = arista.obtenerOrigen().obtenerId();
	    	int destino = arista.obtenerDestino().obtenerId();
	    	
	    	if(!creaCircuito(origen, destino)) {
	    		union(origen, destino);
	    		aristasGeneradas.add(arista);
                aristasSeleccionadas++;
	    		System.out.println("Union del origen " + origen + " y de destino " + destino + ". Cantidad de Aristas = " + aristasSeleccionadas);
	    		
                if (aristasSeleccionadas == grafo.totalVertices() - 1) {
                    break;
                }
	    	}
	    }
	}
	
	// VISUALIZACION
	public void mostrarArbolGeneradoDetallado() {
	    System.out.println("Árbol Generador Mínimo generado por Kruskal (detallado):");
		System.out.println("---------------------------------------------------------");
	    ArrayList<String> conexiones = new ArrayList<>();

	    for (Arista arista : aristasGeneradas) {
	        double peso = arista.obtenerPeso();
	        
	        String conexion =  arista.obtenerOrigen().obtenerNombre() + " --(" + (peso * 100) + "%)--> " + arista.obtenerDestino().obtenerNombre();
	        conexiones.add(conexion);
	    }

	    for (String conexion : conexiones) {
	        System.out.println(conexion);
	    }
	    
	    System.out.println("\nEstructura de los padres de cada nodo:");
	    for (int i = 0; i < padres.length; i++) {
	        System.out.println("Nodo " + i + " -> Padre: " + padres[i]);
	    }
	}
}
