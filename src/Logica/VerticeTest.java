package Logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerticeTest {

    private Vertice vertice1;
    private Vertice vertice2;
    private Vertice vertice3;

    @BeforeEach
    void setUp() {
        vertice1 = new Vertice(1, "Espía 1");
        vertice2 = new Vertice(2, "Espía 2");
        vertice3 = new Vertice(3, "Espía 1"); // Nombre duplicado pero diferente ID
    }

    @Test
    void testObtenerId() {
        assertEquals(1, vertice1.obtenerId(), "El ID del vértice 1 debería ser 1");
        assertEquals(2, vertice2.obtenerId(), "El ID del vértice 2 debería ser 2");
    }

    @Test
    void testObtenerNombre() {
        assertEquals("Espía 1", vertice1.obtenerNombre(), "El nombre del vértice 1 debería ser 'Espía 1'");
        assertEquals("Espía 2", vertice2.obtenerNombre(), "El nombre del vértice 2 debería ser 'Espía 2'");
    }

    @Test
    void testCambiarNombre() {
    	String nombre = "Espía X";
        vertice1.setNombre(nombre);
        assertEquals("Espía X", vertice1.obtenerNombre(), "El nombre del vértice 1 debería ser 'Espía X' después de cambiarlo");
    }

    @Test
    void testIdUnico() {
        assertNotEquals(vertice1.obtenerId(), vertice3.obtenerId(), "Dos vértices diferentes deberían tener IDs únicos");
        assertEquals(vertice1.obtenerNombre(), vertice3.obtenerNombre(), "Los vértices pueden tener nombres repetidos");
    }

    @Test
    void testNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice(5, "");
        }, "Debería lanzar una excepción si el nombre del vértice es una cadena vacía");
    }
    
    @Test
    void testAgregarVecino() {
        vertice1.agregarVecino(2);
        vertice1.agregarVecino(3);

        ArrayList<Integer> vecinos = vertice1.obtenerVecinos();

        assertTrue(vecinos.contains(2), "El vértice 1 debería tener como vecino al vértice 2");
        assertTrue(vecinos.contains(3), "El vértice 1 debería tener como vecino al vértice 3");
    }
    
    @Test
    void testObtenerVecinos() {
        vertice1.agregarVecino(2);
        vertice1.agregarVecino(3);

        ArrayList<Integer> vecinos = vertice1.obtenerVecinos();

        assertEquals(2, vecinos.size(), "El vértice 1 debería tener exactamente 2 vecinos");
        assertEquals(2, vecinos.get(0), "El primer vecino debería ser el vértice 2");
        assertEquals(3, vecinos.get(1), "El segundo vecino debería ser el vértice 3");
    }
    
}
