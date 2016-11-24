package joe.game.layout.implementation.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Rectangle2D;
import joe.game.layout.IDrawable;
import joe.game.layout.implementation.LayoutCalculator;

public class ImageDrawer implements IDrawable {
	protected BufferedImage fImage;
	
	protected ImageDrawer(BufferedImage image) {
		fImage = image;
	}
	
	public ImageDrawer(String location) {
		try {
			fImage = ImageIO.read(getClass().getResourceAsStream(location));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ImageDrawer createImage(BufferedImage image) {
		return new ImageDrawer(image);
	}
	
	@Override
	public Dimension2D getDimension() {
		return LayoutCalculator.createDimension(fImage.getWidth(), fImage.getHeight());
	}

	@Override
	public void draw(Graphics2D g, Rectangle2D rect) {
		draw(g, rect.getX1(), rect.getY1(), rect.getWidth(), rect.getHeight());
	}
	
	public static void draw(Graphics2D g, BufferedImage image, double X, double Y, double width, double height) {
		AffineTransform transform = new AffineTransform();
		transform.scale(1, 1);
		g.setTransform(transform);
		
		if (width < 0) {
			X += 1;
		}
		
		if (height < 0) {
			Y += 1;
		}
		
		g.drawImage(image, (int)X, (int)Y, (int)width, (int)height, null);
	}
	
	public void draw(Graphics2D g, double X, double Y, double width, double height) {
		draw(g, fImage, X, Y, width, height);
	}
	
	public ImageDrawer crop(double left, double right, double top, double bottom) { 
		BufferedImage newImage = createImage((int)(fImage.getWidth() - (right + left)), (int)(fImage.getHeight() - (bottom + top)));
		
		int x1 = (int) (left < 0 ? 0 : left);
		int y1;
		for (int x2 = (int) (left < 0 ? (left * -1) : 0); x2 < newImage.getWidth() && x1 < fImage.getWidth(); x2++) {
			y1 = (int) (top < 0 ? 0 : top);
	        for (int y2 = (int) (top < 0 ? (top * -1) : 0); y2 < newImage.getHeight() && y1 < fImage.getHeight(); y2++) {
	        	if (x1 >= 0 && y1 >= 0) {
	        		newImage.setRGB(x2, y2, fImage.getRGB(x1, y1));
	        	}
	        	y1++;
	        }
	        x1++;
		}

	    return createImage(newImage);
	}
	
	public ImageDrawer resize(double newWidth, double newHeight) {
		if (newWidth == 0 || newHeight == 0) {
			throw new IllegalArgumentException("Size values are invalid. newWidth=<" + newWidth + ">, newHeight=<" + newHeight + ">");
		}
		java.awt.Image scaledImage = fImage.getScaledInstance((int)Math.abs(newWidth), (int)Math.abs(newHeight), java.awt.Image.SCALE_SMOOTH);
	    BufferedImage newImage = createImage((int)Math.abs(newWidth), (int)Math.abs(newHeight));

	    Graphics2D g2d = newImage.createGraphics();
	    g2d.drawImage(scaledImage, (int)(newWidth < 0 ? (-1 * newWidth) : 0), (int)(newHeight < 0 ? (-1 * newHeight) : 0), (int)newWidth, (int)newHeight, null);
	    g2d.dispose();

	    return createImage(newImage);
	}
	
	public ImageDrawer rotate(double rotationInDegrees) {		
		BufferedImage newImage = createImage(fImage.getWidth(), fImage.getHeight());
		
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotationInDegrees), fImage.getWidth() / 2, fImage.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		Graphics2D g2d = newImage.createGraphics();
		g2d.drawImage(op.filter(fImage, null), 0, 0, null);
		g2d.dispose();
		
		return createImage(newImage);
	}
	
	public ImageDrawer getSubimage(double X, double Y, double width, double height) {
		if (X < 0 || X >= fImage.getWidth()) {
			throw new IllegalArgumentException("X is an invalid value. X=<" + X + ">");
		} else if (Y < 0 || Y >= fImage.getHeight()) {
			throw new IllegalArgumentException("Y is an invalid value. Y=<" + Y + ">");
		} else if ((width + X) > fImage.getWidth() || (width + X + 1) < 0) {
			throw new IllegalArgumentException("Width is an invalid value. X=<" + X + ">, width=<" + width + ">");
		} else if ((height + Y) > fImage.getWidth() || (height + Y + 1) < 0) {
			throw new IllegalArgumentException("Height is an invalid value. Y=<" + Y + ">, height=<" + height + ">");
		} else if (width == 0 || height == 0) {
			throw new IllegalArgumentException("Size values are invalid. width=<" + width + ">, height=<" + height + ">");
		} else {
			if (width < 0) {
				X = (X + width) + 1;
			}
			if (height < 0) {
				Y = (Y + height) + 1;
			}
			
			if (width < 0 || height < 0) {
				return createImage(fImage.getSubimage((int)X, (int)Y, (int)Math.abs(width), (int)Math.abs(height))).resize(width, height);
			} else {
				return createImage(fImage.getSubimage((int)X, (int)Y, (int)Math.abs(width), (int)Math.abs(height)));
			}
		}
	}
	
	public ImageDrawer changeHue(int color, double filterAlpha) {
		return changeHue(getHue(color), filterAlpha);
	}
	
	public ImageDrawer changeHue(double hue, double filterAlpha) {
		return changeHSBValues(hue, null, null, filterAlpha);
	}
	
	public ImageDrawer changeSaturation(int color, double filterAlpha) {
		return changeSaturation(getSaturation(color), filterAlpha);
	}
	
	public ImageDrawer changeSaturation(double saturation, double filterAlpha) {
		return changeHSBValues(null, saturation, null, filterAlpha);
	}
	
	public ImageDrawer changeBrightness(int color, double filterAlpha) {
		return changeSaturation(getBrightness(color), filterAlpha);
	}
	
	public ImageDrawer changeBrightness(double brightness, double filterAlpha) {
		return changeHSBValues(null, null, brightness, filterAlpha);
	}
	
	public ImageDrawer changeColor(int color, double filterAlpha) {
		return changeColor(getHue(color), getSaturation(color), filterAlpha);
	}
	
	public ImageDrawer changeColor(double hue, double saturation, double filterAlpha) {
		return changeHSBValues(hue, saturation, null, filterAlpha);
	}
	
	public ImageDrawer changeHSBValues(Double hue, Double saturation, Double brightness, Double filterAlpha) {
		BufferedImage newImage = createImage(fImage.getWidth(), fImage.getHeight());
	    for (int i = 0; i < fImage.getWidth(); i++) {
	        for (int j = 0; j < fImage.getHeight(); j++) {
	        	int color = fImage.getRGB(i, j);
	        	newImage.setRGB(i, j, changeHSBValues(color, hue == null ? getHue(color) : hue, saturation == null ? getSaturation(color) : saturation, brightness == null ? getBrightness(color) : brightness, filterAlpha == null ? 1.0 : filterAlpha));
	        }
	    }
	    return createImage(newImage);
	}
	
	public ImageDrawer convertToGrayScale() {
		BufferedImage newImage = createImage(fImage.getWidth(), fImage.getHeight());
	    for (int i = 0; i < fImage.getWidth(); i++) {
	        for (int j = 0; j < fImage.getHeight(); j++) {
	        	newImage.setRGB(i, j, grayScalePixel(fImage.getRGB(i, j)));
	        }
	    }
	    return createImage(newImage);
	}
	
	protected static BufferedImage createImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	protected static BufferedImage copyImage(BufferedImage image) {
		BufferedImage newImage = createImage(image.getWidth(), image.getHeight());
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {
	        	newImage.setRGB(i, j, image.getRGB(i, j));
	        }
		}
	    return newImage;
	}
	
