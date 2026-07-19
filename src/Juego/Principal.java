package Juego;

import javax.swing.*;

public class Principal {

    public static void main(String[] args) {

        String filasTexto = JOptionPane.showInputDialog(
                "Ingrese el número de filas:"
        );

        String columnasTexto = JOptionPane.showInputDialog(
                "Ingrese el número de columnas:"
        );

        int filas = Integer.parseInt(filasTexto);

        int columnas = Integer.parseInt(columnasTexto);

        JFrame ventana = new JFrame("Pac-Man");

        Juego juego = new Juego(
                filas,
                columnas
        );

        ventana.add(juego);

        ventana.pack();

        ventana.setLocationRelativeTo(null);

        ventana.setResizable(false);

        ventana.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        ventana.setVisible(true);

        juego.requestFocusInWindow();
    }
}