package Juego;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Random;


public class Tablero {

    private int filas;
    private int columnas;
    private int tamCelda=32;

    private char[][] matriz;

    private ArrayList<Muro> muros;
    private ArrayList<Punto> puntos;
    private ArrayList<Poder> poderes;


    private Image imagenMuro;
    private Image imagenPunto;
    private Image imagenPoder;


    public Tablero(int filas,int columnas){

        this.filas=filas;
        this.columnas=columnas;

        matriz=new char[filas][columnas];

        muros=new ArrayList<>();
        puntos=new ArrayList<>();
        poderes=new ArrayList<>();

        cargarImagenes();
        generarTablero();
    }


    private void cargarImagenes(){

        imagenMuro=new ImageIcon(
                getClass().getResource("/Juego/recursos/wall.png")
        ).getImage();

        imagenPunto=new ImageIcon(
                getClass().getResource("/Juego/recursos/powerFood1.png")
        ).getImage();

        imagenPoder=new ImageIcon(
                getClass().getResource("/Juego/recursos/cherry.png")
        ).getImage();
    }


    public void generarTablero(){

        agregarMuros();
        agregarPoderes();
        agregarPuntos();

    }


    private void agregarMuros(){

        Random random=new Random();

        // Bordes
        for(int i=0;i<filas;i++){

            for(int j=0;j<columnas;j++){

                if(i==0 || j==0 || i==filas-1 || j==columnas-1){

                    crearMuro(i,j);

                }
            }
        }


        // Muros internos
        for(int i=2;i<filas-2;i+=2){

            for(int j=2;j<columnas-2;j+=2){

                if(random.nextInt(100)<60){

                    crearMuro(i,j);

                    int direccion=random.nextInt(4);

                    switch(direccion){

                        case 0:
                            crearMuro(i-1,j);
                            break;

                        case 1:
                            crearMuro(i+1,j);
                            break;

                        case 2:
                            crearMuro(i,j-1);
                            break;

                        case 3:
                            crearMuro(i,j+1);
                            break;
                    }
                }
            }
        }
    }


    private void crearMuro(int fila,int columna){

        if(matriz[fila][columna]!='X'){

            matriz[fila][columna]='X';

            muros.add(new Muro(fila,columna));

        }
    }


    private void agregarPuntos(){

        for(int i=1;i<filas-1;i++){

            for(int j=1;j<columnas-1;j++){

                if(matriz[i][j]=='\0'){

                    matriz[i][j]='.';

                    puntos.add(new Punto(i,j));

                }
            }
        }
    }


    private void agregarPoderes(){

        colocarPoder(1,1);
        colocarPoder(1,columnas-2);
        colocarPoder(filas-2,1);
        colocarPoder(filas-2,columnas-2);

    }


    private void colocarPoder(int fila,int columna){

        if(matriz[fila][columna]=='\0'){

            matriz[fila][columna]='C';

            poderes.add(
                    new Poder(
                            fila,
                            columna,
                            "velocidad"
                    )
            );

        }

    }


    public boolean esMovimientoValido(int fila,int columna){

        if(fila<0 || fila>=filas)
            return false;

        if(columna<0 || columna>=columnas)
            return false;

        return matriz[fila][columna]!='X';

    }


    public void dibujar(Graphics g){

        for(int i=0;i<filas;i++){

            for(int j=0;j<columnas;j++){

                int x=j*tamCelda;
                int y=i*tamCelda;


                switch(matriz[i][j]){

                    case 'X':

                        g.drawImage(
                                imagenMuro,
                                x,y,
                                tamCelda,tamCelda,
                                null
                        );
                        break;





                    case 'C':

                        g.drawImage(
                                imagenPoder,
                                x,y,
                                tamCelda,tamCelda,
                                null
                        );
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


    public int getTamCelda(){

        return tamCelda;

    }

    public boolean posicionLibre(int fila, int columna) {
        return esMovimientoValido(fila, columna);
    }

    public int getFilas(){
        return filas;
    }

    public int getColumnas(){
        return columnas;
    }

    //metodo para obtener la lista de puntos
    public ArrayList<Punto> getPuntos() {
        return puntos;
    }

    public ArrayList<Poder> getPoderes(){

        return poderes;

    }
}