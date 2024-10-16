package Logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ArbolGMTest {

    private Grafo grafo;
    private ArbolGM arbolGM;
    private Vertice vertice1;
    private Vertice vertice2;
    private Vertice vertice3;
    private Vertice vertice4;

    @BeforeEach
    void setUp() {
        // Inicializamos los vértices y el grafo antes de cada prueba
        grafo = new Grafo("Mensaje de prueba");

        vertice1 = new Vertice(0, "Espía 1");
        vertice2 = new Vertice(1, "Espía 2");
        vertice3 = new Vertice(2, "Espía 3");
        vertice4 = new Vertice(3, "Espía 4");

        grafo.agregarVertice(vertice1.obtenerNombre());
        grafo.agregarVertice(vertice2.obtenerNombre());
        grafo.agregarVertice(vertice3.obtenerNombre());
        grafo.agregarVertice(vertice4.obtenerNombre());

        grafo.agregarArista(vertice1, vertice2, 0.3);  // peso 0.3 | 0 => 1
        grafo.agregarArista(vertice2, vertice3, 0.5);  // peso 0.5 | 1 => 2
        grafo.agregarArista(vertice3, vertice4, 0.7);  // peso 0.7 | 2 => 3

        arbolGM = new ArbolGM(grafo);
    }

    @Test
    void testCalcularKruskal() {
        arbolGM.calcularKruskal();
        ArrayList<Arista> aristasGeneradas = arbolGM.obtenerAristasGeneradas();

        // Validamos que se han generado dos aristas (ya que son 4 vértices, se generan n-1 aristas)
        assertEquals(3, aristasGeneradas.size());

        // Verificamos que las aristas seleccionadas son las correctas (las de menor peso)
        Arista arista1 = grafo.obtenerArista(0, 1);
        Arista arista2 = grafo.obtenerArista(1, 2);
        Arista arista3 = grafo.obtenerArista(2, 3);

        // Comprobamos que las aristas generadas están entre las seleccionadas
        assertTrue(arbolGM.existeArista(arista1), "La arista entre Espía 1 y Espía 2 debería estar en el árbol");
        assertTrue(arbolGM.existeArista(arista2), "La arista entre Espía 2 y Espía 3 debería estar en el árbol");
        assertTrue(arbolGM.existeArista(arista3), "La arista entre Espía 3 y Espía 4 debería estar en el árbol");

    }

    @Test
    void testCalcularPrim() {
        arbolGM.calcularPrim();
        ArrayList<Arista> aristasGeneradas = arbolGM.obtenerAristasGeneradas();

        // Validamos que se han generado tres aristas (ya que son 4 vértices)
        assertEquals(3, aristasGeneradas.size());

        // Verificamos que las aristas seleccionadas son las correctas (las de menor peso)
        Arista arista1 = grafo.obtenerArista(vertice1.obtenerId(), vertice2.obtenerId());
        Arista arista2 = grafo.obtenerArista(vertice2.obtenerId(), vertice3.obtenerId());
        Arista arista3 = grafo.obtenerArista(vertice3.obtenerId(), vertice4.obtenerId());

        
        // Verificamos que las aristas seleccionadas son las correctas (las de menor peso)
        assertTrue(arbolGM.existeArista(arista1));
        assertTrue(arbolGM.existeArista(arista2));
        assertTrue(arbolGM.existeArista(arista3));
    }

    @Test
    void testCreaCircuito() {
    	arbolGM.calcularKruskal();
    	// Llamada a creaCircuito
    	assertTrue(arbolGM.creaCircuito(vertice4.obtenerId(), vertice1.obtenerId()));

    	 // Inicializamos un nuevo grafo con vértices desconectados.
        Grafo grafoDesconectado = new Grafo("Grafo de prueba");
        
        Vertice vertice1 = new Vertice(0, "Espía 1");
        Vertice vertice2 = new Vertice(1, "Espía 2");
        Vertice vertice3 = new Vertice(2, "Espía 3");
        Vertice vertice4 = new Vertice(3, "Espía 4");

        grafoDesconectado.agregarVertice(vertice1.obtenerNombre());
        grafoDesconectado.agregarVertice(vertice2.obtenerNombre());
        grafoDesconectado.agregarVertice(vertice3.obtenerNombre());
        grafoDesconectado.agregarVertice(vertice4.obtenerNombre());

        // Agregamos algunas aristas entre los vértices.
        grafoDesconectado.agregarArista(vertice1, vertice2, 0.3); // 0 => 1
        grafoDesconectado.agregarArista(vertice3, vertice4, 0.5); // 2 => 3
        
        // Creamos el árbol de mínimo cuello de botella.
        ArbolGM arbolGMDesconectado = new ArbolGM(grafoDesconectado);
        
        // Ejecutamos el algoritmo de Kruskal para generar el árbol.
        arbolGMDesconectado.calcularKruskal();
        
        // Verificamos que no se crea un ciclo entre vértices no conectados directamente.
        // Aquí debería dar false, ya que los vértices 0 y 2 no están conectados.
        assertFalse(arbolGMDesconectado.creaCircuito(vertice1.obtenerId(), vertice3.obtenerId()), 
            "No debería haber un ciclo entre vértices no conectados.");
    }

    @Test
    void testUnion() {
        int origen = vertice1.obtenerId();
        int destino = vertice2.obtenerId();

        arbolGM.union(origen, destino);

        // Comprobamos que ambos vértices tienen la misma raíz después de la unión
        assertEquals(arbolGM.find(origen), arbolGM.find(destino));
    }

    @Test
    void testFind() {
        assertEquals(vertice1.obtenerId(), arbolGM.find(vertice1.obtenerId()));
        arbolGM.union(vertice1.obtenerId(), vertice2.obtenerId());
        assertEquals(arbolGM.find(vertice1.obtenerId()), arbolGM.find(vertice2.obtenerId()));
    }
}