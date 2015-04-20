package view.helpPanel;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ImageTools{

	public static Image getScaledImage(Image srcImg, int scaler){
		
		ImageObserver observer = null;
		
		int height = srcImg.getHeight(observer);
		int width = srcImg.getWidth(observer);
		Dimension newSize = scaleDimension(width, height, scaler);
		
	    BufferedImage resizedImg = new BufferedImage(newSize.width, newSize.height, BufferedImage.TYPE_INT_ARGB);
	    
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, newSize.width, newSize.height, null);
	    g2.dispose();
	    
	    return resizedImg;
	}
	
	public static Dimension scaleDimension(int x, int y, int scaler){
		double newX = x, newY = y;
		if(x > scaler){
			double scalerValue = scaler / newX;
			newX = scalerValue * x;
			newY = scalerValue * y;
			
			x = (int) Math.round(newX);
			y = (int) Math.round(newY);
		}
		if(y > scaler){
			double scalerValue = scaler / newY;
			newX = scalerValue * x;
			newY = scalerValue * y;
			
			x = (int) Math.round(newX);
			y = (int) Math.round(newY);
		}
		return new Dimension(x, y);
	}
}