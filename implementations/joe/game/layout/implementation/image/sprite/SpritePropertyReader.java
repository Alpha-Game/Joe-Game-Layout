package joe.game.layout.implementation.image.sprite;

import java.util.HashMap;
import java.util.Map;

import joe.classes.bundle.Bundle;
import joe.classes.constants.GlobalConstants;
import joe.classes.geometry2D.Dimension2D;
import joe.game.layout.image.sprite.ISpriteFactory;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.image.ImageDrawer;

public class SpritePropertyReader {
	private static final String DIVIDER = "_";
	private static final String LOCATION = "Location";
	private static final String LENGTH = "Length";
	private static final String X = "X";
	private static final String Y = "Y";
	private static final String WIDTH = "Width";
	private static final String HEIGHT = "Height";
	private static final String TIME = "Time";
	private static final String FRAME = "Frame";
	
	private static final int DEFAULT_X = 0;
	private static final int DEFAULT_Y = 0;
	private static final long DEFAULT_TIME = (GlobalConstants.NANOSECONDS / GlobalConstants.MILLISECONDS) * 100; // 1/10 of a second
	
	private Map<String, ISpriteFactory> fSprites;
	private Map<String, SpriteMap> fLoadedMaps;
	
	public SpritePropertyReader() {
		fSprites = new HashMap<String, ISpriteFactory>();
		fLoadedMaps = new HashMap<String, SpriteMap>(); 
	}
	
	public ISpriteFactory getSprite(String spriteName) {
		return fSprites.get(spriteName);
	}
	
	public void readLocation(Bundle bundle) {
		for (String key : bundle.getKeys()) {
			try {
				if (key.endsWith(DIVIDER + LOCATION)) {
					String name = key.substring(0, key.length() - (DIVIDER.length() + LOCATION.length()));
					if (!bundle.hasKey(name + DIVIDER + LENGTH)) {
						readImageFromBundle(bundle, name);
					}
				} else if (key.endsWith(DIVIDER + LENGTH)) {
					readImageFromBundle(bundle, key.substring(0, key.length() - (DIVIDER.length() + LENGTH.length())));
				}
			} catch (Throwable t) {
				// Track Thrown Exceptions
				t.printStackTrace(System.err);
			}
		}
	}
	
	private SpriteMap getSpriteMap(String location) {
		SpriteMap map = fLoadedMaps.get(location);
		if (map == null) {
			map = new SpriteMap(new ImageDrawer(location));
			fLoadedMaps.put(location, map);
		}
		return map;
	}
	
	private void readImageFromBundle(Bundle bundle, String imageName) {
		SpriteMap rootMap = null;
		String location = bundle.getStringValue(imageName + DIVIDER + LOCATION, null);
		if (location != null) {
			rootMap = getSpriteMap(location);
		}
		Long length = bundle.getLongValue(imageName + DIVIDER + LENGTH, null);
		if (length == null || length < 2) {
			SpriteMap map = null;
			location = bundle.getStringValue(imageName + DIVIDER + LOCATION + DIVIDER + 1, null);
			if (location != null) {
				map = getSpriteMap(location);
			}
					
			if (map == null) {
				map = rootMap;
			}
			
			if (map != null) {
				Dimension2D dimension = map.getDimension();

				int x = bundle.getIntegerValue(imageName + DIVIDER + X + DIVIDER + FRAME + 1, bundle.getIntegerValue(imageName + DIVIDER + X, DEFAULT_X));
				int y = bundle.getIntegerValue(imageName + DIVIDER + Y + DIVIDER + FRAME + 1, bundle.getIntegerValue(imageName + DIVIDER + Y, DEFAULT_Y));
				int width = bundle.getIntegerValue(imageName + DIVIDER + WIDTH + DIVIDER + FRAME + 1, bundle.getIntegerValue(imageName + DIVIDER + WIDTH, (int)dimension.getWidth()));
				int height = bundle.getIntegerValue(imageName + DIVIDER + HEIGHT + DIVIDER + FRAME + 1, bundle.getIntegerValue(imageName + DIVIDER + HEIGHT, (int)dimension.getHeight()));
				
				SpriteFactory factory = map.getSprite(LayoutCalculator.createRectangle(x, y, width, height));
				if (factory != null) {
					fSprites.put(imageName, factory);
				}
			}
		} else {
			Integer default_x = bundle.getIntegerValue(imageName + DIVIDER + X, DEFAULT_X);
			Integer default_y = bundle.getIntegerValue(imageName + DIVIDER + Y, DEFAULT_Y);
			Integer default_width = bundle.getIntegerValue(imageName + DIVIDER + WIDTH, null);
			Integer default_height = bundle.getIntegerValue(imageName + DIVIDER + HEIGHT, null);
			Long default_time = bundle.getLongValue(imageName + DIVIDER + TIME, DEFAULT_TIME);
			
			AnimatedSpriteFactory factory = new AnimatedSpriteFactory();
			for (int i = 0; i < length; i++) {
				SpriteMap map = null;
				location = bundle.getStringValue(imageName + DIVIDER + LOCATION + DIVIDER + (i + 1), null);
				if (location != null) {
					map = getSpriteMap(location);
				}
				
				if (map == null) {
					map = rootMap;
				}
				
				if (map == null) {
					long time = bundle.getLongValue(imageName + DIVIDER + TIME + DIVIDER + (i + 1), default_time);
					factory.addFrame(null, time);
				} else {
					Dimension2D dimension = map.getDimension();
					int x = bundle.getIntegerValue(imageName + DIVIDER + X + DIVIDER + FRAME + (i + 1), default_x);
					int y = bundle.getIntegerValue(imageName + DIVIDER + Y + DIVIDER + FRAME + (i + 1), default_y);
					int width = bundle.getIntegerValue(imageName + DIVIDER + WIDTH + DIVIDER + FRAME + (i + 1), default_width != null ? default_width : (int)dimension.getWidth());
					int height = bundle.getIntegerValue(imageName + DIVIDER + HEIGHT + DIVIDER + FRAME + (i + 1), default_height != null ? default_height : (int)dimension.getHeight());
					long time = bundle.getLongValue(imageName + DIVIDER + TIME + DIVIDER + FRAME + (i + 1), default_time);
					
					SpriteFactory frame = map.getSprite(LayoutCalculator.createRectangle(x, y, width, height));
					factory.addFrame(frame == null ? null : frame.getSprite(), time);
				}
			}
			fSprites.put(imageName, factory);
		}
	}
}
