package Logica;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new Grafo("Mensaje para enviar", 9);
		grafo.agregarVertice("Espia Jorge"); 	// 1
		grafo.agregarVertice("Espia Nacho"); 	// 2
		grafo.agregarVertice("Espia Damian"); 	// 3
		grafo.agregarVertice("Espia Sebastian");// 4
		grafo.agregarVertice("Espia 001");		// 5
		grafo.agregarVertice("Espia Sr. X");	// 6
		grafo.agregarVertice("Espia ElBarto");  // 7
		grafo.agregarVertice("Espia Cosme Fulanito");	// 8
		grafo.agregarVertice("Espia Milton");  // 9
		System.out.println("---------------------------------------------------------");
		grafo.agregarArista(0, 1,  10);
		
		grafo.agregarArista(1, 7,  35); 
		grafo.agregarArista(1, 8,  50);
		
		grafo.agregarArista(2, 3,  10);  
		grafo.agregarArista(2, 8,  80);
		
		grafo.agregarArista(3, 4,  15);  
		grafo.agregarArista(3, 5,  80); 	 
		grafo.agregarArista(3, 6,  5);	 
		grafo.agregarArista(3, 8,  40);	
		
		grafo.agregarArista(4, 5,  95);
		
		grafo.agregarArista(5, 6,  65);
		
		grafo.agregarArista(6, 7,  70);
		grafo.agregarArista(6, 8,  20);		
		System.out.println("---------------------------------------------------------");
		ArbolGM arbolK = new ArbolGM(grafo);
		arbolK.calcularKruskal();
		arbolK.mostrarArbolGeneradoDetallado();
		System.out.println("---------------------------------------------------------");		
		ArbolGM arbolP = new ArbolGM(grafo);
		arbolP.calcularPrim();
		arbolP.mostrarArbolGeneradoDetallado();
		System.out.println("---------------------------------------------------------");
				
	}

}
