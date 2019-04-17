
public class Player extends Character{
	public Player() {
		Health = 100;
		Damage = 100;
		Alive = true;
		X = 50;
		Y = 50;
		//Sprite.LoadSprite("Player1_Walk_Forward.png");
	}
	
//	public Player(int Player){
//		Health = 100;
//		Damage = 100;
//		Alive = true;
//		X = 50;
//		Y = 50;
//		
//	}
//	
	
	public void Move(int DeltaX,int DeltaY) {
		X += DeltaX;
		Y += DeltaY;
		//System.out.println("X:" + X);
		//System.out.println("Y:" + Y);
	}
	
	public void TakeDamage(int i) {
		Health = Health - i;
	}
	
	public void Kill() {
		Alive = false;
	}
	
	public boolean IsAlive() {
		return Alive;
	}
}
