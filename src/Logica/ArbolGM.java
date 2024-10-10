package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ArbolGM {
	
	private Grafo grafo;
	private HashMap<Integer, Arista> aristasGeneradas;
	private int padres[]; 
			
	public ArbolGM(Grafo grafo) {
		this.grafo = grafo;
		this.aristasGeneradas = new HashMap<Integer, Arista>();
		this.padres = new int[grafo.totalVertices()];
	}
	
	// ALGORITMOS
	
	public int find(int vertice) {
		if(padres[vertice] == vertice) {
			return vertice;
		}
		return find(padres[vertice]);
	}
	
	public void union(int origen, int destino) {
		int raizOrigen = find(origen);
		int raizDestino = find(destino);
		padres[raizOrigen] = raizDestino;
	}
	
	public void algoritmoKruskal() {
		int i = 1;
		HashMap<Integer, Arista> aristasDelGrafo = grafo.obtenerAristas();
		
		// Inicializamos el array de padres
	    for (int j = 0; j < grafo.totalVertices(); j++) {
	        padres[j] = j;
	    }
	    
	 // Obtenemos las aristas del grafo y las ordenamos por peso
	    ArrayList<Arista> listaAristas = new ArrayList<>(grafo.obtenerAristas().values());
	    Collections.sort(listaAristas, Comparator.comparingDouble(Arista::obtenerPeso));
	 
	    Arista arista = null; // Inicializo la variable de arista
	    
	    while(i < grafo.totalVertices()) {
	    	arista = aristasDelGrafo.get(i);
	    	int origen = arista.obtenerOrigen().obtenerId();
	    	int destino = arista.obtenerDestino().obtenerId();
	    	
	    	if(!creaCircuito(origen, destino)) {
	    		union(origen, destino);
	    		System.out.println("Union del origen " + origen + " y de destino " + destino);
	    	}
	    	i++;
	    }
	}
	
	public boolean creaCircuito(int origen, int destino) {
		return find(origen) == find(destino);
	}
	
	/*public void generarPorKruskal() {
		padres = new int[grafo.totalVertices()];
		
		// Inicializamos el array de padres
	    for (int j = 0; j < grafo.totalVertices(); j++) {
	        padres[j] = j;
	    }
	    
        // Convertimos el HashMap a un ArrayList para poder ordenarlas
        ArrayList<Arista> listaAristas = new ArrayList<>(grafo.obtenerAristas().values());
        Collections.sort(listaAristas, Comparator.comparingDouble(Arista::obtenerPeso));

		
		for(Arista arista : listaAristas) {
			int origen = arista.obtenerOrigen().obtenerId();
			int destino = arista.obtenerDestino().obtenerId();
			System.out.println("Origen: " + origen + " - Destino: " + destino);
			
			if(!buscarConexion(origen, destino) && aristasGeneradas.size() <= grafo.totalVertices()-1) {
			    aristasGeneradas.put(origen + "-" + destino, arista);
				union(origen, destino);
				
				// Si ya hemos añadido V-1 aristas, terminamos (V es el número de vértices)
                //if (aristasGeneradas.size() == grafo.totalVertices() - 1) {
                //    break;
                //}
			}
		}


	}
	
	
	// VALIDACIONES
	public int raiz(int i) {
		i--;
		if(padres[i] != i) {
			i = padres[i];
		}	
		return i;
	}
	
	// Verifica si dos vértices ya están conectados
	public boolean buscarConexion(int origen, int destino) {
	    int raizOrigen = raiz(origen);
	    int raizDestino = raiz(destino);

	    return raizOrigen == raizDestino;
	}
	
	public void union(int origen, int destino) {
		int raizOrigen = raiz(origen);
		int raizDestino = raiz(destino);
		
		if (raizOrigen != raizDestino) {
			padres[raizOrigen] = raizDestino;
		}
	}*/
	
	// VISUALIZACION
	public void mostrarArbolGeneradoDetallado() {
	    System.out.println("Árbol Generador Mínimo generado por Kruskal (detallado):");
	    
	    // Creamos una lista para guardar las conexiones y visualizarlas después
	    ArrayList<String> conexiones = new ArrayList<>();

	    for (Arista arista : aristasGeneradas.values()) {
	        int origen = arista.obtenerOrigen().obtenerId();
	        int destino = arista.obtenerDestino().obtenerId();
	        double peso = arista.obtenerPeso();

	        // Guardamos la conexión en formato "Origen -> Destino [Peso]"
	        String conexion = "Nodo " + origen + " --(" + peso + ")--> Nodo " + destino;
	        conexiones.add(conexion);
	    }

	    // Mostrar las conexiones de forma detallada
	    for (String conexion : conexiones) {
	        System.out.println(conexion);
	    }

	    // Detallamos también la estructura de los padres (como la imagen que compartiste)
	    System.out.println("\nEstructura de los padres de cada nodo:");
	    for (int i = 0; i < padres.length; i++) {
	        System.out.println("Nodo " + i + " -> Padre: " + padres[i]);
	    }
	}


	
	
}
