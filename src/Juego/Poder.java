package Juego;
import javax.swing.Timer;
public class Poder {

    private int fila;
    private int columna;

    private String tipo;

    private int duracion;

    private boolean activo;


    public Poder(int fila, int columna, String tipo) {

        this.fila = fila;
        this.columna = columna;

        this.tipo = tipo;

        activo = true;

        switch (tipo) {

            case "velocidad":

                duracion = 5;

                break;

            case "congelar":

                duracion = 4;

                break;

            case "salud":

                duracion = 0;

                break;

            default:

                duracion = 3;
        }
    }


    public void activar(Jugador jugador) {

        if (!activo) {

            return;
        }

        switch (tipo) {

            case "velocidad":

                jugador.setVelocidad(
                        jugador.getVelocidad() + 1
                );


                Timer timer = new Timer(
                        duracion * 1000,
                        e -> {

                            jugador.velocidadNormal();

                        }
                );


                timer.setRepeats(false);

                timer.start();


                break;


            case "congelar":

                System.out.println(
                        "Los enemigos están congelados"
                );

                break;


            case "salud":

                jugador.setSalud(
                        jugador.getSalud() + 20
                );

                break;
        }

        activo = false;
    }


    public String descripcion() {

        switch (tipo) {

            case "velocidad":

                return "Aumenta la velocidad del jugador";

            case "congelar":

                return "Congela a los enemigos";

            case "salud":

                return "Recupera salud";

            default:

                return "Poder desconocido";
        }
    }


    public int getFila() {

        return fila;
    }

    public int getColumna() {

        return columna;
    }

    public int getDuracion() {

        return duracion;
    }

    public boolean estaActivo() {

        return activo;
    }

}