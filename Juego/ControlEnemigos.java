package Juego;

import java.awt.Graphics;
import java.util.ArrayList;

public class ControlEnemigos {

    // Lista de enemigos
    private ArrayList<Enemigo> enemigos;

    // Jugador para detectar ataques
    private Jugador jugador;

    private int contadorMovimiento = 0;
    private boolean congelados = false;
    private int tiempoCongelado = 0;

    public ControlEnemigos(Jugador jugador) {
        enemigos = new ArrayList<>();
        this.jugador = jugador;
    }

    // Agregar enemigos
    public void agregarEnemigo(Enemigo enemigo) {
        enemigos.add(enemigo);
    }

    // Actualizar movimiento de todos los enemigos
    public void moverEnemigos(Tablero tablero) {
        if(congelados){

            return;

        }
        contadorMovimiento++;

        // Blinky se mueve cada 3 ciclos
        if (contadorMovimiento % 3 != 0) {
            return;
        }

        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaActivo()) {
                enemigo.mover(tablero, jugador);
            }
        }
    }

    // Revisar si algún enemigo toca al jugador
    public void verificarColisiones() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaActivo()) {
                enemigo.verificarColision(jugador);
            }
        }
    }

    // Eliminar enemigos derrotados
    public void eliminarEnemigosInactivos() {
        enemigos.removeIf(enemigo -> !enemigo.estaActivo());
    }

    // Calcular movimientos
    public void generarMovimientos(Tablero tablero) {
        moverEnemigos(tablero);
    }

    // Dibujar enemigos
    public void dibujar(Graphics g, int tamañoCelda) {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaActivo()) {
                enemigo.dibujar(g, tamañoCelda);
            }
        }
    }
    //metodo congelar enemigos
    public void congelar(int tiempo){

        congelados = true;

        tiempoCongelado = tiempo;

    }
    public void actualizarCongelamiento(){

        if(congelados){

            tiempoCongelado--;

            if(tiempoCongelado <= 0){

                congelados = false;

            }

        }

    }
}