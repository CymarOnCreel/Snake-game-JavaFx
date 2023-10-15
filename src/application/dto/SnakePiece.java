package application.dto;

public class SnakePiece {

	private int xPosition;
	private int yPosition;
	
	public SnakePiece(int xPosition, int yPosition) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	@Override
	public String toString() {
		return "SnakePiece [xPosition=" + xPosition + ", yPosition=" + yPosition + "]";
	}
	
	
}
