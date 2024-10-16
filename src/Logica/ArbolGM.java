package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ArbolGM {
	
	private Grafo grafo;
	private ArrayList<Arista> aristasGeneradas;
	private int padres[]; 
	private double tiempoEjecutado;
			
	public ArbolGM(Grafo grafo) {
		this.grafo = grafo;
		this.aristasGeneradas = new ArrayList<Arista>();
		this.padres = new int[grafo.totalVertices()];
		this.tiempoEjecutado = 0;
	}
	
	// --------------------------------   KRUSKAL ---------------------------------------------
	public void calcularKruskal() {		
		long inicio = System.nanoTime();
		
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

	    long fin = System.nanoTime();
	    long tiempoEjecucion = fin - inicio;
	    tiempoEjecutado = tiempoEjecucion / 100_000.0;

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
		return find(origen) == find(destino) && aristasGeneradas.size() > 0;
	}

	// ---------------------------------------- PRIM ------------------------------------
	public void calcularPrim() {
		long inicio = System.nanoTime();
		
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
		    if (fueVisitado(visitados, origen) && !fueVisitado(visitados, destino)) {
		        aristasGeneradas.add(aristaMin);
		        visitados.add(destino);
		        agregarAristasNoVisitadas(destino, aristas, visitados);
		    } else if (fueVisitado(visitados, destino) && !fueVisitado(visitados, origen)) {
		        aristasGeneradas.add(aristaMin);
		        visitados.add(origen);
		        agregarAristasNoVisitadas(origen, aristas, visitados);
		    }
		}
	    
	    long fin = System.nanoTime();
	    long tiempoEjecucion = fin - inicio;
	    tiempoEjecutado = tiempoEjecucion / 100_000.0;

	 }

	public boolean fueVisitado(Set<Vertice> visitados, Vertice verticeAAnalizar) {
		for(Vertice vertice : visitados) {
			if (vertice.equals(verticeAAnalizar.obtenerNombre())){
				return true;
			}
		}
		return false;
	}
	
	// --------------------------- BFS -----------------------------------------------------------
    // Método para recorrer el AGM con BFS
    public List<Vertice> recorrerAGMConBFS(Vertice verticeInicial) {
        List<Vertice> ordenEncuentros = new ArrayList<>();
        Queue<Vertice> cola = new LinkedList<>();
        Set<Vertice> visitados = new HashSet<>();

        // Iniciar BFS desde el vértice inicial
        cola.add(verticeInicial);
        visitados.add(verticeInicial);

        while (!cola.isEmpty()) {
            Vertice vertice = cola.poll();
            ordenEncuentros.add(vertice);

            // Obtener las aristas conectadas al vértice actual en el AGM
            for (Arista arista : aristasGeneradas) {
                Vertice origen = arista.obtenerOrigen();
                Vertice destino = arista.obtenerDestino();

                Vertice vecino = origen.equals(vertice) ? destino : origen;
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return ordenEncuentros;
    }
    // -----------------------------------------------------------------------------------------

    // Método para agregar las aristas de un espía no visitado a la cola de prioridad
    private void agregarAristasNoVisitadas(Vertice vertice, PriorityQueue<Arista> aristas, Set<Vertice> visitados) {
	    for (Arista arista : grafo.obtenerAristas()) {
	        Vertice vecino = arista.obtenerOrigen().equals(vertice) ? arista.obtenerDestino() : arista.obtenerOrigen();
	        // Solo agrega aristas que conecten a un vecino no visitado
	        if (!visitados.contains(vecino)) {
	            aristas.add(arista);
	        }
	    }
	}
    
    public boolean existeArista(Arista aristaAVerificar) {
    	int idOrigen = aristaAVerificar.obtenerOrigen().obtenerId();
    	int idDestino = aristaAVerificar.obtenerDestino().obtenerId();
    	
	    for (Arista arista : aristasGeneradas) {
	    	if(arista.existeConexion(idOrigen, idDestino) || arista.existeConexion(idDestino, idOrigen)) {
	    		return true;
	    	}
	    }
	    return false;

    }
	
	public ArrayList<Arista> obtenerAristasGeneradas() {
	    return aristasGeneradas;
	}

	public double obtenerTiempoEjecutado() {
		return tiempoEjecutado;
	}

}
