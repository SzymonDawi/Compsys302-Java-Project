
public class MeleeEnemy extends Enemy{
	int Health;
	int Damage;
	
	
	MeleeEnemy() {
		Health = 100;
		Damage = 20;
	}
	
	//getters
	public int GetHealth() {
		return Health;
	}
	
	public int GetDamage() {
		return Damage;
	}
	
	//public int GetX() {
		//return Position[0];
	//}
	
	//public int GetY() {
		//return Position[1];
	//}
	
	//setters
	public void SetHealth(int NewHealth) {
		Health = NewHealth;
	}
	
	//public void SetPosition(int x,int y) {
		//Position[0] = x;
		//Position[1] = y;
	//}
}
