import java.awt.image.BufferedImage;

//One frame of the animation stores the time the frame stays on screen
public class Frame {
	private BufferedImage Frame;
	private int Length;
	
	public Frame(BufferedImage Frame ,int Length) {
		this.Frame  = Frame;
		this.Length = Length;
	}
	
	public void Setframe(BufferedImage Frame) {
		this.Frame = Frame;
	}
	
	public void SetLength(int Length) {
		this.Length = Length;
	}
	
	public int GetLength() {
		return Length;
	}

	public BufferedImage GetFrame() {
		return Frame;
	}
}