	// Taken from Color.RGBtoHSB(...)
	protected static double getHue(int pixelColor) {
		double red = getRed(pixelColor);
		double green = getGreen(pixelColor);
		double blue = getBlue(pixelColor);
		
		double hue, saturation;
		double cmax = (red > green) ? red : green;
        if (blue > cmax) cmax = blue;
        double cmin = (red < green) ? red : green;
        if (blue < cmin) cmin = blue;

        if (cmax != 0) {
            saturation = ((double)(cmax - cmin)) / ((double)cmax);
		} else {
            saturation = 0;
        }
        if (saturation == 0) {
            return 0.0;
        } else {
            double redc = (cmax - red) / (cmax - cmin);
            double greenc = (cmax - green) / (cmax - cmin);
            double bluec = (cmax - blue) / (cmax - cmin);
            if (red == cmax) {
                hue = bluec - greenc;
            } else if (green == cmax) {
                hue = 2.0 + redc - bluec;
            } else {
                hue = 4.0 + greenc - redc;
            }
            hue = hue / 6.0;
            if (hue < 0) {
                hue = hue + 1.0;
            }
            return hue;
        }
	}
	
	// Taken from Color.RGBtoHSB(...)
	protected static double getSaturation(int pixelColor) {		
		double red = getRed(pixelColor);
		double green = getGreen(pixelColor);
		double blue = getBlue(pixelColor);
		
		double cmax = (red > green) ? red : green;
        if (blue > cmax) cmax = blue;
        double cmin = (red < green) ? red : green;
        if (blue < cmin) cmin = blue;

        if (cmax != 0) {
            return (cmax - cmin) / cmax;
        } else {
            return 0;
        }
	}
	
	// Taken from Color.RGBtoHSB(...)
	protected static double getBrightness(int pixelColor) {
		double red = getRed(pixelColor);
		double green = getGreen(pixelColor);
		double blue = getBlue(pixelColor);
		
		double cmax = (red > green) ? red : green;
        if (blue > cmax) cmax = blue;
        
        return cmax / 255.0;
	}
	
