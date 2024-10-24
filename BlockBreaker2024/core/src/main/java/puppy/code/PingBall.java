/*package puppy.code;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall {
	    private int x;
	    private int y;
	    private int size;
	    private int xSpeed;
	    private int ySpeed;
	    private Color color = Color.WHITE;
	    private boolean estaQuieto;
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        this.x = x;
	        this.y = y;
	        this.size = size;
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
	    }
	    
	    public boolean estaQuieto() {
	    	return estaQuieto;
	    }
	    public void setEstaQuieto(boolean bb) {
	    	estaQuieto=bb;
	    }
	    public void setXY(int x, int y) {
	    	this.x = x;
	        this.y = y;
	    }
	    public int getY() {return y;}
	    
	    public void draw(ShapeRenderer shape){
	        shape.setColor(color);
	        shape.circle(x, y, size);
	    }
	    
	    public void update() {
	    	if (estaQuieto) return;
	        x += xSpeed;
	        y += ySpeed;
	        if (x-size < 0 || x+size > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }
	        if (y+size > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }
	    
	    public void checkCollision(Paddle paddle) {
	        if(collidesWith(paddle)){
	            color = Color.GREEN;
	            ySpeed = -ySpeed;
	            
	        }
	        else{
	            color = Color.WHITE;
	        }
	    }
	    private boolean collidesWith(Paddle pp) {

	    	boolean intersectaX = (pp.getX() + pp.getWidth() >= x-size) && (pp.getX() <= x+size);
	        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-size) && (pp.getY() <= y+size);		
	    	return intersectaX && intersectaY;
	    }
	    
	    public void checkCollision(Block block) {
	        if(collidesWith(block)){
	            ySpeed = - ySpeed;
	            block.destroyed = true;
	        }
	    }
	    private boolean collidesWith(Block bb) {

	    	boolean intersectaX = (bb.x + bb.width >= x-size) && (bb.x <= x+size);
	        boolean intersectaY = (bb.y + bb.height >= y-size) && (bb.y <= y+size);		
	    	return intersectaX && intersectaY;
	    }
	    
	}
*/
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall {
    private int x;
    private int y;
    private int size;
    private double speed;
    private double direction; // Dirección en grados (0-360)
    private Color color = Color.WHITE;
    private boolean estaQuieto;

    public PingBall(int x, int y, int size, double speed, double direction, boolean iniciaQuieto) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.direction = direction; // Inicializa la dirección
        estaQuieto = iniciaQuieto;
    }

    public boolean estaQuieto() {
        return estaQuieto;
    }

    public void setEstaQuieto(boolean bb) {
        estaQuieto = bb;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, size);
    }

    public void update() {
        if (estaQuieto) return;

        // Calcula la velocidad en X y Y usando la dirección
        int xSpeed = (int) (speed * Math.cos(Math.toRadians(direction)));
        int ySpeed = (int) (speed * Math.sin(Math.toRadians(direction)));

        x += xSpeed;
        y += ySpeed;

        // Verifica si choca con los bordes de la pantalla
        if (x - size < 0 || x + size > Gdx.graphics.getWidth()) {
            direction = 180 - direction; // Revertir dirección horizontal
        }
        if (y + size > Gdx.graphics.getHeight()) {
            direction = -direction; // Revertir dirección vertical
        }
    }

    public void checkCollision(Paddle paddle) {
        if (collidesWith(paddle)) {
            color = Color.GREEN;
            
            // Cambia la dirección aleatoria en un rango de -45 a 45 grados
            double randomAngle = (Math.random() * 90) - 45; // Genera un ángulo aleatorio
            direction = (direction + randomAngle) % 360; // Ajusta la dirección

            // Asegúrate de que ySpeed siempre esté hacia arriba
            if (direction > 180 || direction < 0) {
                direction -= 180; // Mantiene la dirección hacia arriba
            }
        } else {
            color = Color.WHITE;
        }
    }

    private boolean collidesWith(Paddle pp) {
        boolean intersectaX = (pp.getX() + pp.getWidth() >= x - size) && (pp.getX() <= x + size);
        boolean intersectaY = (pp.getY() + pp.getHeight() >= y - size) && (pp.getY() <= y + size);
        return intersectaX && intersectaY;
    }

    public void checkCollision(Block block) {
        if (collidesWith(block)) {
            direction = -direction; // Rebote vertical al chocar con el bloque
            block.destroyed = true; // Marcar bloque como destruido
        }
    }

    private boolean collidesWith(Block bb) {
        boolean intersectaX = (bb.x + bb.width >= x - size) && (bb.x <= x + size);
        boolean intersectaY = (bb.y + bb.height >= y - size) && (bb.y <= y + size);
        return intersectaX && intersectaY;
    }
}
