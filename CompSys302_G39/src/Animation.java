import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	//this holds the animation frames
	private ArrayList<BufferedImage> frame;
	
	private long speed, previousTime;
	private int currentFrame, pausedFrame;
	private volatile boolean running = false;
	
	public BufferedImage Sprite;

	public Animation(ArrayList<BufferedImage> newFrame) {
		frame = newFrame;
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
	
	//updates the frame if enough time has passed
	public void update(long time) {
		if (running) {
			if (time - previousTime >=speed) {
				currentFrame++;
				try {
					Sprite = frame.get(currentFrame);
				} catch(IndexOutOfBoundsException e) {
					currentFrame = 0;
					Sprite = frame.get(currentFrame);
				}
				previousTime = time;
			}
		}
	}
}
