import java.util.ArrayList;
import java.awt.Rectangle;

public class Physics {
	//stores the current scene
	private ArrayList<Enemy> ListOfEnemies = new ArrayList<Enemy>();
	private ArrayList<Obstacle> ListOfObstacles = new ArrayList<Obstacle>();
	private ArrayList<pickups> ListOfPickups = new ArrayList<pickups>();
	private ArrayList<Projectile> ListofProjectiles = new ArrayList<Projectile>();
	
	private Player PlayerOne = new Player();
	private Map Map = new Map();
	private boolean Collision = false;
	private boolean OtherCollision = false;
	private boolean playerHit;
	
	private GameEngine Engine;
	
	public void Update(GameEngine Engine){
		this.Engine = Engine;
		this.ListOfEnemies = Engine.GetListOfEnemies();
		this.ListOfObstacles = Engine.GetListOfObstacles();
		this.ListofProjectiles = Engine.GetListOfProjectile();
		this.ListOfPickups = Engine.GetListOfPickups();
		this.PlayerOne = Engine.GetPlayer();
		this.Map = Engine.Getlevel();
	}
	
	//this method computes the collisions for a given enemy
	public boolean OtherCollisions(Enemy E) {
		playerHit = false;
		int i;
		OtherCollision = false;
		
		//collisions with other enemies
		Rectangle Bounds = new Rectangle(E.GetX()+E.GetDX(), E.GetY()+E.GetDY(), E.GetWidth(),E.GetHeight());
		for(i=0; i < ListOfEnemies.size(); i++) {
			Enemy e = ListOfEnemies.get(i);	
			if(e != E) {
				if(e.GetBounds().intersects(new Rectangle(Map.GetDeltaX()- 100, Map.GetDeltaY()-100,1124,868))) {	
					if(e.GetBounds().intersects(Bounds)) {
					OtherCollision = true;
					}
				}
			}
		}
		
		//collisions with player
		Rectangle PlayerBounds = PlayerOne.GetBounds();
		if(E.GetBounds().intersects(PlayerBounds)) {
			PlayerOne.TakeDamage(E.GetDamage());
			playerHit = true;
			E.Stop();
			OtherCollision =true;
		}
		
		//Collisions with projectiles
		for(i = 0; i <ListofProjectiles.size(); i++) {
			Projectile P = ListofProjectiles.get(i);
			Rectangle Rect = P.GetBounds();
				if(P.getExists()) {
					if(Bounds.intersects(Rect)) {
						E.TakeDamage((float) 0.3);
						P.setExists(false);
					}
				}
		}
		
		//Collisions with obstacles
		for(i=0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
			Rectangle Rect = O.GetBounds();
			if(Bounds.intersects(Rect)) {
			OtherCollision = true;
			}	
		}
		return OtherCollision;
	}

	//this method computes the collisions for the player give the change int x and change in y
	public boolean PlayerCollisions(int X, int Y) {
		Collision = false;
		playerHit = false;
		int i;
		Rectangle PlayerBounds = new Rectangle(PlayerOne.GetBounds().x + X, PlayerOne.GetBounds().y + Y, PlayerOne.GetBounds().width,PlayerOne.GetBounds().height);
		//Collisions with obstacles
		for(i=0; i < ListOfObstacles.size(); i++) {
			Obstacle O = ListOfObstacles.get(i);
					Rectangle Rect2 = O.GetSpecialBounds();
					//Special collisions
					if(PlayerBounds.intersects(Rect2)) {
						Engine.ObstacleAction(O.GetAciton());
					}
				
				Rectangle Rect3 = O.GetBounds();
				if(PlayerBounds.intersects(Rect3)) {
				Collision = true;
				}
		}
		
		//Collisions with enemies
		for(i=0; i < ListOfEnemies.size(); i++) {
			Enemy E = ListOfEnemies.get(i);		
				if(E.GetBounds().intersects(PlayerBounds)) {
				Collision = true;
				playerHit = true;
				}
				if(PlayerOne.GetIsAttacking()) {
					if(PlayerOne.getWeaponType().compareTo("ranged") == 0) {
						Engine.AddProjectile(true, PlayerOne.GetX()+15,PlayerOne.GetY()+20,10,10,PlayerOne.GetDirection());
					}
					else {
						if(PlayerOne.GetSpecialBounds().intersects(E.GetBounds())) {
							E.TakeDamage(PlayerOne.GetDamage(),"Melee");
						}
					}
				}
		}
		
		//Collisions with pickups
		for(i = 0; i < ListOfPickups.size(); i++) {
			int XDifference = Math.abs(ListOfPickups.get(i).GetX() - PlayerOne.GetX());
			int YDifference = Math.abs(ListOfPickups.get(i).GetY() - PlayerOne.GetY());
			if ((XDifference<32) && (YDifference<32)) {
				Engine.PickupItem(i);
			}
		}
		
		return Collision;
	}

	
	public boolean getPlayerHit() {
		return playerHit;
	}
}