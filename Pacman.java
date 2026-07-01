package proyectotallerdeprogramacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Pacman {

    //direccion
    public static final char Arriba = 'W';
    public static final char Abajo = 'S';
    public static final char Izquierda = 'A';
    public static final char Derecha = 'D';
    //atributos  
    private int x, y;
    private int velocidad;
    private char DireccionActual;
    private char DireccionDeseada;
    private int vidas;
    private boolean Ulti;
    private final Color color = Color.YELLOW;
    //constructor 
    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        velocidad = 5;
        vidas = 3;
        Ulti = false;
        DireccionActual = Derecha;
        DireccionDeseada = Derecha;
    }

    //cambio de direccion
    public void setDireccion(char direccion) {
        DireccionDeseada = direccion;
    }

    public void movimiento() {
        DireccionActual = DireccionDeseada;

        switch (DireccionActual) {

            case Arriba ->
                y -= velocidad;

            case Abajo ->
                y += velocidad;

            case Izquierda ->
                x -= velocidad;

            case Derecha ->
                x += velocidad;
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(color);

        int anguloInicio = switch (DireccionActual) {
            case Derecha   -> 30;
            case Izquierda -> 210;
            case Arriba    -> 120;
            case Abajo     -> 300;
            default -> 30;
        };

        g.fillArc(x, y, 24, 24, anguloInicio, 300);
    }
    
    //getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getVidas() { 
        return vidas;
    }

    public char getDireccionActual() { 
        return DireccionActual;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 24, 24);
    }
    
        public class juego implements KeyListener {

        private final Pacman pacman;

        public juego() {
            pacman = new Pacman(100, 100);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            char tecla = Character.toUpperCase(e.getKeyChar());
            switch (tecla) {

                case 'W':
                    pacman.setDireccion(Pacman.Arriba);
                    break;
                case 'S':
                    pacman.setDireccion(Pacman.Abajo);
                    break;
                case 'A':
                    pacman.setDireccion(Pacman.Izquierda);
                    break;
                case 'D':
                    pacman.setDireccion(Pacman.Derecha);
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

}

