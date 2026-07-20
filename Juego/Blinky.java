package Juego;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Blinky extends Enemigo {

    public Blinky(int fila, int columna) {
        super("Blinky", fila, columna, 1, cargarImagen());
    }

    private static Image cargarImagen() {
        return new ImageIcon(Blinky.class.getResource("/Juego/recursos/redGhost.png")).getImage();
    }

    @Override
    public void mover(Tablero tablero, Jugador jugador) {
        int diferenciaFila = jugador.getFila() - fila;
        int diferenciaColumna = jugador.getColumna() - columna;

        if (Math.abs(diferenciaFila) >= Math.abs(diferenciaColumna)) {
            if (diferenciaFila < 0
                    && tablero.esMovimientoValido(fila - 1, columna)) {
                fila--;
                return;
            }

            if (diferenciaFila > 0
                    && tablero.esMovimientoValido(fila + 1, columna)) {
                fila++;
                return;
            }

            if (diferenciaColumna < 0
                    && tablero.esMovimientoValido(fila, columna - 1)) {
                columna--;
                return;
            }

            if (diferenciaColumna > 0
                    && tablero.esMovimientoValido(fila, columna + 1)) {
                columna++;
            }
        } else {
            if (diferenciaColumna < 0
                    && tablero.esMovimientoValido(fila, columna - 1)) {
                columna--;
                return;
            }

            if (diferenciaColumna > 0
                    && tablero.esMovimientoValido(fila, columna + 1)) {
                columna++;
                return;
            }

            if (diferenciaFila < 0
                    && tablero.esMovimientoValido(fila - 1, columna)) {
                fila--;
                return;
            }

            if (diferenciaFila > 0
                    && tablero.esMovimientoValido(fila + 1, columna)) {
                fila++;
            }
        }
    }
}