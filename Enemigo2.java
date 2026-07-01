package proyectotallerdeprogramacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemigo2 {

    private int x, y;
    private int velocidad;
    private char DireccionActual;
    private final Color color = Color.PINK;
    private int targetX, targetY;
    
     public Enemigo2(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.velocidad = 5; 
        this.DireccionActual = 'A';
    }
     
     public void actualizar(Pacman pacman) {
        targetX = pacman.getX();
        targetY = pacman.getY();
        MoverHaciaObjetivo();
    }
     
     private void MoverHaciaObjetivo() {
        int dx = targetX - x;
        int dy = targetY - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            DireccionActual = (dx > 0) ? 'D' : 'A';
        } else {
            DireccionActual = (dy > 0) ? 'S' : 'W';
        }

        switch (DireccionActual) {
            case 'W' -> y -= velocidad;
            case 'S' -> y += velocidad;
            case 'A' -> x -= velocidad;
            case 'D' -> x += velocidad;
        }
    }
     
     public void dibujar(Graphics g) {
        g.setColor(color);
        //Cuerpo
        g.fillRoundRect(x, y, 24, 24, 10, 10); 
        g.setColor(Color.WHITE);
        //Ojos
        g.fillOval(x + 4, y + 6, 6, 6);
        g.fillOval(x + 14, y + 6, 6, 6);
    }

    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    public Rectangle getBounds() { 
        return new Rectangle(x, y, 24, 24); 
    }
}
