package Juego;

public class Punto {

    private int fila;
    private int columna;
    private int valor;
    private boolean recolectado;

    public Punto(int fila, int columna) {

        this.fila = fila;
        this.columna = columna;

        valor = 10;
        recolectado = false;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int obtenerValor() {
        return valor;
    }

    public boolean fueRecolectado() {

        return recolectado;
    }

    public void recolectar() {

        recolectado = true;
    }


}