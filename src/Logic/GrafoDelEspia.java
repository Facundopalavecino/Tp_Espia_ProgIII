package Logic;

import java.util.*;

public class GrafoDelEspia {
    private Map<Espia, List<parDeEspias>> adjList = new HashMap<>();

    public void agregarEspia(Espia espia) {
        adjList.putIfAbsent(espia, new ArrayList<>());
    }

    public void agregarConexion(Espia espia1, Espia espia2, double probabilidadIntercepcion) {
    	parDeEspias arista = new parDeEspias(espia1, espia2, probabilidadIntercepcion);
        adjList.get(espia1).add(arista);
        adjList.get(espia2).add(arista);
    }

    public Map<Espia, List<parDeEspias>> getAdjList() {
        return adjList;
    }

   public List<parDeEspias> obtenerAGM() {
        Prim prim = new Prim();
        return prim.calcularAGM(this);
    } 
}

