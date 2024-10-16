package Logica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AristaTest {

    private Vertice vertice1;
    private Vertice vertice2;
    private Vertice vertice3;
    private Arista arista1;
    private Arista arista2;

    @BeforeEach
    void setUp() {
        // Inicializamos los vértices
        vertice1 = new Vertice(1, "Espía 1");
        vertice2 = new Vertice(2, "Espía 2");
        vertice3 = new Vertice(3, "Espía 3");

        // Inicializamos las aristas
        arista1 = new Arista(1, vertice1, vertice2, 0.5);  // Peso 50
        arista2 = new Arista(2, vertice2, vertice3, 0.3);  // Peso 30
    }

    @Test
    void testConstructor() {
        assertNotNull(arista1);
        assertEquals(1, arista1.obtenerId());
        assertEquals(0.5, arista1.obtenerPeso(), "El peso debería ser 50");
        assertEquals(vertice1, arista1.obtenerOrigen());
        assertEquals(vertice2, arista1.obtenerDestino());
    }

    @Test
    void testObtenerPeso() {
        assertEquals(0.5, arista1.obtenerPeso(), "El peso debería ser 50");
        assertEquals(0.3, arista2.obtenerPeso(), "El peso debería ser 30");
    }

    @Test
    void testObtenerConexion() {
        assertEquals("1-2", arista1.obtenerConexion(), "La conexión debería ser 1-2");
        assertEquals("2-3", arista2.obtenerConexion(), "La conexión debería ser 2-3");
    }

    @Test
    void testExisteConexion() {
        assertTrue(arista1.existeConexion(1, 2), "Debería existir una conexión entre 1 y 2");
        assertFalse(arista1.existeConexion(2, 3), "No debería existir una conexión entre 2 y 3");
    }

    @Test
    void testCompareTo() {
        assertTrue(arista1.compareTo(arista2) > 0, "Arista1 debería ser mayor que Arista2 en peso");
        assertTrue(arista2.compareTo(arista1) < 0, "Arista2 debería ser menor que Arista1 en peso");
        Arista aristaIgualPeso = new Arista(3, vertice1, vertice3, 0.5); // Peso igual a arista1
        assertEquals(0, arista1.compareTo(aristaIgualPeso), "Deberían ser iguales en peso");
    }

    @Test
    void testConstructorPesoInvalido() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            new Arista(3, vertice1, vertice2, 1.5);  // Peso inválido (fuera de rango)
        });
        assertEquals("El peso debe estar entre 0 y 1.", exception.getMessage());
    }

}