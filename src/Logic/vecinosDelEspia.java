package Logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class vecinosDelEspia {
	
	protected ArrayList<HashSet<Espia>> vecinos;
	protected List<Espia> vertices;
	
	public vecinosDelEspia (int numVertice, List<Espia> espiaSeleccionado) {
		if(espiaSeleccionado == null) {
			throw new IllegalArgumentException("No hay una lista de espias creada");
		}
		if(espiaSeleccionado.size() != numVertice) {
			throw new IllegalArgumentException("No hay las suficientes espias para cubrir cada vertice del grafo");
		}
		
		vecinos = new ArrayList <HashSet<Espia>>();
		for(int i = 0; i < numVertice; i++) {
			vecinos.add(new HashSet<Espia>());
		}
		vertices = espiaSeleccionado;
	}
	
	public void agregarArista (Espia e1, Espia e2) {
		verificarVertice (e1);
		verificarVertice (e2);
		verificarMismoVertice(e1,e2);
		
		if(!existeArista(e1,e2)) {
			vecinos.get(vertices.indexOf(e1)).add(e2);
			vecinos.get(vertices.indexOf(e2)).add(e1);			
		}		
	}
	
	public void eliminarArista(Espia e1, Espia e2) {
		verificarVertice (e1);
		verificarVertice (e2);
		verificarMismoVertice(e1,e2);
		
		vecinos.get(vertices.indexOf(e1)).remove(e2);
		vecinos.get(vertices.indexOf(e2)).remove(e1);
	}

	public boolean existeArista(Espia e1, Espia e2) {
		verificarVertice (e1);
		verificarVertice (e2);
		verificarMismoVertice(e1,e2);
		return vecinos.get(vertices.indexOf(e1)).contains(e2) && vecinos.get(vertices.indexOf(e2)).contains(e1);
	}

	public void verificarMismoVertice(Espia e1, Espia e2) {
		if(e1.equals(e2)) {
			throw new IllegalArgumentException("Una arista no se conecta consigo mismo: (" + e1 + ", " + e2 + ")"
				+ " No se permiten bucles.");
		}
	}

	public void verificarVertice(Espia e) {
		if(e == null) {
			throw new IllegalArgumentException("El vertice no puede ser null: " + e);
		}
	}
	
}
