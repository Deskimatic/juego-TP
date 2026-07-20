package Juego;

public class Muro {


    private int fila;

    private int columna;



    public Muro(int fila,int columna){

        this.fila=fila;

        this.columna=columna;

    }



    public int getFila(){

        return fila;

    }



    public int getColumna(){

        return columna;

    }



    public String obtenerPosicion(){

        return "(" + fila + "," + columna + ")";

    }

}