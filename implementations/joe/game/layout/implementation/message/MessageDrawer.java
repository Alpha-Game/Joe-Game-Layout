package joe.game.layout.implementation.message;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.classes.language.Message;
import joe.game.layout.IDrawable;

public class MessageDrawer implements IDrawable {
	private Message fMessage;
	private MessageProperties fProperties;
	
	public MessageDrawer(Message message, Font font, Color color) {
		fMessage = message;
		fProperties = new MessageProperties(font, color);
	}
	
	public void setFont(Font font) {
		fProperties.setFont(font);
	}
	
	public void setColor(Color color) {
		fProperties.setColor(color);
	}
	
	@Override
	public Dimension2D getDimension() {
		return fProperties.getDimension(fMessage.getMessage());
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		draw(g, fMessage.getMessage(), fProperties, rect);
	}
	
	private static void draw(Graphics2D g, String message, MessageProperties properties, double x, double y, double xScale, double yScale) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setTransform(AffineTransform.getScaleInstance(xScale, yScale));
		g.setFont(properties.getFont());
		g.setColor(properties.getColor());
		g.drawString(message, (int)Math.floor(x / xScale), (int)Math.floor(y / yScale));
	}
	
	private static void draw(Graphics2D g, String message, MessageProperties properties, Rectangle2D rect) {
		double xScale = rect.getWidth() / properties.getDimension(message).getWidth();
		double yScale = rect.getHeight() / properties.getDimension(message).getHeight();
		draw(g, message, properties, rect.getMinX(), rect.getMinY(), xScale, yScale);
	}
}
