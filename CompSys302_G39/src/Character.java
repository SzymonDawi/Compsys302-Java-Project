
public class Character extends Entity{
	protected int Health;
	protected int Damage;
	protected int Speed;
	protected int AttSpeed;
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
	
	public int GetAttSpeed() {
		return AttSpeed;
	}
	
	public void SetHealth(int NewHealth) {
		Health = NewHealth;
	}
	
	public void SetDamage(int NewDamage) {
		Damage = NewDamage;
	}
	
	public void SetSpeed(int NewSpeed) {
		Speed = NewSpeed;
	}
	
	public void SetAttSpeed(int NewAttSpeed) {
		AttSpeed = NewAttSpeed;
	}
	
}
