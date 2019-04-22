import java.util.ArrayList;
import java.awt.Color;
import java.awt.Rectangle;

public class Physics {
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private Player PlayerOne = new Player();
	private Map Map = new Map();
	private Boolean Collision = false;
	
	public Physics(ArrayList<Enemy> ListOfEnemies,ArrayList<Obstacle> ListOfObstacles,Player PlayerOne, Map Map){
		this.ListOfEnemies = ListOfEnemies;
		this.ListOfObstacles = ListOfObstacles;
		this.PlayerOne = PlayerOne;
		this.Map = Map;
	}

	public Boolean PlayerCollisions(int X, int Y) {
		Collision = false;
		Rectangle PlayerBounds = new Rectangle(PlayerOne.GetX() + X, PlayerOne.GetY() + Y, 10,10);//PlayerOne.GetBounds();
		for(int i=0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
			Rectangle Rect2 = O.GetBounds();
			if(PlayerBounds.intersects(Rect2)) {
				Collision = true;
			}
		}
		return Collision;
	}
}
