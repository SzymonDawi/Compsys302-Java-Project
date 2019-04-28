
public class Enemy extends Character{
	protected boolean aggro;
	private int StopCounter = 0;
	private boolean Stop= false;
	protected String type;

	//pushes the enemy back when they are hit
	@Override
	public void TakeDamage(double i) {
		if(Direction.compareTo("Forward")==0) {
			Move(0,+20);
			Stop();
		}
		else if(Direction.compareTo("Backwards")==0) {
			Move(0,-20);
			Stop();
		}
		else if(Direction.compareTo("Left")==0) {
			Move(+20,0);
			Stop();
		}
		else if(Direction.compareTo("Right")==0) {
			Move(-20,0);
			Stop();
		}
		
		Health = Health - i;
		if (Health < 0) {
			Health = 0;	
		}
	}
	
	public void setAggro(boolean NewAggro) {
		aggro = NewAggro;
	}
	
	public void Stop() {
		StopCounter = 0;
		Stop = true;
	}
	
	public boolean getAggro() {
		return aggro;
	}
	
	public String getType() {
		return type;
	}
		
	//This computes the "Ai" of the enemies
	public void triangulatePlayer (int xTarget, int yTarget, int TickCount) {
		if (!aggro ) {
			return;
		}
		
		int Xdifference = X - xTarget;
		int Ydifference =  Y - yTarget;
		
		String movementPriority;
		
		if((Math.abs(Xdifference)< Math.abs(Ydifference)) && Math.abs(Xdifference)>Speed) {
			movementPriority = "x";
		} else if(Math.abs(Ydifference)>Speed){
			movementPriority = "y";
		}else if(Math.abs(Xdifference)>Speed) {	
			movementPriority = "x";
		} else {
			return;
		}
		
		if(StopCounter != 20) {
			StopCounter++;
		}
		else {	
			StopCounter = 0;
			Stop = false;
		}
		
		if(TickCount < 7 && !Stop) {
			if (movementPriority == "x") {
				if(Xdifference < 0) {
					SetDX(5);
					Direction = "Right";
				} else {
					SetDX(-5);
					Direction = "Left";
				}
				
			} else {
				if(Ydifference < 0) {
					SetDY(5);
					Direction = "Forward";
				} else {
					SetDY(-5);
					Direction = "Backwards";
				}
			}	
		}
	}
	
	//returns the correct animation to play
	public Animation getEnemyAnimation() {
		Animation temp = walkFront;
			if(!aggro) {
				switch(Direction) {
				case "Left":
					temp = standLeft;
				
					break;
			
				case "Backwards":
					temp = standBack;
					break;
			
				case "Forward":
					temp = standFront;
					break;
			
				case "Right":
					temp = standRight;
					break;
				}
			} else {
				switch(Direction) {
				case "Left":
					temp = walkLeft;
				
					break;
			
				case "Backwards":
					temp = walkBack;
					break;
			
				case "Forward":
					temp = walkFront;
					break;
			
				case "Right":
					temp = walkRight;
					break;
				}
			}
		
		return temp;
	}
	
	//Used for detecting when the player is in front of the enemy
	public boolean detectPlayer(int playerX, int playerY) {
		if (aggro) {
			return false;
		}
		int aggroBlockDistance = 9;
		int pixelDistance = 32* aggroBlockDistance;
		int Xdifference = X - playerX;
		int Ydifference =  Y - playerY;
		
		
		if(Direction == "Forwards" && Ydifference>0 && Ydifference < pixelDistance && (playerX>(X-32) && playerX<(X+32)) ) {
			setAggro(true);
			return true;
		}
		
		else if(Direction == "Backward" && Ydifference<0  && Ydifference > -pixelDistance && (playerX>(X-32) && playerX<(X+32))) {
			setAggro(true);
			return true;
		}
		
		else if(Direction == "Left" && Xdifference>0  && Xdifference < pixelDistance && (playerY>(Y-32) && playerY<(Y+32))) {
			setAggro(true);
			return true;
		}
		
		else if(Direction == "Right" && Xdifference<0  && Xdifference > -pixelDistance && (playerY>(Y-32) && playerY<(Y+32))) {
			setAggro(true);
			return true;
		}
		return false;
	}
}