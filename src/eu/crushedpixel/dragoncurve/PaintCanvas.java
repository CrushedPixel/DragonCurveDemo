package eu.crushedpixel.dragoncurve;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.HashMap;


@SuppressWarnings("serial")
public class PaintCanvas extends DoubleBuffer {

	private HashMap<Integer, Image> imageBuffer = new HashMap<>();

	@Override
	public void paintBuffer(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		
		if(imageBuffer.containsKey(order)) {
			Image img = imageBuffer.get(order);
			g.drawImage(img, 0, 0, this);
			return;
		}

		int x = 400;
		int y = 400;

		int mp = getMPForOrder();

		String curve = "";

		for(int i=0; i<order; i++) {
			String oldCurve = curve;
			curve += "R";

			int mid = (int)Math.floor(oldCurve.length()/2f);
			for(int in=0; in<oldCurve.length(); in++) {
				if(in == mid) {
					curve += "L";
				} else {
					curve += oldCurve.charAt(in);
				}
			}
		}

		Direction dir = Direction.UP;

		Point p = drawDir(g2D, x, y, dir, mp);
		x = p.x;
		y = p.y;

		for(int in=0; in<curve.length(); in++) {
			dir = dir.rotate(curve.charAt(in) == 'R');
			p = drawDir(g2D, x, y, dir, mp);
			x = p.x;
			y = p.y;
		}
	}
	
	private int getMPForOrder() {
		if(order <= 3) return 100;
		if(order <= 4) return 75;
		if(order <= 5) return 50;
		if(order <= 6) return 33;
		if(order <= 7) return 25;
		if(order <= 8) return 18;
		if(order <= 9) return 12;
		if(order <= 10) return 8;
		if(order <= 11) return 6;
		if(order <= 12) return 5;
		if(order <= 13) return 4;
		if(order <= 15) return 2;
		return 1;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		imageBuffer.put(order, deepCopy((BufferedImage)bufferImage));
	}

	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	private enum Direction {
		UP, RIGHT, DOWN, LEFT;

		public Direction rotate(boolean right) {	
			for(int i = 0; i < values().length; i++) {
	            if(values()[i] == this) {
	            	i += right ? 1 : -1;
	                if(i == values().length) {
	                    i = 0;
	                } else if(i == -1) {
	                	i = values().length - 1;
	                }
	                return values()[i];
	            }
	        }
	        return this;
		}
	}

	private int order = 0;

	public void redraw(int val) {
		this.order = val;
		repaint();
	}

	private Point drawDir(Graphics2D g2D, int x, int y, Direction dir, int mp) {
		g2D.setStroke(new BasicStroke(1f));
		g2D.setColor(Color.BLACK);
		Point p = null;
		switch(dir) {
		case DOWN:
			p = new Point(x, y+mp);
			break;
		case LEFT:
			p = new Point(x-mp, y);
			break;
		case RIGHT:
			p = new Point(x+mp, y);		
			break;
		case UP:
			p = new Point(x, y-mp);
			break;
		}
		g2D.drawLine(x, y, p.x, p.y);
		return p;
	}

}
