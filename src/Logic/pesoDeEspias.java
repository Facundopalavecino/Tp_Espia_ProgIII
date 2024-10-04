package Logic;

import java.util.ArrayList;
import java.util.List;

public class pesoDeEspias extends vecinosDelEspia{

	ArrayList<parDeEspias> aristaDeEspias;

	public pesoDeEspias(int numVertice, List<Espia> espiaSeleccionado) {
		super(numVertice, espiaSeleccionado);
		aristaDeEspias = new ArrayList<parDeEspias>();
	}

	/* Es para añadir la arista en la clase principal */
	//@Override
	public void agregarArista (Espia e1, Espia e2, double peso) {
		super.agregarArista(e1, e2);
		
		parDeEspias nuevaArista = new parDeEspias(e1,e2,peso);
		if(!aristaDeEspias.contains(nuevaArista)) {
			aristaDeEspias.add(nuevaArista);
		}
		
	}
	
	//@Override
	public void elimiarArista(Espia e1, Espia e2) {
		super.eliminarArista(e1, e2);
		
		for(int i=0; i < aristaDeEspias.size(); i++) {
			if( aristaDeEspias.get(i).getEspia1() == e1
					&& aristaDeEspias.get(i).getEspia2() == e2 ) {
				aristaDeEspias.remove(i);
			}
		}
		
	}
	
	public double obtenerPesoDeLaArista(Espia e1, Espia e2) {
		for (int i=0; i < aristaDeEspias.size(); i++) {
			if( (aristaDeEspias.get(i).getEspia1() == e1 && aristaDeEspias.get(i).getEspia2() == e2) || 
					(aristaDeEspias.get(i).getEspia2() == e1 && aristaDeEspias.get(i).getEspia1() == e1) ) {
					return aristaDeEspias.get(i).getProbabilidad();
			}
		}
		throw new RuntimeException("No se encontro un peso entre " + e1.getNombre() + " y " + e2.getNombre());
	}
	
	/*  se utiliza para generar una representación en formato de 
	cadena (string) de las aristas (edges) que forman parte del grafo ponderado.  
	ejemplo:
		espia1 - espia2 (weight: 3.5)
		espia2 - espia3 (weight: 2.1)
		espia1 - espia3 (weight: 5.0)
	*/
	
	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < aristaDeEspias.size(); i++) {
			st.append(aristaDeEspias.get(i).toString() + "\n");
		}
		return st.toString();
	}
	
	public ArrayList<parDeEspias> getArista() {
		return aristaDeEspias;
	}

}
