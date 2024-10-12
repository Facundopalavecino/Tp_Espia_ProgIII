package Logica;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {
	private HashMap<Integer, Vertice> vertices;
	private HashMap<Integer, Arista> aristas;
	private String mensaje;
	private int idUltimoVertice = 0;
	private int idUltimaArista = 0;

	
	public Grafo(String mensaje) {
		this.vertices = new HashMap<Integer, Vertice>() ;
		this.aristas = new HashMap<Integer, Arista>();
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	
	// VERTICES
	public Vertice obtenerVertice(int id) {
		return vertices.get(id);
	}
	
	public void agregarVertice(String nombre) {
		Vertice vertice = new Vertice(idUltimoVertice, nombre);
		vertices.put(idUltimoVertice, vertice);
		idUltimoVertice++;
		
		System.out.println("Se agrego vertice nro. " + vertice.obtenerId() + " con nombre: " + nombre );
	}
	
	public void eliminarVertice(int id) {
		if(vertices.containsKey(id)) {
			ArrayList<Integer> vecinos = vertices.get(id).obtenerVecinos();
			Arista aristaAEliminar = new Arista();
			
	        for (int vecino : vecinos) {
	        	
	        	aristaAEliminar = obtenerArista(id, vecino);
				if (aristaAEliminar.existeConexion(id, vecino)) {
					
					aristas.remove(aristaAEliminar);
					System.out.println("Se elimino arista (" + id + " => " + vecino + ")");
				}
				
				aristaAEliminar = obtenerArista(vecino, id);
				if (aristaAEliminar.existeConexion(vecino, id)) {
					
					aristas.remove(vecino + "-" + id);
					System.out.println("Se elimino arista (" + vecino + " => " + id + ")");
				}
	        }
			vertices.remove(id);
			
			System.out.println("Se elimino vertice nro. " + id);
		}
	}
	
	
	// ARISTAS
	public Arista obtenerArista(int origen, int destino) {		
		for(Arista arista : aristas.values()) {
			if(arista.existeConexion(origen, destino)) {
				return arista;
			}
		}
		return null;
	}
	
	public HashMap<Integer, Arista> obtenerAristas() {
		return aristas;
	}
	
	public void agregarArista(int idOrigen, int idDestino, double peso) {
		Vertice origen = obtenerVertice(idOrigen);
		Vertice destino = obtenerVertice(idDestino);
		
		// Verificamos que ambos vértices existen
	    if (origen == null || destino == null) {
	        System.out.println("Error: Uno de los vértices no existe. Origen: " + idOrigen + ", Destino: " + idDestino);
	        return; // No agregamos la arista si los vértices no existen
	    }
		
	    // Si ambos existen, agregamos la arista en ambas direcciones
	    Arista arista1 = new Arista(origen, destino, peso);
	    Arista arista2 = new Arista(destino, origen, peso);  // Segunda arista en sentido inverso

	    aristas.put(idUltimaArista++, arista1);  // Agregamos la arista origen -> destino
	    aristas.put(idUltimaArista++, arista2);  // Agregamos la arista destino -> origen

	    System.out.println("Se agrego arista (" + origen.obtenerId() + " => " + destino.obtenerId() + ") con peso: " + peso);
	    System.out.println("Se agrego arista (" + destino.obtenerId() + " => " + origen.obtenerId() + ") con peso: " + peso);
		
	}
	
	public void eliminarArista(int idArista) {				
		if(aristas.containsKey(idArista)) {
            aristas.remove(idArista);
            System.out.println("Se elimino arista id: " + idArista);
        }
	}
	
	// VISUALIZACION
	public void mostrarGrafo() {
		System.out.println("Grafo: ");
		for (int i = 1; i < idUltimoVertice; i++) {
			if (vertices.containsKey(i)) {
				System.out.println("Vertice " + vertices.get(i).obtenerId() + ": " + vertices.get(i).obtenerNombre());
			}
		}
	}
	
	public void mostrarArista(int idOrigen, int idDestino) {
		try {
			Arista arista = obtenerArista(idOrigen, idDestino);
			System.out.println("Arista (" + idOrigen + " => " + idDestino + ") con peso: " + arista.obtenerPeso());			
		} catch (Exception e) {
			System.out.println(e.getMessage() + " (" + idOrigen + "," + idDestino + ")");
		}
	}
	
	// TOTALES
	public int totalVertices() {
		return vertices.size();
	}
	
}
