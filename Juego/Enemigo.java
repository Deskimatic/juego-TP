package Juego;

import java.awt.Graphics;
import java.awt.Image;

public class Enemigo {

    
    protected String tipo;
    protected int fila;
    protected int columna;
    protected int daño;
    protected boolean activo;

    
    protected Image imagen;

    public Enemigo(String tipo, int fila, int columna, int daño, Image imagen) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.daño = daño;
        this.imagen = imagen;
        this.activo = true;
    }

    
    public void mover(Tablero tablero, Jugador jugador) {
        
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    
    public void atacar(Jugador jugador) {
        jugador.recibirDaño(daño);
    }

    
    public boolean verificarColision(Jugador jugador) {
        if (fila == jugador.getFila()
                && columna == jugador.getColumna()) {
            atacar(jugador);
            return true;
        }

        return false;
    }

    
    public void mostrarEstado() {
        System.out.println(
                "Enemigo: " + tipo
                        + " Posición: " + fila + ", " + columna
                        + " Activo: " + activo
        );
    }

    
    public void dibujar(Graphics g, int tamañoCelda) {
        g.drawImage(
                imagen,
                columna * tamañoCelda,
                fila * tamañoCelda,
                tamañoCelda,
                tamañoCelda,
                null
        );
    }

    public boolean estaActivo() {
        return activo;
    }

    public void eliminar() {
        activo = false;
    }
}
