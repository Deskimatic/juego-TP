package Juego;
import javax.swing.*;
import java.awt.*;

public class Jugador {

    // Atributos

    private String nombre;
    private int fila;
    private int columna;
    private int salud;
    private int puntaje;
    private int velocidad;
    private boolean poderActivo;
    private String direccion;

    private Image imagenArriba;
    private Image imagenAbajo;
    private Image imagenIzquierda;
    private Image imagenDerecha;

    private Image imagenActual;

    // Constructor

    public Jugador(String nombre, int fila, int columna) {

        this.nombre = nombre;
        this.fila = fila;
        this.columna = columna;

        salud = 5;
        puntaje = 0;
        velocidad = 1;
        poderActivo = false;
        direccion = "DERECHA";

        imagenArriba = new ImageIcon(
                getClass().getResource(
                        "/Juego/recursos/pacmanUp.png"
                )
        ).getImage();

        imagenAbajo = new ImageIcon(
                getClass().getResource(
                        "/Juego/recursos/pacmanDown.png"
                )
        ).getImage();

        imagenIzquierda = new ImageIcon(
                getClass().getResource(
                        "/Juego/recursos/cherry.png"
                )
        ).getImage();

        imagenDerecha = new ImageIcon(
                getClass().getResource(
                        "/Juego/recursos/pacmanRight.png"
                )
        ).getImage();

        imagenActual = imagenDerecha;
    }

    // Movimiento

    public void mover(String direccion, Tablero tablero) {
        this.direccion = direccion;

        int nuevaFila = fila;
        int nuevaColumna = columna;

        switch (direccion.toUpperCase()) {

            case "ARRIBA":

                nuevaFila -= velocidad;
                imagenActual = imagenArriba;
                break;

            case "ABAJO":

                nuevaFila += velocidad;
                imagenActual = imagenAbajo;
                break;

            case "IZQUIERDA":

                nuevaColumna -= velocidad;
                imagenActual = imagenIzquierda;
                break;

            case "DERECHA":

                nuevaColumna += velocidad;
                imagenActual = imagenDerecha;
                break;
        }

        if (tablero.esMovimientoValido(
                nuevaFila,
                nuevaColumna
        )) {

            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }

    public void dibujar(Graphics g, int tamCelda) {

        int x = columna * tamCelda;

        int y = fila * tamCelda;

        g.drawImage(
                imagenActual,
                x,
                y,
                tamCelda,
                tamCelda,
                null
        );
    }

    // Recoger puntos

    public void recogerPunto(Punto punto) {

        if (!punto.fueRecolectado()) {

            puntaje += punto.obtenerValor();

            punto.recolectar();
        }
    }

    // Recibir daño

    public void recibirDaño(int cantidad) {
        System.out.println("Pac-Man perdió una vida. Vidas restantes: " + salud);

        salud -= cantidad;

        if (salud < 0) {

            salud = 0;
        }
    }

    // Usar poder

    public void usarPoder(Poder poder) {

        poder.activar(this);

        poderActivo = true;
    }

    // Verificar vida

    public boolean estaVivo() {

        return salud > 0;
    }

    // Mostrar estado

    public void mostrarEstado() {

        System.out.println("Jugador: " + nombre);

        System.out.println(
                "Posición: (" +
                        fila +
                        ", " +
                        columna +
                        ")"
        );

        System.out.println(
                "Salud: " + salud
        );

        System.out.println(
                "Puntaje: " + puntaje
        );

        System.out.println(
                "Velocidad: " + velocidad
        );

        System.out.println(
                "Poder activo: " + poderActivo
        );
    }

    // Getters y setters

    public String getNombre() {

        return nombre;
    }

    public int getFila() {

        return fila;
    }

    public int getColumna() {

        return columna;
    }

    public int getSalud() {

        return salud;
    }

    public int getPuntaje() {

        return puntaje;
    }

    public int getVelocidad() {

        return velocidad;
    }

    public boolean isPoderActivo() {

        return poderActivo;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void velocidadNormal(){

        velocidad = 1;

    }

    public void setSalud(int salud) {

        this.salud = salud;
    }

    public String getDireccion(){

        return direccion;

    }
}