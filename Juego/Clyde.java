package Juego;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Random;

public class Clyde extends Enemigo {

    private Random random;

    public Clyde(int fila, int columna) {
        super("Clyde", fila, columna, 1, cargarImagen());

        random = new Random();
    }

    private static Image cargarImagen() {
        return new ImageIcon(
                Clyde.class.getResource("/Juego/recursos/orangeGhost.png")).getImage();
    }

    @Override
    public void mover(Tablero tablero, Jugador jugador) {
        int direccion = random.nextInt(4);

        int nuevaFila = fila;
        int nuevaColumna = columna;

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

        if (tablero.esMovimientoValido(nuevaFila, nuevaColumna)) {
            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }
}