	protected static int getAlpha(int pixelColor) {
		return ((pixelColor >> 24) & 0xFF);
	}
	
	protected static int getRed(int pixelColor) {
		return ((pixelColor >> 16) & 0xFF);
	}
	
	protected static int getGreen(int pixelColor) {
		return ((pixelColor >> 8) & 0xFF);
	}
	
	protected static int getBlue(int pixelColor) {
		return ((pixelColor >> 0) & 0xFF);
	}
	
	protected static int getPixel(int alpha, int red, int green, int blue) {
		return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF) << 0);
	}
	
	// Taken from Color.HSBtoRGB(...)
	protected static int getPixel(int alpha, double hue, double saturation, double brightness) {
		int red = 0, green = 0, blue = 0;
        if (saturation == 0) {
            red = green = blue = (int) (brightness * 255.0 + 0.5);
        } else {
            double h = (hue - Math.floor(hue)) * 6.0;
            double f = h - Math.floor(h);
            double p = brightness * (1.0 - saturation);
            double q = brightness * (1.0 - saturation * f);
            double t = brightness * (1.0 - (saturation * (1.0 - f)));
            switch ((int) h) {
            case 0:
                red = (int) (brightness * 255.0 + 0.5);
                green = (int) (t * 255.0 + 0.5);
                blue = (int) (p * 255.0 + 0.5);
                break;
            case 1:
                red = (int) (q * 255.0 + 0.5);
                green = (int) (brightness * 255.0 + 0.5);
                blue = (int) (p * 255.0 + 0.5);
                break;
            case 2:
                red = (int) (p * 255.0 + 0.5);
                green = (int) (brightness * 255.0 + 0.5);
                blue = (int) (t * 255.0 + 0.5);
                break;
            case 3:
                red = (int) (p * 255.0 + 0.5);
                green = (int) (q * 255.0 + 0.5);
                blue = (int) (brightness * 255.0 + 0.5);
                break;
            case 4:
                red = (int) (t * 255.0 + 0.5);
                green = (int) (p * 255.0 + 0.5);
                blue = (int) (brightness * 255.0 + 0.5);
                break;
            case 5:
                red = (int) (brightness * 255.0 + 0.5);
                green = (int) (p * 255.0 + 0.5);
                blue = (int) (q * 255.0 + 0.5);
                break;
            }
        }
        return getPixel(alpha, red, green, blue);
	}
	
	protected static int changeHSBValues(int pixelColor, double filterHue, double filterSaturation, double filterBrightness, double filterAlpha) {
		int alpha = getAlpha(pixelColor);
		double hue = getHue(pixelColor);
		double saturation = getSaturation(pixelColor);
		double brightness = getBrightness(pixelColor);
		
		double hueDifference = filterHue - hue;
		if (hueDifference < 0.0) {
			if (hueDifference < -0.5) {
				hue = (hueDifference + 1.0) * filterAlpha + hue;
			} else {
				hue = (hueDifference * filterAlpha + hue);
			}
		} else {
			if (hueDifference > 0.5) {
				hue = (hueDifference - 1.0) * filterAlpha + hue;
			} else {
				hue = (hueDifference * filterAlpha + hue);
			}
		}
		
		if (hue < -0.0) {
			hue += 1.0;
		} else if (hue > 1.0) {
			hue -= 1.0;
		}
		
		saturation = (filterSaturation - saturation) * filterAlpha + saturation;
		brightness = (filterBrightness - brightness) * filterAlpha + brightness;
		
		return getPixel(alpha, hue, saturation, brightness);
	}
	
	private static int grayScalePixel(int pixelColor) {
		return getPixel(getAlpha(pixelColor), 0.0, 0.0, getBrightness(pixelColor));
	}
	
	// TODO
	/*private static boolean isVisible(int pixelColor) {
		return getAlpha(pixelColor) != 0;
	}
	
	private static boolean isGray(int pixelColor) {
		return getRed(pixelColor) == getGreen(pixelColor) && getGreen(pixelColor) == getBlue(pixelColor);
	}*/
	
	public ImageDrawer flipX() {
		return resize(-1 * fImage.getWidth(), fImage.getHeight());
	}
	
	public ImageDrawer flipY() {
		return resize(fImage.getWidth(), -1 * fImage.getHeight());
	}
	
	public boolean isAllColor(int red, int green, int blue, int alpha) {
		Color newColor = new Color(red, green, blue, alpha);
		for (int y = 0; y < fImage.getHeight(); y++) {
			for (int x = 0; x < fImage.getWidth(); x++) {
				if (fImage.getRGB(x, y) != newColor.getRGB()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isAllTransparent() {
		if (fImage.getColorModel().hasAlpha()) {
			for (int y = 0; y < fImage.getHeight(); y++) {
				for (int x = 0; x < fImage.getWidth(); x++) {
					int pixel = fImage.getRGB(x, y);
					if ((pixel>>24) != 0x00) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ImageDrawer clone() {
		return new ImageDrawer(copyImage(fImage));
	}
}
