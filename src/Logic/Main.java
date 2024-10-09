package Logic;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de GrafoDelEspia
        GrafoDelEspia grafo = new GrafoDelEspia();

        // Crear instancias de Espia
        Espia espia1 = new Espia("Espia 1");
        Espia espia2 = new Espia("Espia 2");
        Espia espia3 = new Espia("Espia 3");
        Espia espia4 = new Espia("Espia 4");
        Espia espia5 = new Espia("Espia 5");

        // Agregar espías al grafo
        grafo.agregarEspia(espia1);
        grafo.agregarEspia(espia2);
        grafo.agregarEspia(espia3);
        grafo.agregarEspia(espia4);
        grafo.agregarEspia(espia5);

        // Agregar conexiones (aristas) con probabilidades de intercepción
        grafo.agregarConexion(espia1, espia2, 0.4);
        grafo.agregarConexion(espia1, espia3, 0.2);
        grafo.agregarConexion(espia2, espia3, 0.1);
        grafo.agregarConexion(espia2, espia4, 0.7);
        grafo.agregarConexion(espia3, espia4, 0.3);
        grafo.agregarConexion(espia4, espia5, 0.5);

        // Calcular el Árbol Generador Mínimo (AGM) usando el algoritmo de Prim
        List<parDeEspias> agm = grafo.obtenerAGM();

        // Imprimir las aristas del AGM
        System.out.println("Árbol Generador Mínimo (AGM):");
        for (parDeEspias arista : agm) {
            System.out.println(arista.getEspia1().getNombre() + " - " +
                               arista.getEspia2().getNombre() + " : (Probabilidad) " +
                               arista.getProbabilidadIntercepcion());
        }
    }
}
