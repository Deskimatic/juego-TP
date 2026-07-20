package Juego;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuPrincipal {

    public void mostrarMenu() {
        while (true) {
            String opcionTexto = JOptionPane.showInputDialog(
                    """
                    ===== PAC-MAN =====
    
                    1. Jugar
                    2. Instrucciones
                    3. Créditos
                    4. Salir
    
                    Seleccione una opción:
                    """
            );

            if (opcionTexto == null) {
                System.exit(0);
            }

            int opcion;

            try {
                opcion = Integer.parseInt(opcionTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ingrese una opción válida."
                );
                continue;
            }

            switch (opcion) {
                case 1:
                    iniciarJuego();
                    return;

                case 2:
                    mostrarInstrucciones();
                    break;

                case 3:
                    mostrarCreditos();
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "Opción inválida."
                    );
            }
        }
    }

    private void iniciarJuego() {
        int filasJugables = pedirEntero(
                "Ingrese el número de filas del área jugable:"
        );

        int columnasJugables = pedirEntero(
                "Ingrese el número de columnas del área jugable:"
        );

        boolean muroPersonalizado = preguntarSiPersonaliza();
        int cantidadMuros = 0;

        if (muroPersonalizado) {
            cantidadMuros = pedirEntero(
                    "Ingrese la cantidad de muros:"
            );
        }

        JFrame ventana = new JFrame("Pac-Man");

        Juego juego = new Juego(
                filasJugables,
                columnasJugables,
                muroPersonalizado,
                cantidadMuros
        );

        ventana.add(juego);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);

        juego.requestFocusInWindow();
    }

    private void mostrarInstrucciones() {
        JOptionPane.showMessageDialog(
                null,
                """
                CONTROLES
    
                W → Arriba
                A → Izquierda
                S → Abajo
                D → Derecha
    
                OBJETIVO
    
                • Come todos los puntos.
                • Evita a los fantasmas.
                • Recoge cerezas para recuperar vida.
                • Completa el laberinto para ganar.
                """
        );
    }

    private void mostrarCreditos() {
        JOptionPane.showMessageDialog(
                null,
                """
                PAC-MAN
    
                Elaborado por:
    
                • Jorge André Rojas Montaño
                • Noe Alejo Carlos
                • Nombre 3                            
    
                Curso: Taller de programación 
                Docente Jesamin Melissa
                UTP             
                """
        );
    }

    private boolean preguntarSiPersonaliza() {
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Desea personalizar la cantidad de muros?",
                "Configuración",
                JOptionPane.YES_NO_OPTION
        );

        return opcion == JOptionPane.YES_OPTION;
    }

    private int pedirEntero(String mensaje) {
        while (true) {
            String texto = JOptionPane.showInputDialog(mensaje);

            if (texto == null) {
                System.exit(0);
            }

            try {
                int numero = Integer.parseInt(texto);

                if (numero > 0) {
                    return numero;
                }

                JOptionPane.showMessageDialog(
                        null,
                        "Ingrese un número positivo."
                );

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ingrese un número válido."
                );
            }
        }
    }
}