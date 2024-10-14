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
	    		System.out.println("Union del origen " + origen + " y de destino " + destino + ". Cantidad de Aristas = " + aristasSeleccionadas);
	    		
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

        HashSet<Vertice> vertices = grafo.obtenerVertices();
        
        // Seleccionar un vértice inicial arbitrario
        Vertice espiaInicial = vertices.iterator().next();
        visitados.add(espiaInicial);
        agregarAristasNoVisitadas(espiaInicial, aristas, visitados);

        // Ejecutar el algoritmo de Prim
        while (!aristas.isEmpty() && visitados.size() < vertices.size()) {
        	Arista aristaMin = aristas.poll();
        	Vertice origen = aristaMin.obtenerOrigen();
        	Vertice destino = aristaMin.obtenerDestino();

            // Determinar cuál de los espías de la arista no ha sido visitado
            if (!verticeYaVisitado(origen, visitados) || !verticeYaVisitado(destino, visitados)) {
	    		aristasGeneradas.add(aristaMin);
                Vertice verticeNoVisitado = verticeYaVisitado(origen, visitados) ? destino : origen;
                visitados.add(verticeNoVisitado);
                agregarAristasNoVisitadas(verticeNoVisitado, aristas, visitados);
            }
        }
	 }

	    // Método para verificar si un espía ha sido visitado
	    private boolean verticeYaVisitado(Vertice vertice, Set<Vertice> visitados) {
	        return visitados.contains(vertice);
	    }

	    // Método para agregar las aristas de un espía no visitado a la cola de prioridad
	    private void agregarAristasNoVisitadas(Vertice vertice, PriorityQueue<Arista> aristas, Set<Vertice> visitados) {
	        for (Arista arista : grafo.obtenerAristas()) {
	        	Vertice vecino = arista.obtenerOrigen().equals(vertice) ? arista.obtenerDestino() : arista.obtenerOrigen();
	            if (!visitados.contains(vecino)) {
	                aristas.add(arista);
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
