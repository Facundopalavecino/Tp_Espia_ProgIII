package Logica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

class GrafoTest {

    private Grafo grafo;
    private Vertice vertice1;
    private Vertice vertice2;
    private Vertice vertice3;

    @BeforeEach
    void setUp() {
        grafo = new Grafo("Red de Espías");
        vertice1 = new Vertice(0, "Espía 1");
        vertice2 = new Vertice(1, "Espía 2");
        vertice3 = new Vertice(2, "Espía 3");

        grafo.agregarVertice("Espía 1");
        grafo.agregarVertice("Espía 2");
    }

    @Test
    void testAgregarVertice() {
        assertEquals(2, grafo.totalVertices(), "Debería haber 2 vértices inicialmente");

        grafo.agregarVertice("Espía 3");
        assertEquals(3, grafo.totalVertices(), "Debería haber 3 vértices después de agregar uno más");

        Vertice verticeObtenido = grafo.obtenerVertice(1);
        assertNotNull(verticeObtenido);
        assertEquals("Espía 2", verticeObtenido.obtenerNombre(), "El nombre del vértice debería ser 'Espía 2'");
    }

    @Test
    void testEliminarVertice() {
        grafo.eliminarVertice(1);
        assertEquals(1, grafo.totalVertices(), "Debería haber 1 vértice después de eliminar uno");

        Vertice verticeEliminado = grafo.obtenerVertice(1);
        assertNull(verticeEliminado, "El vértice 1 debería haber sido eliminado");
    }

    @Test
    void testAgregarArista() {
        grafo.agregarArista(0, 1, 60);
        HashSet<Arista> aristas = grafo.obtenerAristas();

        assertEquals(2, aristas.size(), "Debería haber 2 aristas después de agregar una arista bidireccional");
        assertTrue(grafo.verificarAristas(0, 1), "Debería existir una arista entre los vértices 0 y 1");
        assertTrue(grafo.verificarAristas(1, 0), "Debería existir una arista entre los vértices 1 y 0");
    }

    @Test
    void testEliminarArista() {
        grafo.agregarArista(1, 2, 60);
        grafo.eliminarArista(vertice1, vertice2);

        HashSet<Arista> aristas = grafo.obtenerAristas();
        assertEquals(0, aristas.size(), "Debería haber 0 aristas después de eliminar la arista");
    }

    @Test
    void testVerificarAristas() {
        assertFalse(grafo.verificarAristas(0, 1), "No debería haber una arista entre 1 y 2 inicialmente");

        grafo.agregarArista(0, 1, 60);
        assertTrue(grafo.verificarAristas(0, 1), "Debería existir una arista entre 1 y 2 después de agregarla");
    }

    @Test
    void testNombreRepetido() {
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.agregarVertice("Espía 1"); // Nombre repetido
        }, "Debería lanzar una excepción si se agrega un vértice con un nombre repetido");
    }

    @Test
    void testBorrar() {
        grafo.agregarVertice("Espía 3");
        grafo.agregarArista(1, 2, 50);
        grafo.borrar();

        assertEquals(0, grafo.totalVertices(), "Debería haber 0 vértices después de borrar el grafo");
        assertEquals(0, grafo.obtenerAristas().size(), "Debería haber 0 aristas después de borrar el grafo");
    }

    @Test
    void testObtenerVertice() {
        Vertice verticeObtenido = grafo.obtenerVertice(0);
        assertNotNull(verticeObtenido, "Debería obtenerse el vértice 1");
        assertEquals("Espía 1", verticeObtenido.obtenerNombre(), "El nombre del vértice debería ser 'Espía 1'");

        Vertice verticeNoExistente = grafo.obtenerVertice(10);
        assertNull(verticeNoExistente, "No debería encontrarse un vértice inexistente");
    }

    @Test
    void testTotalVertices() {
        assertEquals(2, grafo.totalVertices(), "Debería haber 2 vértices inicialmente");

        grafo.agregarVertice("Espía 3");
        assertEquals(3, grafo.totalVertices(), "Debería haber 3 vértices después de agregar uno más");
    }

}
