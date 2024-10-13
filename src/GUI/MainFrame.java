package GUI;

import javax.swing.*;

import Logica.Grafo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Comunicación entre Espías");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(null);

        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBounds(140, 60, 100, 30);
        add(btnIniciar);

        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Grafo grafo = new Grafo("Mensaje");
                new GrafoFrame(grafo).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}