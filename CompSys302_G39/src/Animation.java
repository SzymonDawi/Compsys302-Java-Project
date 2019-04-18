import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	
	private ArrayList<BufferedImage> frames;
	
	private long speed, timeBefore, previousTime;
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
	}
	
	public void pause() {
		
	}
	public void stop() {
	running = false;
	}
	public void resume() {
	
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
