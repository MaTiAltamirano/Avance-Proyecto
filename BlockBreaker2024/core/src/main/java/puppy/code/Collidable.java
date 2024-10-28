package puppy.code;

public interface Collidable {
	
	
    boolean collidesWith(GameObject obj);
    
    
    
    void onCollision(GameObject obj);
    
}