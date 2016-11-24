package joe.game.layout.implementation.image.sprite;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import joe.classes.bundle.Bundle;
import joe.game.layout.image.sprite.ISpriteFactory;

public class Sprites {
	private Locale fLocale;
	private Map<String, SpritePropertyReader> fReaderMap;
	private Map<String, ISpriteFactory> fSpriteMap;
	
	public Sprites() {
		this(Locale.US);
	}
	
	public Sprites(Locale locale) {
		fLocale = locale;
		fReaderMap = new HashMap<String, SpritePropertyReader>();
		fSpriteMap = new HashMap<String, ISpriteFactory>();
	}
	
	protected SpritePropertyReader getReader(String readLocation) {
		SpritePropertyReader reader = fReaderMap.get(readLocation);
		if (reader == null) {
			String[] location = readLocation.split(":");
			if (location.length == 2) {
				reader = new SpritePropertyReader();
				try {
					reader.readLocation(new Bundle(fLocale, location[0], location[1]));
					fReaderMap.put(readLocation, reader);
				} catch (Throwable t) {
					
				}
			}			
		}
		return reader;
		
	}
	
	public ISpriteFactory getSprite(String sprite) {
		ISpriteFactory factory = fSpriteMap.get(sprite);
		if (factory == null) {
			String[] location = sprite.split("\\?");
			if (location.length == 2) {
				SpritePropertyReader reader = getReader(location[0]);
				if (reader != null) {
					factory = reader.getSprite(location[1]);
					if (factory != null) {
						fSpriteMap.put(sprite, factory);
					}
				}
			}
		}
		return factory;
	}
}
