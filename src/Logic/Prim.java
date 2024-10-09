package Logic;
import java.util.*;

public class Prim {

    public List<parDeEspias> calcularAGM(GrafoDelEspia grafo) {
        Set<Espia> visitados = new HashSet<>();
        PriorityQueue<parDeEspias> aristas = new PriorityQueue<>();
        List<parDeEspias> agm = new ArrayList<>();

        // Seleccionar un vértice inicial arbitrario
        Espia espiaInicial = grafo.getAdjList().keySet().iterator().next();
        visitados.add(espiaInicial);
        agregarAristasNoVisitadas(espiaInicial, aristas, grafo, visitados);

        // Ejecutar el algoritmo de Prim
        while (!aristas.isEmpty() && visitados.size() < grafo.getAdjList().size()) {
        	parDeEspias aristaMin = aristas.poll();
            Espia espia1 = aristaMin.getEspia1();
            Espia espia2 = aristaMin.getEspia2();

            // Determinar cuál de los espías de la arista no ha sido visitado
            if (!espiaYaVisitado(espia1, visitados) || !espiaYaVisitado(espia2, visitados)) {
                agm.add(aristaMin);
                Espia espiaNoVisitado = espiaYaVisitado(espia1, visitados) ? espia2 : espia1;
                visitados.add(espiaNoVisitado);
                agregarAristasNoVisitadas(espiaNoVisitado, aristas, grafo, visitados);
            }
        }
        return agm;
    }

    // Método para verificar si un espía ha sido visitado
    private boolean espiaYaVisitado(Espia espia, Set<Espia> visitados) {
        return visitados.contains(espia);
    }

    // Método para agregar las aristas de un espía no visitado a la cola de prioridad
    private void agregarAristasNoVisitadas(Espia espia, PriorityQueue<parDeEspias> aristas, GrafoDelEspia grafo, Set<Espia> visitados) {
        for (parDeEspias arista : grafo.getAdjList().get(espia)) {
            Espia vecino = arista.getEspia1().equals(espia) ? arista.getEspia2() : arista.getEspia1();
            if (!visitados.contains(vecino)) {
                aristas.add(arista);
            }
        }
    }

}
