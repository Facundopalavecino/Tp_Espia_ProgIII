package Logic;

import java.util.*;

public class Grafo {
	private HashMap<Integer, Vertice> vertices;
	private String mensaje;
	private HashMap<Integer, Arista> aristas;
	
	public Grafo(HashMap<Integer, Vertice>  vertices, String mensaje, Arista aristas) {
		this.vertices = new HashMap<Integer, Vertice>() ;
		this.aristas = new HashMap<Integer, Arista>();
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void agregarVertice(String nombre) {
		Vertice vertice = new Vertice(nombre);
		
		vertices.put(vertices.size(), vertice);
		
		System.out.println("Se agrego vertice nro. " + vertices.size() + " con nombre: " + nombre );
	}
	
	public void agregarArista(Vertice origen, Vertice destino, int peso) {
		Arista arista = new Arista(origen, destino, peso);
		
		aristas.put(vertices.size(), arista);
		
		System.out.println("Se agrego arista del origen " + origen.getNombre() + " al destino " + destino.getNombre() + " con peso: " + peso );
	}
	
	public void mostrarGrafo() {
		System.out.println("Grafo: ");
		for (int i = 0; i < vertices.size(); i++) {
			System.out.println("Vertice " + i + ": " + vertices.get(i).getNombre());
		}
	}

	/*@Override
	public int hashCode() {
		return Objects.hash(nombre,mensaje);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grafo other = (Grafo) obj;
		
		return Objects.equals(nombre, other.nombre) && Objects.equals(mensaje, other.mensaje);
	}
	*/
	
	
}
