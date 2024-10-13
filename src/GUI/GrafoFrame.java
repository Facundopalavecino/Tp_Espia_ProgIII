package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Logica.Grafo;
import Logica.ArbolGM;
import Logica.Arista;

public class GrafoFrame extends JFrame {
    private Grafo grafo;
    private JPanel panelGrafo;
    private ArrayList<JButton> btnVertices;
    private ArrayList<Point> posiciones;
    private ArrayList<Linea> lineas;
    private boolean habilitarAristas = false;

    public GrafoFrame(Grafo grafo) {
        this.grafo = grafo;
        setTitle("Grafo de Espías");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        lineas = new ArrayList<>();
        btnVertices = new ArrayList<>();
        posiciones = new ArrayList<>();

        // Panel para mostrar el mensaje en la parte superior
        JLabel lblMensaje = new JLabel("Haga click en el lugar de la pantalla donde desea generar un espía.", JLabel.CENTER);
        add(lblMensaje, BorderLayout.NORTH);

        panelGrafo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Linea linea : lineas) {
                    g.drawLine(linea.x1, linea.y1, linea.x2, linea.y2);
                    int pesoX = (linea.x1 + linea.x2) / 2;
                    int pesoY = (linea.y1 + linea.y2) / 2;
                    g.drawString(Double.toString(linea.peso), pesoX, pesoY);
                }
            }
        };
        panelGrafo.setLayout(null);
        panelGrafo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!habilitarAristas) {
                    agregarEspia(e.getPoint());
                }
            }
        });
        add(panelGrafo, BorderLayout.CENTER);

        // Crear un panel para los botones en la parte inferior
        JPanel panelBotones = new JPanel();

        JButton btnSeleccionarAristas = new JButton("Crear aristas");
        btnSeleccionarAristas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                habilitarAristas = true;
                for (JButton btnVertice : btnVertices) {
                    btnVertice.setEnabled(true);
                }
            }
        });

        JButton btnAGM = new JButton("AGM");
        btnAGM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarAlgoritmoKruskal();
            }
        });

        panelBotones.add(btnSeleccionarAristas);
        panelBotones.add(btnAGM);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarEspia(Point posicion) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del espía:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            grafo.agregarVertice(nombre);

            JButton btnVertice = new JButton(nombre);
            btnVertice.setBounds(posicion.x - 40, posicion.y - 15, 80, 30);
            btnVertice.setEnabled(false); // Deshabilitado inicialmente
            final int verticeId = btnVertices.size();

            btnVertice.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (habilitarAristas) {
                        seleccionarVertice(verticeId);
                    }
                }
            });

            panelGrafo.add(btnVertice);
            btnVertices.add(btnVertice);
            posiciones.add(posicion);
            panelGrafo.repaint();
        }
    }

    private int verticeSeleccionado1 = -1;

    private void seleccionarVertice(int verticeId) {
        if (verticeSeleccionado1 == -1) {
            verticeSeleccionado1 = verticeId;
            btnVertices.get(verticeId).setBackground(Color.YELLOW);
        } else {
            int verticeSeleccionado2 = verticeId;
            btnVertices.get(verticeSeleccionado1).setBackground(null);
            agregarArista(verticeSeleccionado1, verticeSeleccionado2);
            verticeSeleccionado1 = -1;
        }
    }

    private void agregarArista(int v1, int v2) {
        if (v1 != v2) {
            String pesoStr = JOptionPane.showInputDialog("Ingrese el peso de la arista entre " + 
                grafo.obtenerVertice(v1).obtenerNombre() + " y " + 
                grafo.obtenerVertice(v2).obtenerNombre() + ":");
            try {
                double peso = Double.parseDouble(pesoStr);
                grafo.agregarArista(v1, v2, peso);

                Point pos1 = posiciones.get(v1);
                Point pos2 = posiciones.get(v2);

                lineas.add(new Linea(
                    pos1.x, pos1.y, 
                    pos2.x, pos2.y, 
                    peso
                ));
                panelGrafo.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido para el peso.");
            }
        }
    }

    private void ejecutarAlgoritmoKruskal() {
        ArbolGM arbolGM = new ArbolGM(grafo);
        arbolGM.algoritmoKruskal();

        // Obtener las aristas generadas
        ArrayList<Arista> aristasAGM = arbolGM.obtenerAristasGeneradas();
        
        // Obtener los nombres de los vértices
        ArrayList<String> nombresEspias = grafo.nombres();

        // Crear y mostrar el AGMFrame
        new AGMFrame(aristasAGM, posiciones, nombresEspias).setVisible(true);
    }

    private class Linea {
        int x1, y1, x2, y2;
        double peso;

        Linea(int x1, int y1, int x2, int y2, double peso) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.peso = peso;
        }
    }
}