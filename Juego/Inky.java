package Juego;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Random;

public class Inky extends Enemigo {

    private Blinky blinky;
    private Random random;

    public Inky(int fila, int columna, Blinky blinky) {
        super(
                "Inky",
                fila,
                columna,
                1,
                cargarImagen()
        );

        this.blinky = blinky;
        random = new Random();
    }

    private static Image cargarImagen() {
        return new ImageIcon(
                Inky.class.getResource("/Juego/recursos/blueGhost.png")
        ).getImage();
    }

    @Override
    public void mover(Tablero tablero, Jugador jugador) {
        int distancia = Math.abs(blinky.getFila() - jugador.getFila())
                + Math.abs(blinky.getColumna() - jugador.getColumna());

        int nuevaFila = fila;
        int nuevaColumna = columna;

        // Si Blinky está cerca de Pac-Man, Inky se mueve aleatoriamente.
        if (distancia < 6) {
            int direccion = random.nextInt(4);

            switch (direccion) {
                case 0:
                    nuevaFila--;
                    break;

                case 1:
                    nuevaFila++;
                    break;

                case 2:
                    nuevaColumna--;
                    break;

                case 3:
                    nuevaColumna++;
                    break;
            }
        } else {
            // Si Blinky está lejos, persigue a Pac-Man.
            if (jugador.getFila() < fila) {
                nuevaFila--;
            } else if (jugador.getFila() > fila) {
                nuevaFila++;
            }

            if (jugador.getColumna() < columna) {
                nuevaColumna--;
            } else if (jugador.getColumna() > columna) {
                nuevaColumna++;
            }
        }

        if (tablero.esMovimientoValido(nuevaFila, nuevaColumna)) {
            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }
}
