package GUI;

import javax.swing.JButton;

import Logica.Arista;
import Logica.Vertice;

public class Accion {
    public enum TipoAccion { AGREGAR_VERTICE, AGREGAR_ARISTA }
    
    private TipoAccion tipo;
    private JButton botonVertice;
    private Vertice vertice;
    private Arista arista;
    private Linea linea;

    public Accion(TipoAccion tipo, Vertice vertice, JButton botonVertice) {
        this.tipo = tipo;
        this.vertice = vertice;
        this.botonVertice = botonVertice;
    }

    public Accion(TipoAccion tipo, Arista arista, Linea linea) {
        this.tipo = tipo;
        this.arista = arista;
        this.linea = linea;
    }

    public TipoAccion obtenerTipo() {
        return tipo;
    }

    public Vertice obtenerVertice() {
        return vertice;
    }
    
    public JButton obtenerBotonVertice() {
    	return botonVertice;
    }

    public Arista obtenerArista() {
        return arista;
    }
    
    public Linea obtenerLinea() {
        return linea;
    }
}
