
public class Character extends Entity{
	protected int Health;
	protected int Damage;
	protected int Speed;
	protected int AttSpeed;
	protected int MaxHealth;
	protected boolean Alive;
	protected boolean isAttacking;
	protected boolean Reloading = false;
	protected String Direction;
	
	Sprite currentSprite;
	Animation attackFront;
	Animation attackBack;
	Animation attackLeft;
	Animation attackRight;
	Animation walkFront;
	Animation walkBack;
	Animation walkLeft;
	Animation walkRight;
	Animation standFront;
	Animation standBack;
	Animation standLeft;
	Animation standRight;


	public void TakeDamage(int i, String s) {
		Health = Health - i;
	
	}
	
	//getters
	public int GetHealth() {
		return Health;
	}
	
	public int GetMaxHealth() {
		return MaxHealth;
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
	
	public boolean GetIsAttacking() {
		return isAttacking;
	}
	
	public String GetDirection() {
		return Direction;
	}
	//Setters
	public void SetIsAttacking(boolean b) {
		isAttacking = b;
	}
	
	public void SetHealth(int NewHealth) {
		Health = NewHealth;
	}
	
	public void SetMaxHealth(int NewMaxHealth) {
		MaxHealth = NewMaxHealth;
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