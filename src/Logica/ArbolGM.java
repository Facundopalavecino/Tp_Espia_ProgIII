package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class ArbolGM {
	
	private Grafo grafo;
	private ArrayList<Arista> aristasGeneradas;
	private int padres[]; 
			
	public ArbolGM(Grafo grafo) {
		this.grafo = grafo;
		this.aristasGeneradas = new ArrayList<Arista>();
		this.padres = new int[grafo.totalVertices()];
	}
	
	// --------------------------------   KRUSKAL ---------------------------------------------
	public void calcularKruskal() {		
	    for (int j = 0; j < grafo.totalVertices(); j++) {
	        padres[j] = j;
	    }
	    
	    // Obtenemos las aristas del grafo y las ordenamos por peso
	    ArrayList<Arista> listaAristas = new ArrayList<>(grafo.obtenerAristas());
	    Collections.sort(listaAristas, Comparator.comparingDouble(Arista::obtenerPeso));
	 
	    int aristasSeleccionadas = 0;
	    
	    for(Arista arista : listaAristas) {
	    	int origen = arista.obtenerOrigen().obtenerId();
	    	int destino = arista.obtenerDestino().obtenerId();
	    	
	    	if(!creaCircuito(origen, destino)) {
	    		union(origen, destino);
	    		aristasGeneradas.add(arista);
                aristasSeleccionadas++;
	    		
                if (aristasSeleccionadas == grafo.totalVertices() - 1) {
                    break;
                }
	    	}
	    }
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
	
	
	// ---------------------------------------- PRIM ------------------------------------
	public void calcularPrim() {
	    Set<Vertice> visitados = new HashSet<>();
	    PriorityQueue<Arista> aristas = new PriorityQueue<>();

	    // Seleccionar un vértice inicial arbitrario
	    Vertice espiaInicial = grafo.obtenerVertices().iterator().next();
	    visitados.add(espiaInicial);
	    agregarAristasNoVisitadas(espiaInicial, aristas, visitados);

	    while (!aristas.isEmpty() && visitados.size() < grafo.totalVertices()) {
	        Arista aristaMin = aristas.poll();
	        Vertice origen = aristaMin.obtenerOrigen();
	        Vertice destino = aristaMin.obtenerDestino();

	        // Solo se agrega la arista si conecta un vértice visitado con uno no visitado
	        if (visitados.contains(origen) && !visitados.contains(destino)) {
	            aristasGeneradas.add(aristaMin);
	            visitados.add(destino);
	            agregarAristasNoVisitadas(destino, aristas, visitados);
	        } else if (visitados.contains(destino) && !visitados.contains(origen)) {
	            aristasGeneradas.add(aristaMin);
	            visitados.add(origen);
	            agregarAristasNoVisitadas(origen, aristas, visitados);
	        }
	    }
	}

	private void agregarAristasNoVisitadas(Vertice vertice, PriorityQueue<Arista> aristas, Set<Vertice> visitados) {
	    for (Arista arista : grafo.obtenerAristas()) {
	        Vertice vecino = arista.obtenerOrigen().equals(vertice) ? arista.obtenerDestino() : arista.obtenerOrigen();
	        // Solo agrega aristas que conecten a un vecino no visitado
	        if (!visitados.contains(vecino)) {
	            aristas.add(arista);
	        }
	    }
	}
	
	public ArrayList<Arista> obtenerAristasGeneradas() {
	    return aristasGeneradas;
	}
}
