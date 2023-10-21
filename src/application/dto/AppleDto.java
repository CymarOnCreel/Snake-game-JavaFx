package application.dto;

public class AppleDto {
	private int xPosition;
	private int yPosition;
	
	public AppleDto(int xPosition, int yPosition) {
		
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
		return "AppleDto [xPosition=" + xPosition + ", yPosition=" + yPosition + "]";
	}

}
