package Juego;

public class Poder {

    private int fila;
    private int columna;
    private String tipo;
    private int duracion;
    private boolean activo;
    private boolean congelados = false;
    private int tiempoCongelado = 0;

    public Poder(int fila, int columna, String tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        activo = true;

        switch (tipo) {
            case "velocidad":

            case "congelar":

            case "salud":
                duracion = 0;
                break;

            default:
                duracion = 3;
                break;
        }
    }

    public void activar(
            Jugador jugador,
            ControlEnemigos controlEnemigos
    ) {
        if (!activo) {
            return;
        }

        switch (tipo) {
            case "velocidad":
                jugador.setVelocidad(jugador.getVelocidad() + 1);
                break;

            case "congelar":
                controlEnemigos.congelar(20);
                break;

            case "salud":
                jugador.setSalud(jugador.getSalud() + 1);
                break;
        }

        activo = false;
    }

    public String descripcion() {
        switch (tipo) {
            case "velocidad":
                return "Aumenta la velocidad";

            case "congelar":
                return "Congela a los enemigos";

            case "salud":
                return "Recupera salud";

            default:
                return "Poder desconocido";
        }
    }
    public String getTipo() {

        return tipo;

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