package Logica;

import java.util.ArrayList;
import java.util.HashSet;

public class Grafo {
	private HashSet<Vertice> vertices;
	private HashSet<Arista> aristas;
	private String mensaje;
	private int idUltimoVertice = 0;
	private int idUltimaArista = 0;

	
	public Grafo(String mensaje, int cantidadVertices) {
		this.vertices = new HashSet<Vertice>(cantidadVertices) ;
		this.aristas = new HashSet<Arista>();
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	
	// VERTICES
	public Vertice obtenerVertice(int id) {
		for(Vertice vertice : vertices) {
			if(vertice.obtenerId() == id) {
				return vertice;
			}
		}
		return null;
}
	
	public void agregarVertice(String nombre) {
		Vertice vertice = new Vertice(idUltimoVertice, nombre);
		vertices.add(vertice);
		idUltimoVertice++;
		
		System.out.println("Se agrego vertice nro. " + vertice.obtenerId() + " con nombre: " + nombre );
	}
	
	public void eliminarVertice(int id) {
		if(vertices.contains(id)) {
			ArrayList<Integer> vecinos = obtenerVertice(id).obtenerVecinos();
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
	
	public HashSet<Vertice> obtenerVertices() {
		return vertices;
	}
	
	// ARISTAS
	public Arista obtenerArista(int origen, int destino) {		
		for(Arista arista : aristas) {
			if(arista.existeConexion(origen, destino)) {
				return arista;
			}
		}
		return null;
	}
	
	public HashSet<Arista> obtenerAristas() {
		return aristas;
	}
	
	public void agregarArista(int idOrigen, int idDestino, double peso) {
		Vertice origen = obtenerVertice(idOrigen);
		Vertice destino = obtenerVertice(idDestino);
		
		// Verificamos que ambos vértices existen
	    if (origen == null || destino == null) {
	        System.out.println("Error: Uno de los vértices no existe. Origen: " + idOrigen + ", Destino: " + idDestino);
	        return;
	    }
		
	    Arista arista1 = new Arista(idUltimaArista, origen, destino, peso);
	    idUltimaArista++;
	    Arista arista2 = new Arista(idUltimaArista, destino, origen, peso); 
	    idUltimaArista ++;

	    aristas.add(arista1);  // origen -> destino
	    aristas.add(arista2);  // destino -> origen
	    
	    System.out.println("Se agrego arista (" + origen.obtenerId() + " => " + destino.obtenerId() + ") con peso: " + peso);
	    System.out.println("Se agrego arista (" + destino.obtenerId() + " => " + origen.obtenerId() + ") con peso: " + peso);
		
	}
	
	public void eliminarArista(int idArista) {				
		if(aristas.contains(idArista)) {
            aristas.remove(idArista);
            System.out.println("Se elimino arista id: " + idArista);
        }
	}
	
	// VISUALIZACION
	public void mostrarGrafo() {
		System.out.println("Grafo: ");
		for (int i = 1; i < idUltimoVertice; i++) {
			if (vertices.contains(i)) {
				System.out.println("Vertice " + obtenerVertice(i).obtenerId() + ": " + obtenerVertice(i).obtenerNombre());
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
