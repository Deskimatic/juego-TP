package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Juego extends JPanel implements KeyListener {

    private Tablero tablero;
    private Jugador jugador;
    private ControlEnemigos controlEnemigos;
    private Timer timer;
    private String direccionActual;
    private boolean juegoTerminado;



    public Juego(int filas, int columnas) {

        tablero = new Tablero(filas, columnas);

        jugador = new Jugador(
                "Pac-Man",
                1,
                2
        );
        juegoTerminado = false;
        direccionActual = "NINGUNA";

        controlEnemigos = new ControlEnemigos(jugador);
        Blinky blinky = crearBlinky();
        controlEnemigos.agregarEnemigo(blinky);
        Clyde clyde = crearClyde();
        controlEnemigos.agregarEnemigo(clyde);
        Inky inky = crearInky(blinky);
        controlEnemigos.agregarEnemigo(inky);
        Pinky pinky = crearPinky();
        controlEnemigos.agregarEnemigo(pinky);

        setPreferredSize(
                new Dimension(
                        columnas * tablero.getTamCelda(),
                        filas * tablero.getTamCelda()
                )
        );

        setBackground(Color.BLACK);

        addKeyListener(this);

        setFocusable(true);

        timer = new Timer(250, e -> {


            if(!juegoTerminado){


                // Mover Pac-Man

                if(!direccionActual.equals("NINGUNA")){

                    jugador.mover(
                            direccionActual,
                            tablero
                    );

                    verificarPuntos();

                    verificarPoderes();

                }


                // Mover enemigos

                controlEnemigos.moverEnemigos(tablero);


                // Revisar colisiones

                controlEnemigos.verificarColisiones();


                verificarEstadoJugador();

            }


            repaint();


        });


        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tablero.dibujar(g);
        jugador.dibujar(g, tablero.getTamCelda());
        controlEnemigos.dibujar(g, tablero.getTamCelda());
        g.setColor(Color.WHITE);

        g.drawString(
                "Vidas: " + jugador.getSalud(),
                10,
                20
        );

        g.drawString(
                "Puntaje: " + jugador.getPuntaje(),
                10,
                40
        );
        if(juegoTerminado){

            g.setColor(Color.RED);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            40
                    )
            );

            g.drawString(
                    "GAME OVER",
                    100,
                    300
            );

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(juegoTerminado){

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

    //Pac-Man recogió un punto?
    private void verificarPuntos() {

        for (Punto punto : tablero.getPuntos()) {

            if (punto.getFila() == jugador.getFila()
                    && punto.getColumna() == jugador.getColumna()
                    && !punto.fueRecolectado()) {

                jugador.recogerPunto(punto);
            }
        }
    }
    private void verificarPoderes(){

        for(Poder poder : tablero.getPoderes()){


            if(poder.estaActivo()
                    &&
                    poder.getFila() == jugador.getFila()
                    &&
                    poder.getColumna() == jugador.getColumna()){


                poder.activar(jugador);


            }

        }

    }
    private void verificarEstadoJugador(){

        if(!jugador.estaVivo()){

            juegoTerminado = true;

            timer.stop();

        }

    }

    private Blinky crearBlinky(){
        int fila;
        int columna;

        do{
            fila = (int)(Math.random()*tablero.getFilas());
            columna = (int)(Math.random()*tablero.getColumnas());

        }while(
                !tablero.posicionLibre(fila,columna)
                        ||
                        (fila == jugador.getFila()
                                &&
                                columna == jugador.getColumna())
        );
        return new Blinky(fila,columna);

    }
    private Clyde crearClyde(){

        Clyde clyde;

        do{

            int fila = (int)(Math.random() * tablero.getFilas());
            int columna = (int)(Math.random() * tablero.getColumnas());

            clyde = new Clyde(fila, columna);

        }while(!tablero.esMovimientoValido(
                clyde.getFila(),
                clyde.getColumna()
        ));

        return clyde;

    }
    private Inky crearInky(Blinky blinky){

        Inky inky;

        do{

            int fila = (int)(Math.random() * tablero.getFilas());

            int columna = (int)(Math.random() * tablero.getColumnas());

            inky = new Inky(
                    fila,
                    columna,
                    blinky
            );

        }while(
                !tablero.esMovimientoValido(
                        inky.getFila(),
                        inky.getColumna()
                )
        );

        return inky;

    }
    private Pinky crearPinky(){

        Pinky pinky;

        do{

            int fila = (int)(
                    Math.random()
                            * tablero.getFilas()
            );

            int columna = (int)(
                    Math.random()
                            * tablero.getColumnas()
            );

            pinky = new Pinky(
                    fila,
                    columna
            );

        }while(
                !tablero.esMovimientoValido(
                        pinky.getFila(),
                        pinky.getColumna()
                )
        );

        return pinky;

    }



    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}