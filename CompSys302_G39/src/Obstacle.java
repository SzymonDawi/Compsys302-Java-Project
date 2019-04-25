
public class Obstacle extends Entity{
	public Obstacle( String s, int W,int H,int X, int Y,int Frames){
		Sprite= new Sprite(loadPlayer.loadSprite(s));
		SpriteList.add(Sprite.getSprite(0, 0, W,H));
		Animation = new Animation(SpriteList);
		this.W = W;
		this.H = H;
		this.X = X;
		this.Y = Y;
	}
}