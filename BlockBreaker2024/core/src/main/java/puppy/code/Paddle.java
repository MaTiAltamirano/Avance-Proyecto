package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject {
    /*private int x = 20;
    private int y = 20;
    private int width = 100;
    private int height = 10;
    */
    public Paddle(int x, int y, int width, int height) {
    	super(x, y, width, height);
    }
    
    /*
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	*/

	public void draw(ShapeRenderer shape){
		shape.setColor(Color.BLUE);
        int newX = x;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) newX = x - 15;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) newX = x + 15;

        if (newX > 0 && newX + width < Gdx.graphics.getWidth()) {
            x = newX;
        }
        shape.rect(x, y, width, height);
    }
    
    
}
