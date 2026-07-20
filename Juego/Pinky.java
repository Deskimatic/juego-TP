package Juego;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Pinky extends Enemigo {

    public Pinky(int fila, int columna) {
        super(
                "Pinky",
                fila,
                columna,
                1,
                cargarImagen()
        );
    }

    private static Image cargarImagen() {
        return new ImageIcon(
                Pinky.class.getResource("/Juego/recursos/pinkGhost.png")
        ).getImage();
    }

    @Override
    public void mover(Tablero tablero, Jugador jugador) {
        int filaObjetivo = jugador.getFila();
        int columnaObjetivo = jugador.getColumna();

        switch (jugador.getDireccion()) {
            case "ARRIBA":
                filaObjetivo -= 4;
                break;

            case "ABAJO":
                filaObjetivo += 4;
                break;

            case "IZQUIERDA":
                columnaObjetivo -= 4;
                break;

            case "DERECHA":
                columnaObjetivo += 4;
                break;
        }

        int nuevaFila = fila;
        int nuevaColumna = columna;

        if (filaObjetivo < fila) {
            nuevaFila--;
        } else if (filaObjetivo > fila) {
            nuevaFila++;
        }

        if (columnaObjetivo < columna) {
            nuevaColumna--;
        } else if (columnaObjetivo > columna) {
            nuevaColumna++;
        }

        if (tablero.esMovimientoValido(nuevaFila, nuevaColumna)) {
            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }
}