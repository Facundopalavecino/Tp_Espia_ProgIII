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

    @BeforeEach
    void setUp() {
        // Inicializamos los vértices y el grafo antes de cada prueba
        grafo = new Grafo("Mensaje de prueba");

        vertice1 = new Vertice(0, "Espía 1");
        vertice2 = new Vertice(1, "Espía 2");
        vertice3 = new Vertice(2, "Espía 3");

        grafo.agregarVertice(vertice1.obtenerNombre());
        grafo.agregarVertice(vertice2.obtenerNombre());
        grafo.agregarVertice(vertice3.obtenerNombre());

        grafo.agregarArista(vertice1, vertice2, 30);  // peso 0.30
        grafo.agregarArista(vertice2, vertice3, 50);  // peso 0.50
        grafo.agregarArista(vertice1, vertice3, 70);  // peso 0.70

        arbolGM = new ArbolGM(grafo);
    }

    @Test
    void testCalcularKruskal() {
        arbolGM.calcularKruskal();
        ArrayList<Arista> aristasGeneradas = arbolGM.obtenerAristasGeneradas();

        // Validamos que se han generado dos aristas (ya que son 3 vértices, se generan n-1 aristas)
        assertEquals(2, aristasGeneradas.size());

        // Verificamos que las aristas seleccionadas son las correctas (las de menor peso)
        Arista arista1 = grafo.obtenerArista(0, 1);
        Arista arista2 = grafo.obtenerArista(1, 2);

        // Comprobamos que las aristas generadas están entre las seleccionadas
        assertTrue(aristasGeneradas.contains(arista1), "La arista entre Espía 1 y Espía 2 debería estar en el árbol");
        assertTrue(aristasGeneradas.contains(arista2), "La arista entre Espía 2 y Espía 3 debería estar en el árbol");
    }

    @Test
    void testCalcularPrim() {
        arbolGM.calcularPrim();
        ArrayList<Arista> aristasGeneradas = arbolGM.obtenerAristasGeneradas();

        // Validamos que se han generado dos aristas (ya que son 3 vértices)
        assertEquals(2, aristasGeneradas.size());

        // Verificamos que las aristas seleccionadas son las correctas (las de menor peso)
        assertTrue(aristasGeneradas.contains(grafo.obtenerArista(vertice1.obtenerId(), vertice2.obtenerId())));
        assertTrue(aristasGeneradas.contains(grafo.obtenerArista(vertice2.obtenerId(), vertice3.obtenerId())));
    }

    @Test
    void testCreaCircuito() {
        assertFalse(arbolGM.creaCircuito(vertice1.obtenerId(), vertice2.obtenerId()));
        arbolGM.union(vertice1.obtenerId(), vertice2.obtenerId());
        assertTrue(arbolGM.creaCircuito(vertice1.obtenerId(), vertice2.obtenerId()));
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

