import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	
	private ArrayList<BufferedImage> frames;
	
	private long speed, previousTime;
	private int currentFrame, pausedFrame;
	private volatile boolean running = false;
	
	public BufferedImage Sprite;
	
	public Animation(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}
	
	public void setSpeed(long speed) {
		this.speed = speed;
	}
	
	public void start() {
		running = true;
		previousTime = 0;
		pausedFrame = 0;
		currentFrame = 0;
	}
	
	public void pause() {
		pausedFrame = currentFrame;
		running = false;
	}
	
	public void stop() {
	running = false;
	previousTime = 0;
	pausedFrame = 0;
	currentFrame = 0;
	}
	
	public void resume() {
	currentFrame = pausedFrame;
	running = true;
	}
	
	public void update(long time) {
		while (running) {
			if (time - previousTime >=speed) {
				currentFrame++;
				try {
					Sprite = frames.get(currentFrame);
				} catch(IndexOutOfBoundsException e) {
					currentFrame = 0;
					Sprite = frames.get(currentFrame);
				}
				previousTime = time;
			}
		}
	}
}
