
public class Character extends Entity{
	protected int Health;
	protected int Damage;
	protected int Speed;
	protected boolean Alive;

	public int GetHealth() {
		return Health;
	}

	public int GetDamage() {
		return Damage;
	}
	
	public int GetSpeed() {
		return Speed;
	}
	
	public void SetHealth(int NewHealth) {
		Health = NewHealth;
	}
	
	public void SetDamage(int NewDamage) {
		Health = NewDamage;
	}
	
	public void SetSpeed(int NewSpeed) {
		Health = NewSpeed;
	}
	
}
