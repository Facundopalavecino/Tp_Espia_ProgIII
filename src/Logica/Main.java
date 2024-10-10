package Logica;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new Grafo("Mensaje para enviar");
		grafo.agregarVertice("Espia Jorge"); 	// 1
		grafo.agregarVertice("Espia Nacho"); 	// 2
		grafo.agregarVertice("Espia Damian"); 	// 3
		grafo.agregarVertice("Espia Sebastian");// 4
		grafo.agregarVertice("Espia 001");		// 5
		grafo.agregarVertice("Espia Sr. X");	// 6
		grafo.agregarVertice("Espia ElBarto");  // 7
		System.out.println("-----------------------------------");
		grafo.agregarArista(1, 2,  10); // Jorge => Nacho
		grafo.agregarArista(1, 3,  50); // Jorge => Damian
		grafo.agregarArista(2, 3,  10); // Nacho => Damian
		grafo.agregarArista(3, 5,  10); // Damian => 001
		grafo.agregarArista(2, 5,  70); // Nacho => 001
		grafo.agregarArista(2, 6,  25); // Nacho => Sr. X
		grafo.agregarArista(6, 5,  5); 	// Sr. X => 001
		grafo.agregarArista(6, 7,  10);	// Sr. X => ElBarto
		grafo.agregarArista(5, 7,  35);	// 001 => ElBarto
		System.out.println("-----------------------------------");
		ArbolGM arbolK = new ArbolGM(grafo);
		arbolK.algoritmoKruskal();
		System.out.println("-------------DETALLADO-------------");
		arbolK.mostrarArbolGeneradoDetallado();
		System.out.println("-----------------------------------");
				
	}

}
