package Logic;

import java.util.HashMap;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Grafo grafo = new Grafo(new HashMap<Integer, Vertice>(), "Grafo de prueba",
			new Arista(new Vertice("A"), new Vertice("B"), 10));
			grafo.agregarVertice("A");
			grafo.agregarVertice("B");
			grafo.agregarArista(new Vertice("A"), new Vertice("B"), 10);
			grafo.mostrarGrafo();
	}

}
