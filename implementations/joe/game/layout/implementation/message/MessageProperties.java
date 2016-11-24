package joe.game.layout.implementation.message;

import java.awt.Color;
import java.awt.Font;

import joe.classes.geometry2D.Dimension2D;

public class MessageProperties {
	private MessageMetrics fMetrics;
	private Color fColor;
	
	public MessageProperties(Font font, Color color) {
		fMetrics = new MessageMetrics(font);
		fColor = color;
	}
	
	public Dimension2D getDimension(String message) {
		return fMetrics.getDimension(message);
	}
	
	public void setFont(Font font) {
		fMetrics.setFont(font);
	}
	
	public void setColor(Color color) {
		fColor = color;
	}
	
	public Font getFont() {
		return fMetrics.getFont();
	}
	
	public Color getColor() {
		return fColor;
	}
}
