package Juego;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.ImageIcon;

public class Tablero {

    private int filas;
    private int columnas;
    private int tamCelda = 32;

    // Si es true, se usa la cantidad EXACTA de muros que pidió el usuario.
    // Si es false, se usa el algoritmo original (probabilidad del 60%).
    private boolean muroPersonalizado;

    // Cantidad exacta de muros/obstáculos internos (solo aplica si
    // muroPersonalizado es true).
    private int cantidadMuros;

    // Probabilidad (0 a 100) que usaba el juego originalmente para
    // decidir si una celda interna se convierte en muro.
    private static final int DENSIDAD_MUROS_POR_DEFECTO = 60;

    private char[][] matriz;

    private ArrayList<Muro> muros;
    private ArrayList<Punto> puntos;
    private ArrayList<Poder> poderes;

    private Image imagenMuro;
    private Image imagenPunto;
    private Image imagenPoder;
    private Image imagenCongelar;

    // Posición fija donde nace Pac-Man (definida en Juego.java).
    // La excluimos de los muros para que nunca nazca encima de uno.
    private static final int FILA_INICIO_JUGADOR = 1;
    private static final int COLUMNA_INICIO_JUGADOR = 2;

    // filasJugables / columnasJugables: el área donde Pac-Man se puede
    // mover, SIN contar el borde. El borde se agrega automáticamente.
    //
    // muroPersonalizado = false -> se ignora cantidadMuros y se usa el
    //                              algoritmo original (por defecto).
    // muroPersonalizado = true  -> se usan exactamente "cantidadMuros"
    //                              muros internos.
    public Tablero(
            int filasJugables,
            int columnasJugables,
            boolean muroPersonalizado,
            int cantidadMuros
    ) {
        // Sumamos 2: una fila/columna de borde a cada lado.
        this.filas = filasJugables + 2;
        this.columnas = columnasJugables + 2;

        this.muroPersonalizado = muroPersonalizado;

        if (cantidadMuros < 0) {
            cantidadMuros = 0;
        }

        this.cantidadMuros = cantidadMuros;

        matriz = new char[filas][columnas];

        muros = new ArrayList<>();
        puntos = new ArrayList<>();
        poderes = new ArrayList<>();

        cargarImagenes();
        generarTablero();
    }

    private void cargarImagenes() {
        imagenMuro = new ImageIcon(
                getClass().getResource("/Juego/recursos/wall.png")
        ).getImage();

        imagenPunto = new ImageIcon(
                getClass().getResource("/Juego/recursos/powerFood1.png")
        ).getImage();

        imagenPoder = new ImageIcon(
                getClass().getResource("/Juego/recursos/cherry.png")
        ).getImage();

        imagenCongelar = new ImageIcon(
                getClass().getResource("/Juego/recursos/cherryBlue1.png")
        ).getImage();
    }

    public void generarTablero() {
        agregarBorde();

        if (muroPersonalizado) {
            agregarMurosPersonalizados();
        } else {
            agregarMurosPorDefecto();
        }

        agregarPoderes();
        agregarPuntos();
    }

