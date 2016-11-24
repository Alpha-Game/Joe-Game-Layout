package joe.game.layout.implementation.menu;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.Position;

public class Menu implements IDrawable {
	private Set<Integer> fSelected;
	private Position fInnerPosition;
	private AbstractMenuItem fMenu[];
	
	public Menu(AbstractMenuItem menu[], Position position) {
		fSelected = new HashSet<Integer>();
		fInnerPosition = position;
		fMenu = menu;
	}
	
	public void addSelected(int selected) {
		fSelected.add(selected);
	}
	
	public void clearSelected() {
		fSelected.clear();
	}
	
	public void removeSelected(int selected) {
		fSelected.remove(selected);
	}
	
	public void setPosition(Position position) {
		fInnerPosition = position;
	}
	
	@Override
	public Dimension2D getDimension() {
		double width = 0;
		double height = 0;
		for (AbstractMenuItem item : fMenu) {
			Dimension2D dimension = item.getDimension();
			height += dimension.getHeight();
			if (width < dimension.getWidth()) {
				width = dimension.getWidth();
			}
		}
		return LayoutCalculator.createDimension(width, height);
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		Dimension2D dimensions = getDimension();
		double xScale = rect.getWidth() / dimensions.getWidth();
		double yScale = rect.getHeight() / dimensions.getHeight();
		
		double y = 0;
		
		int i = 0;
		for (AbstractMenuItem item : fMenu) {
			item.setIsSelected(fSelected.contains(i));
			Dimension2D menuItemDimensions = item.getDimension();
			Rectangle2D menuItemRectangle = LayoutCalculator.getAbsoluteInnerRectangle(rect, LayoutCalculator.createDimension(menuItemDimensions.getWidth() * xScale, menuItemDimensions.getHeight() * yScale), fInnerPosition, 0, y);
			item.draw(g, menuItemRectangle);
			y += menuItemRectangle.getHeight();
			i++;
		}
	}
}
