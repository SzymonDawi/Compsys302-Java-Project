import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	private int FrameCount;
	private int FrameDelay;
	private int CurrentFrame;
	private int FrameAmount;
	private int Length;
	
	private boolean Playing;
	
	private List<Frame> Frames = new ArrayList<Frame>();
	
	public Animation(BufferedImage[] Frames, int FrameDelay) {
		this.FrameDelay = FrameDelay;
		this.Playing = false;
		
		
		this.FrameCount = 0;
		this.CurrentFrame = 0;
		this.FrameAmount = this.Frames.size();
	}
	
	public void Play() {
		if(Playing) {
			return;
		}
		
		if(Frames.size() == 0) {
			return;
		}
		
		Playing = true;
	}
	
	public void Stop() {
		if(Frames.size() == 0) {
			return;
		}
		
		Playing = false;
		CurrentFrame = 0;
	}
	
	public void Reset() {
		this.Playing = false;
		this.FrameAmount = 0;
		this.CurrentFrame = 0;
	}
	
	public BufferedImage GetSprite() {
		return Frames.get(CurrentFrame).GetFrame();
	}
	
	
}
