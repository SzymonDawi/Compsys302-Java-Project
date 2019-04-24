import java.util.ArrayList;
import java.awt.Color;
import java.awt.Rectangle;

public class Physics {
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private ArrayList<pickups> ListOfPickups = new ArrayList<pickups>();
	private Player PlayerOne = new Player();
	private Map Map = new Map();
	private Boolean Collision = false;
	private GameEngine Engine;
	
	public Physics(GameEngine Engine){
		this.Engine = Engine;
		this.ListOfEnemies = Engine.GetListOfEnemies();
		this.ListOfObstacles = Engine.GetListOfObstacles();
		this.ListOfPickups = Engine.GetListOfPickups();
		this.PlayerOne = Engine.GetPlayer();
		this.Map = Engine.Getlevel();
	}

	public Boolean PlayerCollisions(int X, int Y) {
		Collision = false;
		Rectangle PlayerBounds = new Rectangle(PlayerOne.GetX() + X, PlayerOne.GetY() + Y, 32,32);
		for(int i=0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
			Rectangle Rect2 = O.GetBounds();
			if(PlayerBounds.intersects(Rect2)) {
				Collision = true;
			}
		}
		
		for(int i = 0; i < ListOfPickups.size(); i++) {
			int XDifference = Math.abs(ListOfPickups.get(i).GetX() - PlayerOne.GetX());
			int YDifference = Math.abs(ListOfPickups.get(i).GetY() - PlayerOne.GetY());
			if ((XDifference<32) && (YDifference<32)) {
				Engine.PickupItem(i);
			}
		}
		
		
		return Collision;
	}
}