    // Borde exterior: encierra todo el área jugable. Se agrega siempre,
    // sin importar el modo de muros elegido.
    private void agregarBorde() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == 0 || j == 0 || i == filas - 1 || j == columnas - 1) {
                    crearMuro(i, j);
                }
            }
        }
    }

    // Algoritmo ORIGINAL del primer código: recorre la matriz en pasos
    // de 2 y, con una probabilidad del 60%, coloca un muro y otro
    // extra al lado en una dirección aleatoria.
    private void agregarMurosPorDefecto() {
        Random random = new Random();

        for (int i = 2; i < filas - 2; i += 2) {
            for (int j = 2; j < columnas - 2; j += 2) {
                if (random.nextInt(100) < DENSIDAD_MUROS_POR_DEFECTO) {
                    crearMuro(i, j);

                    int direccion = random.nextInt(4);

                    switch (direccion) {
                        case 0:
                            crearMuro(i - 1, j);
                            break;

                        case 1:
                            crearMuro(i + 1, j);
                            break;

                        case 2:
                            crearMuro(i, j - 1);
                            break;

                        case 3:
                            crearMuro(i, j + 1);
                            break;
                    }
                }
            }
        }
    }

    // Modo NUEVO: coloca exactamente "cantidadMuros" celdas internas
    // como muros, elegidas al azar dentro del área jugable.
    private void agregarMurosPersonalizados() {
        ArrayList<int[]> candidatas = new ArrayList<>();

        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                if (esCeldaReservada(i, j)) {
                    continue;
                }

                candidatas.add(new int[]{i, j});
            }
        }

        Collections.shuffle(candidatas, new Random());

        int total = Math.min(cantidadMuros, candidatas.size());

        for (int k = 0; k < total; k++) {
            int[] celda = candidatas.get(k);
            crearMuro(celda[0], celda[1]);
        }
    }

    // Celdas que nunca deben convertirse en muro: donde nace Pac-Man
    // y las 4 esquinas donde aparecen los poderes.
    private boolean esCeldaReservada(int fila, int columna) {
        if (fila == FILA_INICIO_JUGADOR && columna == COLUMNA_INICIO_JUGADOR) {
            return true;
        }

        boolean esEsquinaPoder =
                (fila == 1 && columna == 1)
                || (fila == 1 && columna == columnas - 2)
                || (fila == filas - 2 && columna == 1)
                || (fila == filas - 2 && columna == columnas - 2);

        return esEsquinaPoder;
    }

    private void crearMuro(int fila, int columna) {
        if (matriz[fila][columna] != 'X') {
            matriz[fila][columna] = 'X';
            muros.add(new Muro(fila, columna));
        }
    }

    private void agregarPuntos() {
        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                if (matriz[i][j] == '\0') {
                    matriz[i][j] = '.';
                    puntos.add(new Punto(i, j));
                }
            }
        }
    }

    private void agregarPoderes() {

        colocarPoder(1, 1, "salud");

        colocarPoder(
                1,
                columnas - 2,
                "congelar"
        );

        colocarPoder(
                filas - 2,
                1,
                "salud"
        );

        colocarPoder(
                filas - 2,
                columnas - 2,
                "congelar"
        );
    }

    private void colocarPoder(
            int fila,
            int columna,
            String tipo
    ) {

        if (matriz[fila][columna] == '\0') {

            matriz[fila][columna] = 'C';

            poderes.add(
                    new Poder(
                            fila,
                            columna,
                            tipo
                    )
            );
        }
    }

    public boolean esMovimientoValido(int fila, int columna) {
        if (fila < 0 || fila >= filas) {
            return false;
        }

        if (columna < 0 || columna >= columnas) {
            return false;
        }

        return matriz[fila][columna] != 'X';
    }

    public void dibujar(Graphics g) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int x = j * tamCelda;
                int y = i * tamCelda;

                switch (matriz[i][j]) {
                    case 'X':
                        g.drawImage(
                                imagenMuro,
                                x,
                                y,
                                tamCelda,
                                tamCelda,
                                null
                        );
                        break;

                    case 'C':

                        for (Poder poder : poderes) {

                            if (poder.getFila() == i
                                    && poder.getColumna() == j
                                    && poder.estaActivo()) {

                                if (poder.getTipo().equals("congelar")) {

                                    g.drawImage(
                                            imagenCongelar,
                                            x,
                                            y,
                                            tamCelda,
                                            tamCelda,
                                            null
                                    );

                                } else {

                                    g.drawImage(
                                            imagenPoder,
                                            x,
                                            y,
                                            tamCelda,
                                            tamCelda,
                                            null
                                    );

                                }

                            }

                        }

                        break;
                }
            }
        }

        for (Punto punto : puntos) {
            if (!punto.fueRecolectado()) {
                g.drawImage(
                        imagenPunto,
                        punto.getColumna() * tamCelda,
                        punto.getFila() * tamCelda,
                        tamCelda,
                        tamCelda,
                        null
                );
            }
        }
    }

    public int getTamCelda() {
        return tamCelda;
    }

    public boolean posicionLibre(int fila, int columna) {
        return esMovimientoValido(fila, columna);
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public ArrayList<Punto> getPuntos() {
        return puntos;
    }

    public ArrayList<Poder> getPoderes() {
        return poderes;
    }
}
