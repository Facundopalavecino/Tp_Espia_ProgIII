package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

import Logica.Grafo;
import Logica.Vertice;
import Logica.ArbolGM;
import Logica.Arista;

public class GrafoFrame extends JFrame {
    private Grafo grafo;
    private JPanel panelGrafo;
    private Stack<Accion> pilaDeshacer, pilaRehacer;
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
        pilaDeshacer = new Stack<>();
        pilaRehacer = new Stack<>();

        // Panel para mostrar el mensaje en la parte superior
        JLabel lblMensaje = new JLabel("Haga click en el lugar de la pantalla donde desea generar un espía.", JLabel.CENTER);
        add(lblMensaje, BorderLayout.NORTH);

        panelGrafo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Linea linea : lineas) {
                    g.drawLine(linea.obtenerX1(), linea.obtenerY1(), linea.obtenerX2(), linea.obtenerY2());
                    int pesoX = (linea.obtenerX1() + linea.obtenerX2()) / 2;
                    int pesoY = (linea.obtenerY1() + linea.obtenerY2()) / 2;
                    g.drawString(Double.toString(linea.obtenerPeso()), pesoX, pesoY);
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

        JButton btnSeleccionarAristas = new JButton("Crear Aristas");
        JButton btnAgregarVertices = new JButton("Crear Vertices");
        
        // Agrego aristas
        btnSeleccionarAristas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (grafo.cantidadVertices() > 0) {
                    habilitarAristas = true;
                    for (JButton btnVertice : btnVertices) {
                        btnVertice.setEnabled(true);
                        btnAgregarVertices.setBackground(null);
                        btnSeleccionarAristas.setBackground(Color.LIGHT_GRAY);
                    }
                }
            }
        });

        // Agrego vertices
        btnAgregarVertices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                habilitarAristas = false;
                for (JButton btnVertice : btnVertices) {
                    btnVertice.setEnabled(false);
                    btnSeleccionarAristas.setBackground(null);
                    btnAgregarVertices.setBackground(Color.LIGHT_GRAY);
                }
            }
        });

        // Botón para Kruskal
        JButton btnKruskal = new JButton("Kruskal");
        btnKruskal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarAlgoritmoKruskal();
            }
        });
        
        // Botón para Prim
        JButton btnPrim = new JButton("Prim");
        btnPrim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarAlgoritmoPrim();
            }
        });

        JButton btnBorrarGrafo = new JButton("Borrar grafo");
        btnBorrarGrafo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrarGrafo();
            }
        });

        JButton btnDeshacer = new JButton("Deshacer ultimo cambio");
        btnDeshacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deshacerUltimaAccion();
            }
        });

        JButton btnRehacer = new JButton("Rehacer ultimo cambio");
        btnRehacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rehacerUltimaAccion();
            }
        });

        panelBotones.add(btnAgregarVertices);
        panelBotones.add(btnSeleccionarAristas);
        panelBotones.add(btnKruskal);
        panelBotones.add(btnPrim);
        panelBotones.add(btnBorrarGrafo);
        panelBotones.add(btnDeshacer);
        panelBotones.add(btnRehacer);
        btnAgregarVertices.setBackground(Color.LIGHT_GRAY);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarEspia(Point posicion) {
    	try {				
	        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del espía:");
            grafo.agregarVertice(nombre);

            JButton btnVertice = new JButton(nombre);
            btnVertice.setBounds(posicion.x - 40, posicion.y - 15, 80, 30);
            btnVertice.setEnabled(false); // Deshabilitado inicialmente
            final int verticeId = btnVertices.size();

            pilaDeshacer.push(new Accion(Accion.TipoAccion.AGREGAR_VERTICE, grafo.obtenerVertice(verticeId), btnVertice));
            
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int verticeSeleccionado1 = -1;

    private void seleccionarVertice(int verticeId) {
    	try {
	        if (verticeSeleccionado1 == -1) {
	            verticeSeleccionado1 = verticeId;
	            btnVertices.get(verticeId).setBackground(Color.YELLOW);
	        } else {
	            int verticeSeleccionado2 = verticeId;
	            if(grafo.verificarAristas(verticeSeleccionado1, verticeSeleccionado2)) {
	            	throw new Exception("Ya existe una arista entre estos dos vértices");
	            }
	            btnVertices.get(verticeSeleccionado1).setBackground(null);
	            agregarArista(verticeSeleccionado1, verticeSeleccionado2);
	            verticeSeleccionado1 = -1;
	        }
    	} catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }

    private void agregarArista(int v1, int v2) {
        if (v1 != v2) {
        	try {
        		String pesoStr = JOptionPane.showInputDialog("Ingrese el peso de la arista entre " + 
                grafo.obtenerVertice(v1).obtenerNombre() + " y " + 
                grafo.obtenerVertice(v2).obtenerNombre() + ":");
        		
        		if (!pesoStr.isEmpty()) {
	                double peso = Double.parseDouble(pesoStr);
	                grafo.agregarArista(v1, v2, peso);
	
	                
	                Point pos1 = posiciones.get(v1);
	                Point pos2 = posiciones.get(v2);
	
	                Linea linea = new Linea(pos1.x, pos1.y, pos2.x, pos2.y, peso);
	                lineas.add(linea);
	         
	                pilaDeshacer.push(new Accion(Accion.TipoAccion.AGREGAR_ARISTA, grafo.obtenerArista(v1, v2), linea));
	                
	                panelGrafo.repaint();
        		}else {
        			throw new Exception("No se ingreso un peso para la arista");
        		}
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
            	JOptionPane.showMessageDialog(this, ex.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void ejecutarAlgoritmoKruskal() {
    	try {			
	    	if(grafo.cantidadVertices() > 0) {
	    		
	        ArbolGM arbolGM = new ArbolGM(grafo);
	        arbolGM.calcularKruskal();
	
	        // Obtener las aristas generadas
	        ArrayList<Arista> aristasAGM = arbolGM.obtenerAristasGeneradas();
	        
	        // Obtener los nombres de los vértices
	        ArrayList<String> nombresEspias = grafo.nombres();
	
	        // Crear y mostrar el AGMFrame
	        new AGMFrame(aristasAGM, posiciones, nombresEspias, "Kruskal").setVisible(true);
	        
	    	} else {
	    		throw new Exception("No hay vertices para generar el AGM");
	    	}
    	} catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    private void ejecutarAlgoritmoPrim() {
        try {
            if (grafo.cantidadVertices() > 0) {
                ArbolGM arbolGM = new ArbolGM(grafo);
                arbolGM.calcularPrim();

                // Obtener las aristas generadas
                ArrayList<Arista> aristasAGM = arbolGM.obtenerAristasGeneradas();

                // Obtener los nombres de los vértices
                ArrayList<String> nombresEspias = grafo.nombres();

                // Crear y mostrar el AGMFrame
                new AGMFrame(aristasAGM, posiciones, nombresEspias, "Prim").setVisible(true);
            } else {
                throw new Exception("No hay vertices para generar el AGM");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void borrarGrafo() {    	
        panelGrafo.removeAll();
        panelGrafo.repaint(); 
        lineas.clear();
        pilaDeshacer.clear();
        pilaRehacer.clear();
        grafo.borrar();
    }

    public void deshacerUltimaAccion() {
        if (!pilaDeshacer.isEmpty()) {
            Accion ultimaAccion = pilaDeshacer.pop();
            pilaRehacer.push(ultimaAccion);
            
            switch (ultimaAccion.obtenerTipo()) {
                case AGREGAR_VERTICE:
                	panelGrafo.remove(ultimaAccion.obtenerBotonVertice());
                	btnVertices.remove(ultimaAccion.obtenerBotonVertice());
                    grafo.eliminarVertice(ultimaAccion.obtenerVertice().obtenerId());
                    break;
                case AGREGAR_ARISTA:
                	lineas.remove(ultimaAccion.obtenerLinea()); 
                    grafo.eliminarArista(ultimaAccion.obtenerArista().obtenerOrigen(), ultimaAccion.obtenerArista().obtenerDestino());
                    grafo.eliminarArista(ultimaAccion.obtenerArista().obtenerDestino(), ultimaAccion.obtenerArista().obtenerOrigen());
                    break;
            }

            panelGrafo.revalidate();  
            panelGrafo.repaint();
        }
    }
    
    public void rehacerUltimaAccion() {
        if (!pilaRehacer.isEmpty()) {
            Accion ultimaAccion = pilaRehacer.pop();

            // Mover la acción rehecha a la pila de deshacer
            pilaDeshacer.push(ultimaAccion);

            switch (ultimaAccion.obtenerTipo()) {
                case AGREGAR_VERTICE:
                    grafo.agregarVertice(ultimaAccion.obtenerVertice().obtenerNombre());
                    rehacerVertice(ultimaAccion.obtenerBotonVertice(), ultimaAccion.obtenerVertice());
                    break;
                case AGREGAR_ARISTA:
                    grafo.agregarArista(ultimaAccion.obtenerArista().obtenerOrigen(), ultimaAccion.obtenerArista().obtenerDestino(), ultimaAccion.obtenerArista().obtenerPeso());
                    rehacerArista(ultimaAccion.obtenerArista());
                    break;
            }

            panelGrafo.revalidate();
            panelGrafo.repaint();
        }
    }
    
    public void rehacerArista(Arista arista) {
    	Point pos1 = posiciones.get(arista.obtenerOrigen().obtenerId());
        Point pos2 = posiciones.get(arista.obtenerDestino().obtenerId());

        Linea linea = new Linea(pos1.x, pos1.y, pos2.x, pos2.y, arista.obtenerPeso());
        lineas.add(linea);
 
        pilaDeshacer.push(new Accion(Accion.TipoAccion.AGREGAR_ARISTA, grafo.obtenerArista(arista.obtenerOrigen().obtenerId(), arista.obtenerDestino().obtenerId()), linea));
    }
    
    public void rehacerVertice(JButton btnVertice, Vertice vertice) {
        panelGrafo.add(btnVertice);
        btnVertices.add(btnVertice);
        
        pilaDeshacer.push(new Accion(Accion.TipoAccion.AGREGAR_VERTICE, vertice, btnVertice));
    }
}