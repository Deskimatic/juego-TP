package Juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Juego extends JPanel implements KeyListener {

    private Tablero tablero;
    private Jugador jugador;
    private ControlEnemigos controlEnemigos;
    private Timer timer;
    private String direccionActual;
    private boolean juegoTerminado;
    private boolean victoria;

    // filasJugables / columnasJugables: el área de movimiento que pidió
    // el usuario (sin contar el borde, que Tablero agrega solo).
    // muroPersonalizado / cantidadMuros: si el usuario eligió personalizar
    // la cantidad de muros, o si se usa la configuración original.
    public Juego(
            int filasJugables,
            int columnasJugables,
            boolean muroPersonalizado,
            int cantidadMuros
    ) {
        tablero = new Tablero(
                filasJugables,
                columnasJugables,
                muroPersonalizado,
                cantidadMuros
        );

        jugador = new Jugador("Pac-Man", 1, 2);

        juegoTerminado = false;
        victoria = false;
        direccionActual = "NINGUNA";

        controlEnemigos = new ControlEnemigos(jugador);

        int cantidadFantasmas = calcularCantidadFantasmas(
                filasJugables, columnasJugables
        );

        agregarFantasmas(cantidadFantasmas);

        // El tamaño de la ventana usa las dimensiones REALES del tablero
        // (con borde incluido), no las que ingresó el usuario.
        setPreferredSize(
                new Dimension(
                        tablero.getColumnas() * tablero.getTamCelda(),
                        tablero.getFilas() * tablero.getTamCelda()
                )
        );

        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(250, e -> {
            if (!juegoTerminado) {
                // Mover Pac-Man
                if (!direccionActual.equals("NINGUNA")) {
                    jugador.mover(direccionActual, tablero);
                    verificarPuntos();
                    verificarPoderes();
                }

                // Mover enemigos
                controlEnemigos.moverEnemigos(tablero);
                // congelar enemigos
                controlEnemigos.actualizarCongelamiento();

                // Revisar colisiones
                controlEnemigos.verificarColisiones();
                verificarEstadoJugador();
                verificarVictoria();
            }

            repaint();
        });

        timer.start();
    }

    // Decide cuántos fantasmas aparecen según el lado más grande del
    // área jugable que ingresó el usuario:
    // <=5 -> 1 fantasma | <=10 -> 2 | <=15 -> 3 | >15 -> 4
    private int calcularCantidadFantasmas(int filasJugables, int columnasJugables) {
        int tamanoReferencia = Math.max(filasJugables, columnasJugables);

        if (tamanoReferencia <= 5) {
            return 1;
        } else if (tamanoReferencia <= 10) {
            return 2;
        } else if (tamanoReferencia <= 15) {
            return 3;
        } else {
            return 4;
        }
    }

    // Agrega los fantasmas en orden: Blinky, Clyde, Inky, Pinky,
    // deteniéndose apenas se alcanza la cantidad indicada.
    private void agregarFantasmas(int cantidadFantasmas) {
        Blinky blinky = null;

        if (cantidadFantasmas >= 1) {
            blinky = crearBlinky();
            controlEnemigos.agregarEnemigo(blinky);
        }

        if (cantidadFantasmas >= 2) {
            Clyde clyde = crearClyde();
            controlEnemigos.agregarEnemigo(clyde);
        }

        if (cantidadFantasmas >= 3) {
            Inky inky = crearInky(blinky);
            controlEnemigos.agregarEnemigo(inky);
        }

        if (cantidadFantasmas >= 4) {
            Pinky pinky = crearPinky();
            controlEnemigos.agregarEnemigo(pinky);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        tablero.dibujar(g);
        jugador.dibujar(g, tablero.getTamCelda());
        controlEnemigos.dibujar(g, tablero.getTamCelda());

        g.setColor(Color.WHITE);

        g.drawString("Vidas: " + jugador.getSalud(), 10, 20);
        g.drawString("Puntaje: " + jugador.getPuntaje(), 10, 40);

        if (juegoTerminado && !victoria) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 100, 300);
        }

        if (victoria) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("VICTORY", 120, 300);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                direccionActual = "ARRIBA";
                break;

            case KeyEvent.VK_S:
                direccionActual = "ABAJO";
                break;

            case KeyEvent.VK_A:
                direccionActual = "IZQUIERDA";
                break;

            case KeyEvent.VK_D:
                direccionActual = "DERECHA";
                break;
        }
    }

    // Pac-Man recogió un punto
    private void verificarPuntos() {
        for (Punto punto : tablero.getPuntos()) {
            if (punto.getFila() == jugador.getFila()
                    && punto.getColumna() == jugador.getColumna()
                    && !punto.fueRecolectado()) {
                jugador.recogerPunto(punto);
            }
        }
    }

    private void verificarPoderes() {
        for (Poder poder : tablero.getPoderes()) {
            if (poder.estaActivo()
                    && poder.getFila() == jugador.getFila()
                    && poder.getColumna() == jugador.getColumna()) {
                poder.activar(jugador, controlEnemigos);
            }
        }
    }

    private void verificarEstadoJugador() {
        if (!jugador.estaVivo()) {
            juegoTerminado = true;
            timer.stop();
        }
    }

    private void verificarVictoria() {
        for (Punto punto : tablero.getPuntos()) {
            if (!punto.fueRecolectado()) {
                return;
            }
        }

        victoria = true;
        juegoTerminado = true;
        timer.stop();
    }

    private Blinky crearBlinky() {
        int fila;
        int columna;

        do {
            fila = (int) (Math.random() * tablero.getFilas());
            columna = (int) (Math.random() * tablero.getColumnas());

        } while (!tablero.posicionLibre(fila, columna)
                || (fila == jugador.getFila()
                && columna == jugador.getColumna()));

        return new Blinky(fila, columna);
    }

    private Clyde crearClyde() {
        Clyde clyde;

        do {
            int fila = (int) (Math.random() * tablero.getFilas());
            int columna = (int) (Math.random() * tablero.getColumnas());

            clyde = new Clyde(fila, columna);

        } while (!tablero.esMovimientoValido(
                clyde.getFila(),
                clyde.getColumna()
        ));

        return clyde;
    }

    private Inky crearInky(Blinky blinky) {
        Inky inky;

        do {
            int fila = (int) (Math.random() * tablero.getFilas());
            int columna = (int) (Math.random() * tablero.getColumnas());

            inky = new Inky(fila, columna, blinky);

        } while (!tablero.esMovimientoValido(
                inky.getFila(),
                inky.getColumna()
        ));

        return inky;
    }

    private Pinky crearPinky() {
        Pinky pinky;

        do {
            int fila = (int) (Math.random() * tablero.getFilas());
            int columna = (int) (Math.random() * tablero.getColumnas());

            pinky = new Pinky(fila, columna);

        } while (!tablero.esMovimientoValido(
                pinky.getFila(),
                pinky.getColumna()
        ));

        return pinky;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
