package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Logica.Arista;

public class AGMFrame extends JFrame {
    private ArrayList<Arista> aristas;
    private ArrayList<Point> posiciones;
    private ArrayList<String> nombres; // Para almacenar los nombres de los vértices
    private String titulo;


    public AGMFrame(ArrayList<Arista> aristas, ArrayList<Point> posiciones, ArrayList<String> nombres, String titulo, Double tiempoEjecutado) {
        this.aristas = aristas;
        this.posiciones = posiciones;
        this.nombres = nombres; // Almacena los nombres de los espías
        this.titulo = titulo;


        setTitle("Árbol Generador Mínimo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel lblMensaje = new JLabel("Grafo del Árbol Generador Mínimo por algoritmo de " + titulo, JLabel.CENTER);
        add(lblMensaje, BorderLayout.NORTH);

        JLabel lblTiempo = new JLabel("Tiempo ejecutado: " + tiempoEjecutado + "s", SwingConstants.CENTER);
        add(lblTiempo, BorderLayout.SOUTH);
        
        JPanel panelAGM = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibuja las aristas del AGM
                for (Arista arista : aristas) {
                    int x1 = posiciones.get(arista.obtenerOrigen().obtenerId()).x;
                    int y1 = posiciones.get(arista.obtenerOrigen().obtenerId()).y;
                    int x2 = posiciones.get(arista.obtenerDestino().obtenerId()).x;
                    int y2 = posiciones.get(arista.obtenerDestino().obtenerId()).y;

                    g.drawLine(x1, y1, x2, y2);
                    double peso = arista.obtenerPeso();
                    int pesoX = (x1 + x2) / 2;
                    int pesoY = (y1 + y2) / 2;
                    g.drawString(Double.toString(peso*100)+"%", pesoX, pesoY);
                }

                // Dibuja los vértices (espías) en sus posiciones originales
                for (int i = 0; i < posiciones.size(); i++) {
                    Point posicion = posiciones.get(i);
                    g.fillOval(posicion.x - 15, posicion.y - 15, 30, 30); // Dibuja el espía como un círculo
                    g.drawString(nombres.get(i), posicion.x - 15, posicion.y - 20); // Dibuja el nombre del espía
                }
            }
        };

        add(panelAGM, BorderLayout.CENTER);
    }
}