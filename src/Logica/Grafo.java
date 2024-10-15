package Logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Grafo {
	private HashSet<Vertice> vertices;
	private HashSet<Arista> aristas;
	private String mensaje;
	private int idUltimoVertice = 0;
	private int idUltimaArista = 0;

	
	public Grafo(String mensaje) {
		this.vertices = new HashSet<Vertice>() ;
		this.aristas = new HashSet<Arista>();
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public int cantidadVertices() {
		return vertices.size();
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
			if(nombreRepetido(nombre)) {
				throw new IllegalArgumentException("Ya existe un vertice con el mismo nombre.");
			} else {
				Vertice vertice = new Vertice(idUltimoVertice, nombre);
				vertices.add(vertice);
				idUltimoVertice++;			
			}

	}
	
	public void eliminarVertice(int id) {
		for(Vertice vertice : vertices) {
			if(vertice.obtenerId() == id) {
				ArrayList<Integer> vecinos = vertice.obtenerVecinos();
				Arista aristaAEliminar = new Arista();
				
				for (int vecino : vecinos) {
					
					aristaAEliminar = obtenerArista(id, vecino);
					if (aristaAEliminar != null && aristaAEliminar.existeConexion(id, vecino)) {
						aristas.remove(aristaAEliminar);
					}
					
					aristaAEliminar = obtenerArista(vecino, id);
					if (aristaAEliminar != null && aristaAEliminar.existeConexion(vecino, id)) {
						aristas.remove(aristaAEliminar);
					}
				}
				vertices.remove(vertice);
				idUltimoVertice--;
				break;
			}
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
	
	public void agregarArista(Vertice origen, Vertice destino, double peso) {
		try {			
			// Verificamos que ambos vértices existen
		    if ((origen == null || destino == null)) {
		        return;
		    }
		    
		    Arista arista1 = new Arista(idUltimaArista, origen, destino, peso);
		    idUltimaArista++;
		    Arista arista2 = new Arista(idUltimaArista, destino, origen, peso); 
		    idUltimaArista ++;
	
		    aristas.add(arista1);  // origen -> destino
		    aristas.add(arista2);  // destino -> origen
		 } catch (NumberFormatException e) {
            throw new NumberFormatException("Error al crear la arista: " + e.getMessage());
        }
	    		
	}
	
	public void agregarArista(int idOrigen, int idDestino, double peso) {
		try {
			Vertice origen = obtenerVertice(idOrigen);
			Vertice destino = obtenerVertice(idDestino);
			
			// Verificamos que ambos vértices existen
		    if ((origen == null || destino == null)) {
		        return;
		    }
		    
		    Arista arista1 = new Arista(idUltimaArista, origen, destino, peso);
		    idUltimaArista++;
		    Arista arista2 = new Arista(idUltimaArista, destino, origen, peso); 
		    idUltimaArista ++;
	
		    aristas.add(arista1);  // origen -> destino
		    aristas.add(arista2);  // destino -> origen
		 } catch (NumberFormatException e) {
            throw new NumberFormatException("Error al crear la arista: " + e.getMessage());
        }	
	}
	
	public void eliminarArista(Vertice origen, Vertice destino) {	
		int idOrigen = origen.obtenerId();
		int idDestino = destino.obtenerId();
		
		for(Arista arista : aristas) {
			if(arista.existeConexion(idOrigen, idDestino)) {
				aristas.remove(arista);
				idUltimaArista--;
				break;
			}			
		}
	}
	
	// VERIFICACIONES
	public boolean verificarAristas(int origen, int destino) {
		for(Arista arista : aristas) {
			if(arista.existeConexion(origen, destino)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean nombreRepetido(String nombre) {
		for(Vertice vertice : vertices) {
			String nombre1 = vertice.obtenerNombre().toLowerCase();
			if(nombre1.equals(nombre.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> nombres(){
		ArrayList<String> resultado = new ArrayList<String>();
		for (int i = 0; i < totalVertices(); i++) {
            resultado.add(obtenerVertice(i).obtenerNombre());
        }
		return resultado;
	}
		
	// TOTALES
	public int totalVertices() {
		return vertices.size();
	}
	
	// BORRAR TODO
	public void borrar() {
        // Limpiar los vértices y aristas
        this.vertices.clear();
        this.aristas.clear();
    }
	
}
