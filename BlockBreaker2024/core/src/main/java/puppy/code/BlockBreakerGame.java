package puppy.code;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle paddle;
	private ArrayList<Block> blocks = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;
    
		@Override
		public void create () {	
			camera = new OrthographicCamera();
		    camera.setToOrtho(false, 800, 480);
		    batch = new SpriteBatch();
		    font = new BitmapFont();
		    font.getData().setScale(3, 2);
		    nivel = 1;
		    crearBloques(2+nivel);
			
		    shape = new ShapeRenderer();
		    ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 6, 90, true);
		    paddle = new Paddle(Gdx.graphics.getWidth()/2-50,40,100,10);
		    vidas = 3;
		    puntaje = 0;    
		}
		public void crearBloques(int filas) {
			blocks.clear();
			int blockWidth = 70;
		    int blockHeight = 26;
		    int y = Gdx.graphics.getHeight();
		    for (int cont = 0; cont<filas; cont++ ) {
		    	y -= blockHeight+10;
		    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
		            blocks.add(new Block(x, y, blockWidth, blockHeight));
		        }
		    }
		}
		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
			batch.end();
		}	
		
		@Override
		public void render () {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
	        shape.begin(ShapeRenderer.ShapeType.Filled);
	        paddle.draw(shape);
	        // monitorear inicio del juego
	        if (ball.estaQuieto()) {
	        	ball.setXY(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
	        		ball.setEstaQuieto(false);
	        	}
	        }else ball.update();
	        
	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
	        	vidas--;
	        	//nivel = 1;
	        	ball = new PingBall(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11, 10, 6, 90, true);
	        }
	        // verificar game over
	        if (vidas<=0) {
	        	resetGame();   	
	        }
	        // verificar si el nivel se terminó
	        if (blocks.isEmpty()) {
	        	nivel++;
	        	crearBloques(2+nivel);
	        	ball = new PingBall(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11, 10, 6, 10, true);
	        }    	
	        //dibujar bloques y ver colisiones
	        for (Block block : blocks) {
	            block.draw(shape);
	            if (ball.collidesWith(block)) {
	                ball.onCollision(block);
	                block.onCollision(ball);
	                if (block.destroyed) {
	                    puntaje++;
	                }
	            }
	        }
	        
	        blocks.removeIf(block -> block.destroyed);
	        
	        //si pelota colisiona con paddle
	        if (ball.collidesWith(paddle)) {
	            ball.onCollision(paddle);
	        }
	        
	        ball.draw(shape);
	        shape.end();
	        dibujaTextos();
		}
		
		private void resetGame() {
			vidas = 3;
			nivel = 1;
			puntaje = 0;
			crearBloques(2+nivel);
		}
		
		@Override
		public void dispose () {
			batch.dispose();
	        font.dispose();
	        shape.dispose();
		}
}
